package com.accenture.letovit.todolist;

/**
 * This class represent todo json received from front-end. It contains flag about todo item finish state.
 * It is converted from JSON to Java Object when request is received by Spring.
 */
public class UpdateRequest {
	private boolean finished;
	private String finishedAt;

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(String finishedAt) {
		this.finishedAt = finishedAt;
	}
	
	
}
