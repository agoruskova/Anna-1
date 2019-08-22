package com.accenture.letovit.todolist.validator;

import com.accenture.letovit.todolist.ToDoItem;

/**
 * This class is used for validating our input from front-end.
 */
public class ToDoItemValidator {
	
	public static void validate(ToDoItem toDoItem) {
		// title should not be too long
		if (toDoItem.getTitle().length() > 30) {
			throw new RuntimeException("Title is longer than 20 characters.");
		}
		// text should not be too long
		if (toDoItem.getText().length() > 250) {
			throw new RuntimeException("Text is longer than 250 characters.");
		}
		// validate characters in title
		for(int i=0; i<toDoItem.getTitle().length(); i++) {
			Character znak = toDoItem.getTitle().charAt(i);
			if (!Character.isLetterOrDigit(znak) && !Character.isSpaceChar(znak)) {
				throw new RuntimeException("Invalid character '"+znak +"' in title.");
			}
		}
	}
}
