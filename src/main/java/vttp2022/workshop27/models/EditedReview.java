package vttp2022.workshop27.models;

import java.time.LocalDateTime;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class EditedReview {
    private String comment;
    private String rating;
    private LocalDateTime posted;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }


    // helper method, to read from model and convert to json object, toJSON
    public JsonObject readModelCreateJsonObj() {
        return Json.createObjectBuilder()
                .add("comment", this.getComment())
                .add("rating", getRating())
                .add("posted", getPosted().toString())
                .build();
    }
}
