package ru.digitalhabbits.homework1.wikimodel;

import java.util.Map;

public class Query {

    private Map<String, Page> pages;


    public Map<String, Page> getPages() {
        return pages;
    }

    public void setPages(Map<String, Page> pages) {
        this.pages = pages;
    }
}