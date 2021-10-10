package com.example.demo.model;

public class Result {
    private Integer id;
    private String message;
    
    public Result(Integer id, String message) {
        this.id = id;
        this.message = message;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
