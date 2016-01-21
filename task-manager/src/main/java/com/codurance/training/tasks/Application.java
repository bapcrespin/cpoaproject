package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class Application implements Runnable {
    private static final String QUIT = "quit";

    private final ArrayList<Projet> projects = new ArrayList<>();
    private final BufferedReader in;
    private final PrintWriter out;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private long lastId = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new Application(in, out).run();
    }

    public Application(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            execute(command);
        }
    }

    private void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                show();
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "delete":
                deleteTask(Integer.parseInt(commandRest[1]));
                break;
            case "check":
                check(commandRest[1]);
                break;
            case "uncheck":
                uncheck(commandRest[1]);
                break;
            case "help":
                help();
                break;
            case "deadline":
            	deadline(commandRest[1]);
            	break;
            case "today":
            	today();
            	break;
            case "viewByDate":
            	viewByDate();
            	break;
            case "viewByDeadline":
            	viewByDeadline();
            	break;
            default:
                error(command);
                break;
        }
    }

    private void show() {
        for (Projet projet : projects) {
            out.println(projet.getName());
            for (Task task : projet.getTasks()) {
                out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

    private void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(projectTask[0], projectTask[1]);
        }
    }

    private void addProject(String name) {
        projects.add(new Projet(name));
    }

    private void addTask(String project, String description) {
    	Projet temp = null;
    	for (Projet projet : projects) {
    		if (projet.getName().equals(project)) {
    			temp = projet;
    		}
    	}
    	if (temp == null) {
    		out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
    	}
    	temp.addTask(new Task(nextId(), description, false));
    }
    
    private void deleteTask(int id) {
    	for (Projet projet : projects) {
    		for (Task task : projet.getTasks()) {
                if (task.getId() == id) {
                    projet.deleteTask(task);
                    return;
                }
            }
    	}
    }

    private void check(String idString) {
        setDone(idString, true);
    }

    private void uncheck(String idString) {
        setDone(idString, false);
    }

    private void setDone(String idString, boolean done) {
        int id = Integer.parseInt(idString);
        for (Projet projet : projects) {
            for (Task task : projet.getTasks()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }

    private void help() {
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task description>");
        out.println("  delete <task ID>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println("  deadline <ID> <date> (dd/MM/yyyy");
        out.println("  today");
        out.println("  viewByDate");
        out.println("  viewByDeadline");
        out.println();
    }
    
    private void deadline(String commandLine) {
    	String[] subCommandRest = commandLine.split(" ", 2);
    	int id = Integer.parseInt(subCommandRest[0]);
        for (Projet projet : projects) {
            for (Task task : projet.getTasks()) {
                if (task.getId() == id) {
                	Date temp = null;
					try {
						temp = sdf.parse(subCommandRest[1]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    task.setDeadline(temp);
                    //out.println(sdf.format(task.getDeadline()));
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }
    
    private void today () {
    	Date today = new Date();
    	String temp = sdf.format(today);
    	String temp2;
    	for (Projet projet : projects) {
            out.println(projet.getName());
            int cpt = 0 ;
            for (Task task : projet.getTasks()) {
            	if (task.getDeadline() != null) {
            		temp2 = sdf.format(task.getDeadline());
            		if (temp.equals(temp2)){
            			out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            			cpt ++ ;
            		}
            	}
            }
            if (cpt == 0) {
            	out.print("    Pas de tâche à finir aujourd'hui pour ce projet.");
            }
            out.println();
        }
    }
    
    private void viewByDate () {
    	
    }
    
    private void viewByDeadline () {
    	
    }

    private void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

    private long nextId() {
        return ++lastId;
    }
}
