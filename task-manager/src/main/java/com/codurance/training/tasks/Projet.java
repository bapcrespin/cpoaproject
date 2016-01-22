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
	 * getter de la liste de taches cochées
	 * 
	 * @return
	 * 		la liste des taches cochées de ce projet
	 */
	public ArrayList<Task> getTasksco(){
		return tasksco;
	}
	
	/**
	 * Ajoute une tache à la liste des taches cochées
	 * 
	 * @param tache
	 * 		la tache à ajouter
	 */
	public void addTaskco(Task tache){
		tasksco.add(tache);
	}
	
	/**
	 * Supprime une tache de la liste des taches cochées
	 * 
	 * @param task
	 * 		la tache à supprimer
	 */
	public void deleteTaskco(Task task){
		tasksco.remove(task);
	}
	
	/**
	 * getter de la liste de taches décochées
	 * 
	 * @return
	 * 		la liste des taches décochées de ce projet
	 */
	public ArrayList<Task> getTasksde(){
		return tasksde;
	}
	
	/**
	 * Ajoute une tache à la liste des taches décochées
	 * 
	 * @param tache
	 * 		la tache à ajouter
	 */
	public void addTaskde(Task tache){
		tasksde.add(tache);
	}
	
	/**
	 * Supprime une tache de la liste des taches décochées
	 * 
	 * @param task
	 * 		la tache à supprimer
	 */
	public void deleteTaskde(Task task){
		tasksde.remove(task);
	}
	
}
