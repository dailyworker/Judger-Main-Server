package com.qt.question;


import com.qt.domain.question.dto.QuestionInfo;
import com.qt.domain.question.dto.ReplyInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class QuestionController {

    private final QuestionService questionService;
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/contests/{id}/questions")
    public ResponseEntity createQuestion(@PathVariable("id") Long contestId, @ModelAttribute QuestionInfo questionInfo) {
        Long id = questionService.saveQuestion(contestId, questionInfo);
        return ResponseEntity.created(URI.create("/questions/" + id)).build();
    }

    
    @GetMapping("/questions/{id}")
    public ResponseEntity<QuestionInfo> showQuestionInfo(@PathVariable("id") Long questionId) {
        QuestionInfo questionInfo = questionService.questionFindById(questionId);
        return ResponseEntity.ok(questionInfo);
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("id") Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/questions/{id}/replies")
    public ResponseEntity createReply(@PathVariable("id")Long questionId, @ModelAttribute ReplyInfo replyInfo){
        Long id = questionService.saveReply(questionId, replyInfo);
        return ResponseEntity.created(URI.create("/replies/" + id)).build();
    }

    @GetMapping("/replies/{id}")
    public ResponseEntity<ReplyInfo> showReplyInfo(@PathVariable("id") Long replyId){
        ReplyInfo replyInfo = questionService.replyFindById(replyId);
        return ResponseEntity.ok(replyInfo);
    }

    @ExceptionHandler
    public ResponseEntity<?> eventErrorHandler(NotFoundQuestionException exception) {
        return ResponseEntity.notFound().build();
    }
}
