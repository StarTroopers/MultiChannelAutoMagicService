package com.fanniemae.starapp.providers.externals.trello.models;

public class MemberCreator {
	private String id;
	private String avatarHash;
	private String fullName;
	private String initials;
	private String username;

	// Getter Methods

	public String getId() {
		return id;
	}

	public String getAvatarHash() {
		return avatarHash;
	}

	public String getFullName() {
		return fullName;
	}

	public String getInitials() {
		return initials;
	}

	public String getUsername() {
		return username;
	}

	// Setter Methods

	public void setId(String id) {
		this.id = id;
	}

	public void setAvatarHash(String avatarHash) {
		this.avatarHash = avatarHash;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}