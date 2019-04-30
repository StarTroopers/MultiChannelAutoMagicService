package com.fanniemae.starapp.providers.externals.trello.models;

public class Model {
	@Override
	public String toString() {
		return "Model [id=" + id + ", name=" + name + ", desc=" + desc + ", closed=" + closed + ", idOrganization="
				+ idOrganization + ", pinned=" + pinned + ", url=" + url + ", PrefsObject=" + PrefsObject.toString()
				+ ", LabelNamesObject=" + LabelNamesObject.toString() + "]";
	}

	private String id;
	private String name;
	private String desc;
	private boolean closed;
	private String idOrganization;
	private boolean pinned;
	private String url;
	Prefs PrefsObject;
	LabelNames LabelNamesObject;

	// Getter Methods

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public boolean getClosed() {
		return closed;
	}

	public String getIdOrganization() {
		return idOrganization;
	}

	public boolean getPinned() {
		return pinned;
	}

	public String getUrl() {
		return url;
	}

	public Prefs getPrefs() {
		return PrefsObject;
	}

	public LabelNames getLabelNames() {
		return LabelNamesObject;
	}

	// Setter Methods

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public void setIdOrganization(String idOrganization) {
		this.idOrganization = idOrganization;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPrefs(Prefs prefsObject) {
		this.PrefsObject = prefsObject;
	}

	public void setLabelNames(LabelNames labelNamesObject) {
		this.LabelNamesObject = labelNamesObject;
	}
}