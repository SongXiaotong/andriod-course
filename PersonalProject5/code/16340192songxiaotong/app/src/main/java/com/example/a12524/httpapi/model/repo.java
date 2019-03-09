package com.example.a12524.httpapi.model;

public class repo {
    private String id;
    private String name;
    private String full_name;
    private String description;
    private Boolean has_issues;
    private int open_issues;

    public String getFull_name() {
        return full_name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getHas_issues() {
        return has_issues;
    }

    public int getOpen_issues() {
        return open_issues;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHas_issues(Boolean has_issues) {
        this.has_issues = has_issues;
    }

    public void setOpen_issues(int open_issues) {
        this.open_issues = open_issues;
    }

    public void setId(String id) {
        this.id = id;
    }
}
