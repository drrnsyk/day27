package vttp2022.workshop27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.workshop27.models.Review;
import vttp2022.workshop27.services.BoardgameReviewService;

@Controller
@RequestMapping("/review")
public class BoardgameReviewController {

    @Autowired
    private BoardgameReviewService boardgameReviewSvc;

    @PostMapping
    public String insertReview (@RequestBody MultiValueMap<String, String> form, Model model) {
        String user = form.getFirst("name");
        String rating = form.getFirst("rating");
        String comment = form.getFirst("comment");
        String gameId = form.getFirst("gameId");
        String boardgameName = form.getFirst("boardgameName");

        Review review = new Review(user, rating, comment, gameId, boardgameName);

        Review insertedReview = boardgameReviewSvc.insertReview(review);
        model.addAttribute("insertedReview", insertedReview);
        return "reviewsuccess";
    }
    
}
