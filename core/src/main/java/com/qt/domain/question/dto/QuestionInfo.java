package com.qt.domain.question.dto;

import com.qt.domain.contest.Contest;
import com.qt.domain.question.Question;
import com.qt.domain.question.Reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class QuestionInfo {

    @NotNull
    private Contest contest;

    @NotNull
    private Integer problemNumber;

    @NotNull
    @Lob
    private String content;

    @Lob
    private Reply reply;

    @Builder
    public QuestionInfo(@NotNull Contest contest, @NotNull Integer problemNumber, @NotNull String content, String response, LocalDateTime createTime) {
        this.contest = contest;
        this.problemNumber = problemNumber;
        this.content = content;
        this.reply = reply;
    }

    public Question toEntity() {
        return new Question(contest, problemNumber, content, reply);
    }

    @Override
    public String toString() {
        return "QuestionInfo{" +
                "contest=" + contest +
                ", problemNumber=" + problemNumber +
                ", content='" + content + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }
}
