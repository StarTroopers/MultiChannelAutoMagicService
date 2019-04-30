package com.fanniemae.starapp.providers.externals.trello.models;

public class Prefs {
	private String permissionLevel;
	private String voting;
	private String comments;
	private String invitations;
	private boolean selfJoin;
	private boolean cardCovers;
	private boolean canBePublic;
	private boolean canBeOrg;
	private boolean canBePrivate;
	private boolean canInvite;

	// Getter Methods

	public String getPermissionLevel() {
		return permissionLevel;
	}

	public String getVoting() {
		return voting;
	}

	public String getComments() {
		return comments;
	}

	public String getInvitations() {
		return invitations;
	}

	public boolean getSelfJoin() {
		return selfJoin;
	}

	public boolean getCardCovers() {
		return cardCovers;
	}

	public boolean getCanBePublic() {
		return canBePublic;
	}

	public boolean getCanBeOrg() {
		return canBeOrg;
	}

	public boolean getCanBePrivate() {
		return canBePrivate;
	}

	public boolean getCanInvite() {
		return canInvite;
	}

	// Setter Methods

	public void setPermissionLevel(String permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	public void setVoting(String voting) {
		this.voting = voting;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setInvitations(String invitations) {
		this.invitations = invitations;
	}

	public void setSelfJoin(boolean selfJoin) {
		this.selfJoin = selfJoin;
	}

	public void setCardCovers(boolean cardCovers) {
		this.cardCovers = cardCovers;
	}

	public void setCanBePublic(boolean canBePublic) {
		this.canBePublic = canBePublic;
	}

	public void setCanBeOrg(boolean canBeOrg) {
		this.canBeOrg = canBeOrg;
	}

	public void setCanBePrivate(boolean canBePrivate) {
		this.canBePrivate = canBePrivate;
	}

	public void setCanInvite(boolean canInvite) {
		this.canInvite = canInvite;
	}

	@Override
	public String toString() {
		return "Prefs [permissionLevel=" + permissionLevel + ", voting=" + voting + ", comments=" + comments
				+ ", invitations=" + invitations + ", selfJoin=" + selfJoin + ", cardCovers=" + cardCovers
				+ ", canBePublic=" + canBePublic + ", canBeOrg=" + canBeOrg + ", canBePrivate=" + canBePrivate
				+ ", canInvite=" + canInvite + "]";
	}
}
