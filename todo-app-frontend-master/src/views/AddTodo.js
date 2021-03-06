import React, { Component } from "react";
import { withRouter } from "react-router-dom";

class AddTodo extends Component {
  state = {
    title: "",
    text: "",
    deadline: ""
  };

  handleSubmit = async event => {
    event.preventDefault();
    await this.props.onAdd(this.state);
    this.setState({
      title: '',
      text: ''
    });
    this.props.history.push("/");
  };

  handleChange = event => {
    const { name, value } = event.target;
    this.setState({
      [name]: value
    });
  };

  render() {
    const { title, text, deadline } = this.state;

    return (
      <form onSubmit={this.handleSubmit}>
        <input
          name="title"
          type="text"
          value={title}
          onChange={this.handleChange}
          className="form-control mb-2"
          placeholder="Title"
          required
        />
        <textarea
          name="text"
          value={text}
          onChange={this.handleChange}
          className="form-control mb-2"
          placeholder="Text"
        />
         <input
          name="deadline"
          type="datetime-local"
          value={deadline}
          onChange={this.handleChange}
          className="form-control mb-2"
          placeholder="Deadline"
          required
        />

        <button
          type="submit"
          className="btn btn-outline-success"
          disabled={!title}
        >
          Save
        </button>
      </form>
    );
  }
}

export default withRouter(AddTodo);
