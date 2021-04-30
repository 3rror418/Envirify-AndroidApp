package edu.eci.ieti.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Place {
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
    private List<String> ratings;

    public Place() {
    }

    public Place(String name, String department, String city, String direction, String description, String urlImage, int capacity, int habitations, int bathrooms) {
        this.name = name;
        this.department = department;
        this.city = city;
        this.direction = direction;
        this.description = description;
        this.urlImage = urlImage;
        this.capacity = capacity;
        this.habitations = habitations;
        this.bathrooms = bathrooms;
        this.ratings= new ArrayList<>();
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

    public List<String> getRatings() {
        return ratings;
    }

    public void setRatings(List<String> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", city='" + city + '\'' +
                ", direction='" + direction + '\'' +
                ", description='" + description + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", owner='" + owner + '\'' +
                ", capacity=" + capacity +
                ", habitations=" + habitations +
                ", bathrooms=" + bathrooms +
                ", ratings=" + ratings +
                '}';
    }
}
