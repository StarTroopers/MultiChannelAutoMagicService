package com.fanniemae.starapp.controllers.request;

public class ContactUsBean {

    private String firstName;
    private String lastName;
    private String email;
    private String message;
    private String subject;
    private String name;

    public ContactUsBean() {

    }

    public ContactUsBean(String firstName, String lastName, String email, String message, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.message = message;
        this.subject = subject;
        if (this.name == null || this.name.isEmpty()) {
            this.name = this.firstName + " " + this.lastName;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
