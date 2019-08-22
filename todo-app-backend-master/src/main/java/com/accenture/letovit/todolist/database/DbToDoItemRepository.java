package com.accenture.letovit.todolist.database;

import org.springframework.data.repository.CrudRepository;

import com.accenture.letovit.todolist.ToDoListController;

/**
 * This is a Spring magic
 * This interface is just a definition of CRUD repository (Create-Read-Update-Delete)
 * on specified database table DbToDoItem with String ID type. That is why it
 * extends CrudRepository<DbToDoItem, String>
 * Instance object implementing this interface is automatically created by Spring and
 * set to our local variable in {@link ToDoListController}
 */
public interface DbToDoItemRepository extends CrudRepository<DbToDoItem, String>{

}
