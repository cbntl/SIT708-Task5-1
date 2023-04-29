package com.example.sit708_task5_1;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class NewsItem implements Serializable {
        String title, description;

    public NewsItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static List<NewsItem> storyList() {
        List<NewsItem> storyList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            String title, description;
            title = "Story Article " + (i+1);
            description = "This story article #" + (i+1) + " has an exciting story.";
            NewsItem story = new NewsItem(title, description);
            storyList.add(story);
        }
        return storyList;
    }

    public static List<NewsItem> newsList() {
        List<NewsItem> newsList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            String title, description;
            title = "News Article " + (i+1);
            description = "This news article #" + (i+1)  + " has an exciting news / events.";
            NewsItem newsItem = new NewsItem(title, description);
            newsList.add(newsItem);
        }
        return newsList;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }



}
