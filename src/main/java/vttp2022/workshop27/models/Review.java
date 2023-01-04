package vttp2022.workshop27.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Review {
    
    // create attributes based on the endpoint
    private String user;
    private String rating;
    private String comment;
    private String id;
    private Integer gameId;
    private LocalDateTime posted;
    private String boardgameName;
    // to fulfill part b, two new attributes needs to be added
    private List<EditedReview> listOfEditedReviews;
    private Boolean isEdited;
    // to fulfill part c, one new attribute needs to be added
    private LocalDateTime timeStamp;

    // create constructors to accomodate automatic input of local date time
    public Review() {
    }

    public Review(String user, String rating, String comment, Integer gameId, String boardgameName) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.gameId = gameId;
        this.posted = LocalDateTime.now();
        this.boardgameName = boardgameName;
        this.isEdited = false;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getGameId() {
        return gameId;
    }
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
    public LocalDateTime getPosted() {
        return posted;
    }
    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }
    public String getBoardgameName() {
        return boardgameName;
    }
    public void setBoardgameName(String boardgameName) {
        this.boardgameName = boardgameName;
    }
    public List<EditedReview> getListOfEditedReviews() {
        return listOfEditedReviews;
    }
    public void setListOfEditedReviews(List<EditedReview> listOfEditedReviews) {
        this.listOfEditedReviews = listOfEditedReviews;
    }

    public boolean isEdited() {
        return isEdited;
    }
    public void setIsEdited(boolean isEdited) {
        this.isEdited = isEdited;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }


    // helper method, read from json document and convert to model
    public static Review readDocumentCreateReview(Document d) {
        Review review = new Review();
        review.setUser(d.getString("user"));
        review.setRating(d.getString("rating"));
        review.setId(d.getObjectId("_id").toString());
        review.setGameId(d.getInteger("gameId"));
        review.setComment(d.getString("comment"));
        LocalDateTime postedDt = Instant.ofEpochMilli(d.getDate("posted").getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        review.setPosted(postedDt);
        review.setBoardgameName(d.getString("name"));
        return review;
    }


    // helper method, to read from model and convert to json object, toJSON
    public JsonObject readModelCreateJsonObj(boolean switchProp) {

        // convert list of edited review into a list of json object using lamda
        // List<JsonObject> js = this.getEdited()
        //     .stream()
        //     .map(c -> c.toJSON())
        //     .toList();

        // convert list of edited review into a list of json object
        List<JsonObject> listOfjo = new LinkedList<>();
        List<EditedReview> listOfEditedReviews = this.getListOfEditedReviews();
        for (int i = 0; i < listOfEditedReviews.size(); i++) {
            // create a json object for each edited review model
            listOfjo.add(listOfEditedReviews.get(i).readModelCreateJsonObj());
        }

        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add("user", getUser());
        jsonObjBuilder.add("rating", getRating());
        jsonObjBuilder.add("comment", getComment());
        jsonObjBuilder.add("_id", getId());
        jsonObjBuilder.add("gameId", getGameId());
        jsonObjBuilder.add("posted", getPosted().toString());
        jsonObjBuilder.add("BoardgameName", getBoardgameName());

        // switcher is to differentiate the requirement between task b and task c
        if (switchProp) {
            if (isEdited()) {
                jsonObjBuilder.add("edited", isEdited());
                jsonObjBuilder.add("timestamp", getTimeStamp().toString());
            }
        } else {
            jsonObjBuilder.add("edited", listOfjo.toString());
        }
        
        return jsonObjBuilder.build();
    }


    // helper method, to read json object and convert to document
    public Document readJsonObjCreateDocument(JsonObject jo) {
        return null;
    }
}
