package com.qt.domain.question.dto;

import com.qt.domain.question.Question;
import com.qt.domain.question.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class ReplyInfo {

    @NotNull
    String replyText;

    @NotNull
    String replier;

    @NotNull
    Question question;

    @Builder
    public ReplyInfo(@NotNull String replyText, @NotNull String replier, @NotNull Question question) {
        this.replyText = replyText;
        this.replier = replier;
        this.question = question;
    }

    public Reply toEntity(){ return new Reply(replyText, replier, question); }

    @Override
    public String toString() {
        return "ReplyInfo{" +
                "replyText='" + replyText + '\'' +
                ", replier='" + replier + '\'' +
                ", question=" + question +
                '}';
    }
}
