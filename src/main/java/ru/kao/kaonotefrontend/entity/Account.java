package ru.kao.kaonotefrontend.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    public Account() {
    }

    public Account(String email, String firstName, String lastName, char[] password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public Account(String email, String firstName, String lastName, List<Note> notes, List<Task> tasks) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.notes = notes;
        this.tasks = tasks;
    }

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private char[] password;

    private List<Note> notes = new ArrayList<>();

    private List<Task> tasks = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
