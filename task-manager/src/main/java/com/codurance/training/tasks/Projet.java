package com.codurance.training.tasks;

import java.util.ArrayList;

public final class Projet {
	private final String name ;
	private ArrayList<Task> tasks;
	
	public Projet(String name){
		this.name = name;
		tasks = new ArrayList<Task>();
	}
	
	public String getName(){
		return this.name ;
	}
	
	public ArrayList<Task> getTasks(){
		return tasks;
	}
	
	public void addTask(Task tache){
		tasks.add(tache);
	}
	
}
