package edu.eci.ieti.myapplication.model;

import java.util.Date;

public class BookCard {

    private Date initialDate;
    private Date finalDate;
    private Place place;

    public BookCard() {
    }

    public BookCard(Book book) {
        this.initialDate = book.getInitialDate();
        this.finalDate = book.getFinalDate();
        this.place = book.getPlace();
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
