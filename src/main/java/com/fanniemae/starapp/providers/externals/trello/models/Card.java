package com.fanniemae.starapp.providers.externals.trello.models;

public class Card {
	@Override
	public String toString() {
		return "Card [idShort=" + idShort + ", name=" + name + ", id=" + id + "]";
	}

	private float idShort;
	private String name;
	private String id;

	// Getter Methods

	public float getIdShort() {
		return idShort;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	// Setter Methods

	public void setIdShort(float idShort) {
		this.idShort = idShort;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}
}