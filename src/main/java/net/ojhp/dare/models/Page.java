package net.ojhp.dare.models;

import lombok.Data;

@Data
@Collection("pages")
public class Page {
    private String title;
    private String slug;
    private int order;
    private String content;
}
