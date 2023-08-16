package ru.kao.kaonotefrontend.entity;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    public String path;

    public String text;

    public Date dateStart;

    public Date dateEnd;

    public Long priority;

    public Boolean isDone;

    public Account account;
}
