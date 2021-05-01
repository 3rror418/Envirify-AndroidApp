package edu.eci.ieti.myapplication.model;

public class CreateBook {

    private String initialDate;
    private String finalDate;
    private String placeId;

    public CreateBook(String initialDate, String finalDate, String placeId) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.placeId = placeId;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public String toString() {
        return "CreateBook{" +
                "initialDate='" + initialDate + '\'' +
                ", finalDate='" + finalDate + '\'' +
                ", placeId='" + placeId + '\'' +
                '}';
    }
}
