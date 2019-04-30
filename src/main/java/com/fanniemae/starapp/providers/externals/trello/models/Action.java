package com.fanniemae.starapp.providers.externals.trello.models;

public class Action {
	private String id;
	private String idMemberCreator;
	Data DataObject;
	private String type;
	private String date;
	MemberCreator MemberCreatorObject;

	// Getter Methods

	public String getId() {
		return id;
	}

	public String getIdMemberCreator() {
		return idMemberCreator;
	}

	public Data getData() {
		return DataObject;
	}

	public String getType() {
		return type;
	}

	public String getDate() {
		return date;
	}

	public MemberCreator getMemberCreator() {
		return MemberCreatorObject;
	}

	// Setter Methods

	public void setId(String id) {
		this.id = id;
	}

	public void setIdMemberCreator(String idMemberCreator) {
		this.idMemberCreator = idMemberCreator;
	}

	public void setData(Data dataObject) {
		this.DataObject = dataObject;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setMemberCreator(MemberCreator memberCreatorObject) {
		this.MemberCreatorObject = memberCreatorObject;
	}

	@Override
	public String toString() {
		return "Action [id=" + id + ", idMemberCreator=" + idMemberCreator.toString() + ", DataObject=" + DataObject.toString() + ", type="
				+ type + ", date=" + date + ", MemberCreatorObject=" + MemberCreatorObject.toString() + "]";
	}
}
