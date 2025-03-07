package com.sparkfusion.quiz.brainvoyage.api.controller.quiz;

import com.sparkfusion.quiz.brainvoyage.api.dto.correct_answer.CorrectAnswer;
import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.GetQuizRatingDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.GetUserQuizRatingDto;
import com.sparkfusion.quiz.brainvoyage.api.service.QuizRatingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quizzes/rating")
public class QuizRatingController {

    private final QuizRatingService quizRatingService;

    public QuizRatingController(QuizRatingService quizRatingService) {
        this.quizRatingService = quizRatingService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CorrectAnswer> updateRating(Long quizId, Integer rating) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        quizRatingService.updateRating(email, quizId, rating);
        return ResponseEntity.ok(CorrectAnswer.createSuccessAnswer());
    }

    @GetMapping("/ofUser")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GetUserQuizRatingDto> readUserQuizRating(Long quizId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(quizRatingService.readUserQuizRating(quizId, email));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GetQuizRatingDto> readQuizRating(Long quizId) {
        return ResponseEntity.ok(quizRatingService.readQuizRating(quizId));
    }
}


























