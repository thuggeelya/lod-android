package com.example.rseu;

import java.util.ArrayList;

public class Team {
    public String name;
    public String field;
    public ArrayList<String> users;

    public Team () {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ArrayList<String> getUsers() {
        return this.users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public boolean getTeamByName(Team team, String name) {
        return team.getName().equals(name);
    }
}