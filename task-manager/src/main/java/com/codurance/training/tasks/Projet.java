package com.codurance.training.tasks;

import java.util.ArrayList;

/**
 * 
 * @author Nicolas CELLIER - Baptiste CRESPIN
 *
 */
public final class Projet {
	private final String name ;
	private ArrayList<Task> tasksco;
	private ArrayList<Task> tasksde;
	
	/**
	 * Constructeur de projet
	 * 
	 * @param name
	 * 		nom du projet
	 */
	public Projet(String name){
		this.name = name;
		tasksco = new ArrayList<Task>();
		tasksde = new ArrayList<Task>();
	}
	
	/**
	 * getter du nom du projet
	 * 
	 * @return le nom du projet
	 */
	public String getName(){
		return this.name ;
	}
	
	/**
	 * getter de la liste de taches coch�es
	 * 
	 * @return
	 * 		la liste des taches coch�es de ce projet
	 */
	public ArrayList<Task> getTasksco(){
		return tasksco;
	}
	
	/**
	 * Ajoute une tache � la liste des taches coch�es
	 * 
	 * @param tache
	 * 		la tache � ajouter
	 */
	public void addTaskco(Task tache){
		tasksco.add(tache);
	}
	
	/**
	 * Supprime une tache de la liste des taches coch�es
	 * 
	 * @param task
	 * 		la tache � supprimer
	 */
	public void deleteTaskco(Task task){
		tasksco.remove(task);
	}
	
	/**
	 * getter de la liste de taches d�coch�es
	 * 
	 * @return
	 * 		la liste des taches d�coch�es de ce projet
	 */
	public ArrayList<Task> getTasksde(){
		return tasksde;
	}
	
	/**
	 * Ajoute une tache � la liste des taches d�coch�es
	 * 
	 * @param tache
	 * 		la tache � ajouter
	 */
	public void addTaskde(Task tache){
		tasksde.add(tache);
	}
	
	/**
	 * Supprime une tache de la liste des taches d�coch�es
	 * 
	 * @param task
	 * 		la tache � supprimer
	 */
	public void deleteTaskde(Task task){
		tasksde.remove(task);
	}
	
}
