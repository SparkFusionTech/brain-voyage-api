package com.sparkfusion.quiz.brainvoyage.api.controller.moderation_answer;

import com.sparkfusion.quiz.brainvoyage.api.dto.moderation_answer.GetModerationAnswerDto;
import com.sparkfusion.quiz.brainvoyage.api.service.ModerationAnswerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moderation/quizzes")
public class ModerationAnswerController {

    private final ModerationAnswerService moderationAnswerService;

    public ModerationAnswerController(ModerationAnswerService moderationAnswerService) {
        this.moderationAnswerService = moderationAnswerService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{quizId}")
    public ResponseEntity<GetModerationAnswerDto> readQuizModerationAnswer(
            @PathVariable("quizId") Long quizId
    ) {
        GetModerationAnswerDto getModerationAnswerDto = moderationAnswerService.readModerationAnswerByQuestionId(quizId);
        return new ResponseEntity<>(getModerationAnswerDto, HttpStatus.OK);
    }
}


















