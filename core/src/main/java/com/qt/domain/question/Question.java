package com.qt.domain.question;

import com.qt.domain.BaseTimeEntity;
import com.qt.domain.contest.Contest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Contest contest;

    private Integer problemNumber;

    @Lob
    private String content;

    @OneToOne
    @JoinColumn(name = "reply_id")
    private Reply reply;

    public Question(@NotNull Contest contest, @NotNull Integer problemNumber, @NotNull String content, Reply reply) {
        this.contest = contest;
        this.problemNumber = problemNumber;
        this.content = content;
        this.reply = reply;
    }
}
