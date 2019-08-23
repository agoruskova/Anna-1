import React, { Component } from "react";

import axios from "../axios";

import TodoButtons from "./TodoButtons";

import moment from "moment";

class Todo extends Component {
  renderText = () => {
    const { text } = this.props.todo;
    if (!text) return null;

    return (
      <div className="card-text" dangerouslySetInnerHTML={{ __html: text }} />
    );
  };

  handleFinish = async () => {
    await axios.patch("/todos/" + this.props.todo.id, {
      finished: true,
      finishedAt: moment().format()
    });
    this.props.onFinish();
  };

  handleRemove = async () => {
    await axios.delete("/todos/" + this.props.todo.id);
    this.props.onRemove();
  };

  render() {
    const { createdAt, finishedAt, deadline, title, finished } = this.props.todo;
    let classes = "card";
    if (finished) classes += " topBorderGreen"
    else classes += " topBorderRed";


    
      var a = moment();
var b = moment(createdAt);
      const showBadge = a.diff(b, 'minutes') <= 10 ? true : false; 
    


    return (
      
      <div className="todo mb-2">
        <div className={classes}>
          <div className="card-body">
            <h5 className="card-title">{title} <span className={ showBadge ? "badge badge-secondary" : "hideBadge"}>New</span></h5>
            <h6 className="card-subtitle text-muted mb-2">
              Created at {moment(createdAt).format('HH:mm:ss YYYY-MM-DD')}
            </h6>
            { (finished) && (
            <h6 className="card-subtitle text-muted mb-2">
              Finished at {moment(finishedAt).format('HH:mm:ss YYYY-MM-DD')}
            </h6>
            )}
            {this.renderText()}
            <br></br>
            <h6 className="card-subtitle text-muted mb-2">
              Deadline at { deadline != null ? moment(deadline).format('HH:mm:ss YYYY-MM-DD') : "N/A"}
            </h6>
            <TodoButtons
              todo={this.props.todo}
              onFinish={this.handleFinish}
              onRemove={this.handleRemove}
            />
          </div>
        </div>
      </div>
    );
  }
}

export default Todo;
