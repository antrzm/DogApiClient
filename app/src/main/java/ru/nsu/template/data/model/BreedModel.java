package ru.nsu.template.data.model;

import java.util.List;

public class BreedModel {

    private List<String> message;
    private String status;
    private List<String> name;

    public List<String> getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> breedName) {
        name = breedName;
    }
}
