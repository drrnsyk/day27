package vttp2022.workshop27.repositories;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.workshop27.models.EditedReview;
import vttp2022.workshop27.models.Review;

@Repository
public class BoardgameReviewRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public Review insertReview (Review review) {
        return mongoTemplate.insert(review, "reviews");
    }

    public Review insertEditReview (String reviewId, EditedReview editedReview) {

        // find the review by review id = object id
        ObjectId docId = new ObjectId(reviewId);
        // find review, store it into review model by return review class directly
        Review review = mongoTemplate.findById(docId, Review.class, "reviews");
        
        // check if a review already exist. if exist, edit. if does not exist, do nothing/return null
        if (review != null) {
            EditedReview er = new EditedReview();
            er.setComment(editedReview.getComment());
            er.setRating(editedReview.getRating());
            er.setPosted(LocalDateTime.now());

            // check if the review has existing edited reviews. if exist, add on to the list. if does not exist, add new list
            if (review.getListOfEditedReviews() != null) {
                // if list of edited reviews exist, add the new edited review into the list
                review.getListOfEditedReviews().add(er);
            } else {
                // instantiate a new linked list of edited reviews
                List<EditedReview> listOfEditedReviews = new LinkedList<>();
                // add the latest edited review into the linked list
                listOfEditedReviews.add(er);
                // set the new linked list to the review
                review.setListOfEditedReviews(listOfEditedReviews);
            }
            // set the review to is edited true as the review has been edited
            review.setIsEdited(true);
            // save and update the review record in mongodb
            mongoTemplate.save(review, "reviews");
        }
        return review;
    }

    public Review getLatestReview(String reviewId) {

        // find the review by review id = object id
        ObjectId docId = new ObjectId(reviewId);
        // find review, store it into review model by return review class directly
        Review review = mongoTemplate.findById(docId, Review.class, "reviews");

        // check if a review already exist. if exist, edit. if does not exist, do nothing/return null
        if (review != null && review.isEdited()) {
            // set the comment and rating to latest comment and rating for display
            review.setComment(review.getListOfEditedReviews().get(review.getListOfEditedReviews().size()-1).getComment());
            review.setRating(review.getListOfEditedReviews().get(review.getListOfEditedReviews().size()-1).getRating());
            review.setIsEdited(true);
            review.setTimeStamp(LocalDateTime.now());
        } else if (!review.isEdited()) {
            review.setIsEdited(false);
            review.setTimeStamp(LocalDateTime.now());
        }
        return review;
    }
}
