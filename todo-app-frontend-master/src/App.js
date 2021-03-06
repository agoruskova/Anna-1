import React, { Component } from "react";
import { HashRouter, Switch, Route } from "react-router-dom";
import moment from "moment";
import findIndex from "lodash/findIndex";

import axios from "./axios";

import AddTodo from "./views/AddTodo";
import TodoList from "./views/TodoList";
import Navbar from "./components/Navbar";

class App extends Component {
  state = {
    todos: []
  };

  async componentDidMount() {
    const result = await axios.get("/todos");

    this.setState({
      todos: result.data
    });
  }

  addTodo = async todo => {
    const newTodo = {
      ...todo,
      finished: false,
      createdAt: moment().format(),
      deadline: moment(todo.deadline).format()
    };
    const result = await axios.post("/todos", newTodo);
    newTodo.id = result.data;

    this.setState(prevState => {
      return {
        todos: prevState.todos.concat(newTodo)
      };
    });
  };

  editTodo = (todo) => {
    const index = findIndex(this.state.todos, { id: todo.id })
    const todos = [...this.state.todos];
    todos.splice(index, 1, todo);
    this.setState({
      todos: todos
    });
  };

  removeTodo = (todo) => {
    const index = findIndex(this.state.todos, { id: todo.id })
    const todos = [...this.state.todos];
    todos.splice(index, 1);
    this.setState({
      todos: todos
    });
  };

  render() {
    const todos = this.state.todos;

    const filteredArray1 = todos.filter((todo) => {return todo.finished === false})
    const filteredArray2 = todos.filter((todo) => {return todo.finished === true})
    filteredArray1.sort((a,b) => {return moment(b.createdAt).diff(moment(a.createdAt))});
    filteredArray2.sort((a,b) => {return moment(b.createdAt).diff(moment(a.createdAt))});
    const correctToDoList = filteredArray1.concat(filteredArray2)

    return (
      <HashRouter>
        <div className="App">
          <Navbar />

          <div className="p-3">
          <Switch>
            <Route
              path="/"
              exact
              render={() => (
                <TodoList
                  todos={correctToDoList}
                  onEdit={this.editTodo}
                  onRemove={this.removeTodo}
                />
              )}
            />
            <Route
              path="/add"
              render={() => <AddTodo onAdd={this.addTodo} />}
            />
          </Switch>
          </div>
        </div>
      </HashRouter>
    );
  }
}

export default App;
