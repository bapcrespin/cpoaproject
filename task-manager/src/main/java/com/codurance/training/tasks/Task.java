package com.codurance.training.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Nicolas CELLIER - Baptiste CRESPIN
 *
 */
public final class Task {
	private long id;
	private final String description;
	private boolean done;
	private Date deadline;
	private Date date;
	private SimpleDateFormat sdf;

	/**
	 * Constructeur de Task
	 * 
	 * @param id 
	 * 		identifiant de la tache
	 * @param description
	 * 		description de la tache
	 * @param done
	 * 		indique si la tache a été réalisée ou non
	 */
	public Task(long id, String description, boolean done) {
		this.id = id;
		this.description = description;
		this.done = done;
		this.deadline = null;
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.date = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * getter de id
	 * 
	 * @return l'id de la tache
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * setter de l'id, permet de modifier l'id
	 * 
	 * @param pId
	 * 		le nouvel id à donner à la tache
	 */
	public void setId(long pId) {
		this.id = pId;
	}
	
	/**
	 * getter de la description
	 * 
	 * @return la description de la tache
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * getter de done, permet de voir si la tache a été réalisée ou non
	 * 
	 * @return true si la tache a été réalisé false sinon
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * setter de done
	 * 
	 * @param done
	 * 		true ou false, l'état à donner à la tache 
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * getter de deadline
	 * 
	 * @return la deadline de la tache
	 */
	public Date getDeadline() {
		return deadline;
	}
	
	/**
	 * setter de deadline
	 * 
	 * @param date
	 * 		la deadline à donner à la tache
	 */
	public void setDeadline(Date date) {
		try {
			this.deadline = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * getter de date
	 * 
	 * @return la date de création de la tache
	 */
	public Date getDate() {
		return date;
	}
}
