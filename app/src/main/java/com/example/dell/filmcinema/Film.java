package com.example.dell.filmcinema;

import java.io.Serializable;
import java.util.Date;

public class Film implements Serializable{
    String name;
    String content;
    String image;
    String start;
    String end;
    String date;
    String category;
    String actor;
    String cinema;

    public Film() {
    }

    public Film(String name, String content, String image, String start, String end, String date, String category, String actor, String cinema) {
        this.name = name;
        this.content = content;
        this.image = image;
        this.start = start;
        this.end = end;
        this.date = date;
        this.category = category;
        this.actor = actor;
        this.cinema = cinema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }
}

