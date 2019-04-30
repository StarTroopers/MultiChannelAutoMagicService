package com.fanniemae.starapp.providers.externals.trello.models;

public class Board {
	private String name;
	private String id;

	// Getter Methods

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	// Setter Methods

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Board [name=" + name + ", id=" + id + "]";
	}
}