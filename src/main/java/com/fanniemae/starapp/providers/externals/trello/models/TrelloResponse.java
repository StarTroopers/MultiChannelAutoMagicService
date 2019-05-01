package com.fanniemae.starapp.providers.externals.trello.models;

public class TrelloResponse {
	Action ActionObject;
	Model ModelObject;

	// Getter Methods

	public Action getAction() {
		return ActionObject;
	}

	public Model getModel() {
		return ModelObject;
	}

	// Setter Methods

	@Override
	public String toString() {
		return "TrelloResponse [ActionObject=" + ActionObject + ", ModelObject=" + ModelObject + "]";
	}

	public void setAction(Action actionObject) {
		this.ActionObject = actionObject;
	}

	public void setModel(Model modelObject) {
		this.ModelObject = modelObject;
	}
}
