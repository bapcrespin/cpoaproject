package com.codurance.training.tasks;

import java.util.ArrayList;

public final class Projet {
	private final String name ;
	private ArrayList<Task> tasksco;
	private ArrayList<Task> tasksde;
	
	public Projet(String name){
		this.name = name;
		tasksco = new ArrayList<Task>();
		tasksde = new ArrayList<Task>();
	}
	
	public String getName(){
		return this.name ;
	}
	
	public ArrayList<Task> getTasksco(){
		return tasksco;
	}
	
	public void addTaskco(Task tache){
		tasksco.add(tache);
	}
	
	public void deleteTaskco(Task task){
		tasksco.remove(task);
	}
	
	public ArrayList<Task> getTasksde(){
		return tasksde;
	}
	
	public void addTaskde(Task tache){
		tasksde.add(tache);
	}
	
	public void deleteTaskde(Task task){
		tasksde.remove(task);
	}
	
}
