package com.example.appenquetes1.dto.admin;

import java.util.List;

public class BatchActionRequest {
    private List<Integer> ids;
    private String comment;

    // getters and setters
    public List<Integer> getIds() { return ids; }
    public void setIds(List<Integer> ids) { this.ids = ids; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}