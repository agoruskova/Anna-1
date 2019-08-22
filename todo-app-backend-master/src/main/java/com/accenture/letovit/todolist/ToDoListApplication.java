package com.accenture.letovit.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoListApplication {

	/**
	 * Main method that will start-up the application.
	 * Spring will start the web server with backend application
	 * running on it on address http://localhost:8080 
	 */
	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}

}
