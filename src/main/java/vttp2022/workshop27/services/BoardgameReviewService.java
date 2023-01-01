package vttp2022.workshop27.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.workshop27.models.EditedReview;
import vttp2022.workshop27.models.Review;
import vttp2022.workshop27.repositories.BoardgameReviewRepository;

@Service
public class BoardgameReviewService {

    @Autowired
    private BoardgameReviewRepository boardgameReviewRepo;
    
    public Review insertReview (Review review) {
        return boardgameReviewRepo.insertReview(review);
    }

    public Review insertEditReview(String reviewId, EditedReview editedReview) {
        return boardgameReviewRepo.insertEditReview(reviewId, editedReview);
    }

    public Review getLatestReview(String reviewId) {
        return boardgameReviewRepo.getLatestReview(reviewId);
    }

}
