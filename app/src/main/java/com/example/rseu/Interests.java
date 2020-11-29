package com.example.rseu;

import java.util.ArrayList;

public class Interests {

    public String name;
    public ArrayList<String> interests = new ArrayList<>();

    public Interests() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterests() {
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < interests.size(); i++)
            a.append(interests.get(i)).append("\n");
        return a.toString();
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public boolean getInterestByName(Interests interest, String name) {
        return interest.getName().equals(name);
    }
}
