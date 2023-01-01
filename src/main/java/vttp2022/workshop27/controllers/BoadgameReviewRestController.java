package vttp2022.workshop27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.workshop27.models.EditedReview;
import vttp2022.workshop27.models.Review;
import vttp2022.workshop27.services.BoardgameReviewService;

@Controller
@RequestMapping("/api/review")
public class BoadgameReviewRestController {

    @Autowired
    private BoardgameReviewService boardgameReviewSvc;
    
    /*
    take in a path variable review id and a request body
    postman command: 
                        PUT localhost:8080/api/review/63b12f17f4ddf911f8535c33
    postman body: 
                        {
                            "comment": "Excellent!",
                            "rating": 10
                        }
    */
    @PutMapping("/{reviewId}") 
    public ResponseEntity<String> insertEditReview(@PathVariable String reviewId, @RequestBody EditedReview editedReview ) {

        Review updatedReview = boardgameReviewSvc.insertEditReview(reviewId, editedReview);
        // an updated review with edited review(s) is returned, null if review was not found
        
        JsonObject jo = Json.createObjectBuilder()
                .add("updatedReview", updatedReview.readModelCreateJsonObj(false))
                .build();
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jo.toString());
    }


    /*
    take in a path variable review id
    postman command: 
                        GET localhost:8080/api/review/63b12f17f4ddf911f8535c33
    */
    @GetMapping("/{reviewId}")
    public ResponseEntity<String> getLatestReview(@PathVariable String reviewId) {

        Review latestReview = boardgameReviewSvc.getLatestReview(reviewId);
        
        JsonObject jo = Json.createObjectBuilder()
                .add("latestReview", latestReview.readModelCreateJsonObj(true))
                .build();                

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jo.toString());
    }

    /*
    take in a path variable review id
    postman command: 
                        GET localhost:8080/api/review/63b1b7b3c457c95302d7269f/history
    */
    @GetMapping("/{reviewId}/history")
    public ResponseEntity<String> getReviewHistory(@PathVariable String reviewId) {

        Review reviewHistory = boardgameReviewSvc.getLatestReview(reviewId);
        
        JsonObject jo = Json.createObjectBuilder()
                .add("reviewHistory", reviewHistory.readModelCreateJsonObj(false))
                .build();                

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jo.toString());
    }


}
