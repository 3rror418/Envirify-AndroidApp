package edu.eci.ieti.myapplication.model;

public class Card {

    private String id;
    private String name;
    private String department;
    private String city;
    private String direction;
    private String description;
    private String urlImage;
    private String owner;
    private int capacity;
    private int habitations;
    private int bathrooms;
    private int score;



    public Card() {
    }

    public Card(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.department = place.getDepartment();
        this.city = place.getCity();
        this.direction = place.getDirection();
        this.description = place.getDescription();
        this.urlImage = place.getUrlImage();
        this.owner = place.getOwner();
        this.capacity = place.getCapacity();
        this.habitations = place.getHabitations();
        this.bathrooms = place.getBathrooms();
    }

    public Card(Place place , int score) {
        this.id = place.getId();
        this.name = place.getName();
        this.department = place.getDepartment();
        this.city = place.getCity();
        this.direction = place.getDirection();
        this.description = place.getDescription();
        this.urlImage = place.getUrlImage();
        this.owner = place.getOwner();
        this.capacity = place.getCapacity();
        this.habitations = place.getHabitations();
        this.bathrooms = place.getBathrooms();
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getHabitations() {
        return habitations;
    }

    public void setHabitations(int habitations) {
        this.habitations = habitations;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }
}
