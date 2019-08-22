package com.accenture.letovit.todolist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.letovit.todolist.database.DbToDoItem;
import com.accenture.letovit.todolist.database.DbToDoItemRepository;
import com.accenture.letovit.todolist.database.ToDoItemConverter;
import com.accenture.letovit.todolist.validator.ToDoItemValidator;

/**
 * Main controller class that is handling REST request from front-end (@RestController annotation is marking
 * this class to do this)
 * @CrossOrigin annotation is just disabling some security features, that would not let our backend application
 * work with our front-end application, that is running on http:localhost:3000
 * 
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoListController {
	
	private DbToDoItemRepository repository;
	
	/**
	 * This constructor is called automatically by Spring Boot and the repository
	 * is also created by Spring Boot and assigned to our repository variable.
	 * Then we can use this variable in all of our handling methods below.
	 */
	public ToDoListController(DbToDoItemRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * POST request handling method listening on '<base_url>/todos.json' url. 
	 * This handler will create new todo item and save it to database.
	 * Unique identifier is also created and used as a primary key (unique) column in database table.
	 * Response with generated ID is returned (SaveResponse object).
	 */
	@RequestMapping(value ="todos", method = RequestMethod.POST)
	public String addToDoItem(@RequestBody ToDoItem request) {
		// validate incoming request with our validator
		ToDoItemValidator.validate(request);
		
		// we will use our own current date in todo, not the one from json request
		LocalDateTime now = LocalDateTime.now();
		String prettyDateTime = DateTimeFormatter.ISO_DATE_TIME.format(now);
		
		// generate unique ID for each todo item
		String id = UUID.randomUUID().toString();
		
		// save to database
		DbToDoItem dbToDoItem = ToDoItemConverter.jsonToDbEntity(request, id);
		repository.save(dbToDoItem);
		
		// send back generated id
		return id;
	}
	
	/**
	 * GET request handling method listening on '<base_url>/todos.json' URL. 
	 * Method will return all records from database as map object.
	 * Map is converted from java object to json response.
	 */
	@RequestMapping(value ="todos", method = RequestMethod.GET)
	public List<ToDoItem> fetchAllToDoItems() {
		// fetch all data from database table
		Iterable<DbToDoItem> dbToDoItemList = repository.findAll();
		// create empty map that will be converted to json
		List<ToDoItem> toDoItems = new ArrayList<ToDoItem>();
		// fill our map with data from database
		for (DbToDoItem dbToDoItem : dbToDoItemList) {
			toDoItems.add(ToDoItemConverter.dbEntityToJson(dbToDoItem));
		}
		return toDoItems;
	}
	
	/**
	 * DELETE request handling method listening on '<base_url>/todos/{identifier}.json' URL. 
	 * This handler will just delete single todo item from the database.
	 * Method is returning nothing - that means empty response is returned to front-end.
	 * @PathVariable identifier is received in our request from front-end
	 */
	@RequestMapping(value ="/todos/{identifier}", method = RequestMethod.DELETE)
	public void deleteToDoItem(@PathVariable String identifier) {
		// delete the record from DB
		repository.deleteById(identifier);
	}
	
	/**
	 * PATCH request handling method listening on '<base_url>/todos/{identifier}.json' URL. 
	 * This handler will just edit single todo item from the map with new finished flag.
	 * Method is returning nothing - that means empty response is returned to front-end.
	 * @PathVariable identifier is received in our request from front-end
	 * Inside our request body we receive the new finished flag (@RequestBody param)
	 */
	@RequestMapping(value ="/todos/{identifier}", method = RequestMethod.PATCH)
	public void updateToDoItem(@PathVariable String identifier, @RequestBody UpdateRequest requestBody) {
		// set single todo item to finished and save the change to DB
		DbToDoItem dbToDoItem = repository.findById(identifier).get();
		dbToDoItem.setFinished(requestBody.isFinished());
		repository.save(dbToDoItem);
	}
}
