package edu.eci.ieti.myapplication.model;

import java.util.Date;

public class Book {
    private String id;
    private Date initialDate;
    private Date finalDate;
    private String userId;
    private Place place;

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", initialDate=" + initialDate +
                ", finalDate=" + finalDate +
                ", userId='" + userId + '\'' +
                ", place=" + place +
                '}';
    }
}
