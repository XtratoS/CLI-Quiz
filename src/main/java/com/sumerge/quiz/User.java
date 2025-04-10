package com.sumerge.quiz;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Integer> responses;

    public User() {
        this.responses = new ArrayList<>();
    }

    public void addResponse(Integer response) {
        responses.add(response);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getResponses() {
        return responses;
    }

    public void setResponses(List<Integer> responses) {
        this.responses = responses;
    }
}
