package edu.eci.ieti.myapplication.model;

public class ReviewWrapper {

    private String comment;
    private Integer qualification;
    private String owner;

    public ReviewWrapper( String comment, Integer qualification, String owner) {
        this.comment = comment;
        this.qualification = qualification;
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getQualification() {
        return qualification;
    }

    public void setQualification(Integer qualification) {
        this.qualification = qualification;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
