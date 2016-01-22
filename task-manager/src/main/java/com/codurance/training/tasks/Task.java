package com.codurance.training.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Task {
	private long id;
	private final String description;
	private boolean done;
	private Date deadline;
	private Date date;
	private SimpleDateFormat sdf;

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

	public long getId() {
		return id;
	}
	
	public void setId(long pId) {
		this.id = pId;
	}

	public String getDescription() {
		return description;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date date) {
		try {
			this.deadline = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getDate() {
		return date;
	}
}
