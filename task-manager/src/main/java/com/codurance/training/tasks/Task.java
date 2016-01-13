package com.codurance.training.tasks;

import java.util.Date;

public final class Task {
	private final long id;
	private final String description;
	private boolean done;
	private Date deadline;
	private Date date;

	public Task(long id, String description, boolean done) {
		this.id = id;
		this.description = description;
		this.done = done;
		this.deadline = null;
		this.date = new Date();
	}

	public long getId() {
		return id;
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
		this.deadline = date;
	}

	public Date getDate() {
		return date;
	}
}
