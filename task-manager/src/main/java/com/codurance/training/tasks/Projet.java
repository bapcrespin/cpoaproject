package com.codurance.training.tasks;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Projet {
	private final String name ;
	private Map<String, List<Task>> tasks;
	
	public Projet(String name){
		this.name = name;
		tasks = new LinkedHashMap<>();
	}
	
	public Map<String, List<Task>> getTasks(){
		return tasks;
	}
}
