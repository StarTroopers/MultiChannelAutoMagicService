package com.fanniemae.starapp.providers.externals.trello.models;

public class Old {

    private String idList;
    private String text;
    private String name;

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Old{" +
                "idList='" + idList + '\'' +
                ", text='" + text + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
