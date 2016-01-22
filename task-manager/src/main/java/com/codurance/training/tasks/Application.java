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

	/**
	 * Permet d'instancier le BufferedReader et PrintWriter afin de pouvoir 
	 * gérer l'affichage et lance l'application Runnable avec la méthode run()
	 * 
	 * @param args
	 * @throws Exception
	 * @see BufferedReader
	 * @see PrintWriter
	 */
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		new Application(in, out).run();
	}

	/**
	 * Constructeur de l'application ayant deux parametres. Initialise la sortie et entré du buffeur.
	 * 
	 * @param reader
	 * @param writer
	 * @see BufferedReader
	 * @see PrintWriter
	 */
	public Application(BufferedReader reader, PrintWriter writer) {
		this.in = reader;
		this.out = writer;
	}

	/**
	 * Methode utiliser pour démarrer le thread et lancer l'application
	 * 
	 * @see Application#execute
	 */
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

	/**
	 * Permet d'executer les actions liées aux commandes tapées.
	 * 
	 * @param commandLine
	 * 					Commandes écrites dans la console
	 * @see Application#view
	 * @see Application#add
	 * @see Application#deleteTask
	 * @see Application#check
	 * @see Application#uncheck
	 * @see Application#help
	 * @see Application#deadline
	 * @see Application#today
	 */
	private void execute(String commandLine) {
		String[] commandRest = commandLine.split(" ", 2);
		String command = commandRest[0];
		switch (command) {
		case "view":
			view(commandRest[1]);
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
		default:
			error(command);
			break;
		}
	}

	/**
	 * Affiche la liste des taches regroupées par projet.
	 * 
	 * @see Projet
	 * @see Task
	 */
	private void viewByProject() {
		for (Projet projet : projects) {
			out.println(projet.getName());
			for (Task task : projet.getTasksco()) {
				out.printf("    [%c] %d: %s%n", 'x', task.getId(), task.getDescription());
			}
			for (Task task : projet.getTasksde()) {
				out.printf("    [%c] %d: %s%n", ' ', task.getId(), task.getDescription());
			}
			out.println();
		}
	}

	/**
	 * En fonction de la commande tapé après le add execute l'action correspondante
	 * 
	 * @param commandLine
	 * 					Suite de la commande tapé par l'utilisateur après add
	 * @see Application#addProject
	 * @see Application#addTask
	 */
	private void add(String commandLine) {
		String[] subcommandRest = commandLine.split(" ", 2);
		String subcommand = subcommandRest[0];
		if (subcommand.equals("project")) {
			addProject(subcommandRest[1]);
		} else if (subcommand.equals("task")) {
			String[] projectTask = subcommandRest[1].split(" ", 2);
			addTask(projectTask[0], projectTask[1]);
		} else {
			out.println("Veuillez rentrer une commande existante");
		}
	}

	/**
	 * Crée un projet et l'ajoute dans la liste des projets.
	 * 
	 * @param name
	 * 			nom du projet
	 */
	private void addProject(String name) {
		projects.add(new Projet(name));
	}

	/**
	 * Crée et ajoute une tache dans un projet dans la liste des taches cochées ou non cochées.
	 * 
	 * @param project
	 * 				nom du projet
	 * @param description
	 * 					nom de la tache
	 * @see Task
	 * @see Projet
	 */
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
		Task tempTask = new Task(nextId(), description, false);
		for (Projet projet : projects) {
			for (Task task : projet.getTasksco()) {
				if (task.getDescription().equals(tempTask.getDescription())) {
					tempTask.setId(task.getId());
					tempTask.setDone(true);
					temp.addTaskco(tempTask);
					lastId--;
					return;
				}
			}
			for (Task task : projet.getTasksde()) {
				if (task.getDescription().equals(tempTask.getDescription())) {
					tempTask.setId(task.getId());
					lastId--;
				}
			}
		}
		temp.addTaskde(tempTask);
	}

	/**
	 * Supprime une tache existante.
	 * 
	 * @param id
	 * 			identifiant de la tache
	 */
	private void deleteTask(int id) {
		for (Projet projet : projects) {
			for (Task task : projet.getTasksco()) {
				if (task.getId() == id) {
					projet.deleteTaskco(task);
					return;
				}
			}
			for (Task task : projet.getTasksde()) {
				if (task.getId() == id) {
					projet.deleteTaskde(task);
					return;
				}
			}
		}
	}

	/**
	 * Coche une tache ou plusieurs taches s'y elles ont le même identifiant.
	 * 
	 * @param idString
	 * 				identifiant de la tache
	 * @see Task
	 * @see Projet
	 */
	private void check(String idString) {
		int temp = -1;
		ArrayList<Task> toRemove = new ArrayList<Task>();
		ArrayList<Task> toAdd = new ArrayList<Task>();
		for (Projet projet : projects) {
			for (Task task : projet.getTasksde()) {
				if (task.getId() == Long.parseLong(idString)) {
					task.setDone(true);
					toAdd.add(task);
					toRemove.add(task);
					temp = 1;
				}
			}
			if (!toAdd.isEmpty() && !toRemove.isEmpty()) {
				projet.getTasksco().addAll(toAdd);
				projet.getTasksde().removeAll(toRemove);
				toAdd.clear();
				toRemove.clear();
			}
		}
		if (temp == -1) {
			out.printf("Could not find a task check with an ID of %s.", idString);
			out.println();
		}
		
	}

	/**
	 * Decoche une tache ou plusieurs taches s'y elles ont le même identifiant.
	 * 
	 * @param idString
	 * 				identifiant de la tache
	 * @see Task
	 * @see Projet
	 */
	private void uncheck(String idString) {
		int temp = -1;
		ArrayList<Task> toRemove = new ArrayList<Task>();
		ArrayList<Task> toAdd = new ArrayList<Task>();
		for (Projet projet : projects) {
			for (Task task : projet.getTasksco()) {
				if (task.getId() == Long.parseLong(idString)) {
					task.setDone(false);
					toAdd.add(task);
					toRemove.add(task);
					temp = 1;
				}
			}
			if (!toAdd.isEmpty() && !toRemove.isEmpty()) {
				projet.getTasksde().addAll(toAdd);
				projet.getTasksco().removeAll(toRemove);
				toAdd.clear();
				toRemove.clear();
			}
		}
		if (temp == -1) {
			out.printf("Could not find a task uncheck with an ID of %s.", idString);
			out.println();
		}
	}

	/**
	 * Affiche l'aide des commandes dans la console
	 */
	private void help() {
		out.println("Commands:");
		out.println("  view by project");
		out.println("  add project <project name>");
		out.println("  add task <project name> <task description>");
		out.println("  delete <task ID>");
		out.println("  check <task ID>");
		out.println("  uncheck <task ID>");
		out.println("  deadline <ID> <date> (dd/MM/yyyy");
		out.println("  today");
		out.println("  view by date");
		out.println("  view by deadline");
		out.println();
	}

	/**
	 * Affecte une date de fin à une tache ou des taches s'y il ya des taches avec le meme idetifiant.
	 * 
	 * @param commandLine
	 * 					date de fin (dd/MM/yy)
	 * @see Projet
	 * @see Task
	 * @see Date
	 */
	private void deadline(String commandLine) {
		int tempError = -1;
		String[] subCommandRest = commandLine.split(" ", 2);
		int id = Integer.parseInt(subCommandRest[0]);
		for (Projet projet : projects) {
			for (Task task : projet.getTasksco()) {
				if (task.getId() == id) {
					Date temp = null;
					try {
						temp = sdf.parse(subCommandRest[1]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					task.setDeadline(temp);
					tempError = 1;
				}
			}
			for (Task task : projet.getTasksde()) {
				if (task.getId() == id) {
					Date temp = null;
					try {
						temp = sdf.parse(subCommandRest[1]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					task.setDeadline(temp);
					tempError = 1;
				}
			}
		}
		if (tempError == -1) {
			out.printf("Could not find a task with an ID of %d.", id);
			out.println();
		}
	}

	/**
	 * Affiche la liste des taches avec une date de fin égal au jour actuel.
	 * 
	 * @see Date
	 * @see SimpleDateFormat
	 * @see Projet
	 * @see Task
	 */
	private void today() {
		Date today = new Date();
		String temp = sdf.format(today);
		String temp2;
		for (Projet projet : projects) {
			out.println(projet.getName());
			int cpt = 0;
			for (Task task : projet.getTasksco()) {
				if (task.getDeadline() != null) {
					temp2 = sdf.format(task.getDeadline());
					if (temp.equals(temp2)) {
						out.printf("    [%c] %d: %s%n", 'x', task.getId(),
								task.getDescription());
						cpt++;
					}
				}
			}
			for (Task task : projet.getTasksde()) {
				if (task.getDeadline() != null) {
					temp2 = sdf.format(task.getDeadline());
					if (temp.equals(temp2)) {
						out.printf("    [%c] %d: %s%n", ' ', task.getId(),
								task.getDescription());
						cpt++;
					}
				}
			}
			if (cpt == 0) {
				out.print("    Pas de tâche à finir aujourd'hui pour ce projet.");
			}
			out.println();
		}
	}
	
	/**
	 * Permet d'executer les commandes view suivant les mots rentrés par l'utilisateur.
	 * 
	 * @param commandLine
	 * 					Suite de la commande tapé après view
	 * @see Application#viewByProject
	 * @see Application#viewByDate
	 * @see Application#viewByDeadline
	 */
	private void view(String commandLine) {
		String[] subcommandRest = commandLine.split(" ", 2);
		String subcommand = subcommandRest[1];
		if (subcommand.equals("project")) {
			viewByProject();
		} else if (subcommand.equals("date")) {
			viewByDate();
		} else if (subcommand.equals("deadline")) {
			viewByDeadline();
		} else {
			out.println("Veuillez rentrer une commande existante");
		}
	}

	/**
	 * Affiche la liste des taches rangées par dates.
	 * 
	 * @see Projet
	 * @see Task
	 * @see Date
	 */
	private void viewByDate() {
		ArrayList<Date> dateTrouve = new ArrayList<Date>();
		for (Projet projet : projects) {
			for (Task task : projet.getTasksco()) {
				if (!dateTrouve.contains(task.getDate())) {
					dateTrouve.add(task.getDate());
				}
			}
			for (Task task : projet.getTasksde()) {
				if (!dateTrouve.contains(task.getDate())) {
					dateTrouve.add(task.getDate());
				}
			}
		}
		for (Date date : dateTrouve) {
			out.println(sdf.format(date));
			for (Projet projet : projects) {
				for (Task task : projet.getTasksco()) {
					if (date.compareTo(task.getDate()) == 0) {
						out.printf("    [%c] %d: %s%n", 'x', task.getId(),
								task.getDescription());
					}
				}
				for (Task task : projet.getTasksde()) {
					if (date.compareTo(task.getDate()) == 0) {
						out.printf("    [%c] %d: %s%n", ' ', task.getId(),
								task.getDescription());
					}
				}
			}
		}
	}

	/**
	 * Affiche les taches rangées par date de fin.
	 * 
	 * @see Projet
	 * @see Task
	 * @see Date
	 */
	private void viewByDeadline() {
		ArrayList<Date> dateTrouve = new ArrayList<Date>();
		for (Projet projet : projects) {
			for (Task task : projet.getTasksco()) {
				if (task.getDeadline() != null) {
					if (!dateTrouve.contains(task.getDeadline())) {
						dateTrouve.add(task.getDeadline());
					}
				}
			}
			for (Task task : projet.getTasksde()) {
				if (task.getDeadline() != null) {
					if (!dateTrouve.contains(task.getDeadline())) {
						dateTrouve.add(task.getDeadline());
					}
				}
			}
		}
		for (Date date : dateTrouve) {
			out.println(sdf.format(date));
			for (Projet projet : projects) {
				for (Task task : projet.getTasksco()) {
					if (task.getDeadline() != null) {
						if (date.compareTo(task.getDeadline()) == 0) {
							out.printf("    [%c] %d: %s%n", 'x', task.getId(),
									task.getDescription());
						}
					}
				}
				for (Task task : projet.getTasksde()) {
					if (task.getDeadline() != null) {
						if (date.compareTo(task.getDeadline()) == 0) {
							out.printf("    [%c] %d: %s%n", ' ', task.getId(),
									task.getDescription());
						}
					}
				}
			}
		}
	}

	/**
	 * Gere les erreurs de commande de la part de l'utilisateur.
	 * 
	 * @param command
	 * 				commande tape par l'utilisateur
	 */
	private void error(String command) {
		out.printf("I don't know what the command \"%s\" is.", command);
		out.println();
	}

	/**
	 * Incremente l'identifiant des taches.
	 * 
	 * @return l'identifiant d'une tache + 1
	 */
	private long nextId() {
		return ++lastId;
	}
}
