package net.ojhp.dare.models;

import lombok.Data;

import java.util.Map;

@Data
@Collection("reply-questions")
public class ReplyQuestion {
    private String name;
    private String label;
    private boolean required;
    private int order;
    private String type;
    private Map<String, Object> options;
}
