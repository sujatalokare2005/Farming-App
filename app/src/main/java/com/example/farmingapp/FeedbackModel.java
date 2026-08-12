package com.example.farmingapp;

public class FeedbackModel {
    private String name;
    private String feedback;

    public FeedbackModel() { }

    public FeedbackModel(String name, String feedback) {
        this.name = name;
        this.feedback = feedback;
    }

    public String getName() {
        return name;
    }

    public String getFeedback() {
        return feedback;
    }
}
