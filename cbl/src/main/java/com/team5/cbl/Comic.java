package com.team5.cbl;

public class Comic {
    private final String title;
    private final Creator creator;

    public Comic(String title, Creator creator) {
        this.title = title;
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public Creator getCreator() {
        return creator;
    }
}
