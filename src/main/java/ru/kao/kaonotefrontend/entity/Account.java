package ru.kao.kaonotefrontend.entity;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    public Account() {
    }

    public Account(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Account(String email, String firstName, String lastName, List<Note> notes, List<Task> tasks) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.notes = notes;
        this.tasks = tasks;
    }

    public Long id;

    public String email;

    public String firstName;

    public String lastName;

    public List<Note> notes = new ArrayList<>();

    public List<Task> tasks = new ArrayList<>();

}
