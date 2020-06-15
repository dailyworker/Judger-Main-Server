package com.qt.domain.question;

import com.qt.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String replyText;

    private String replier;

    @OneToOne(mappedBy = "reply")
    private Question question;

    public Reply(String replyText, String replier, Question question) {
        this.replyText = replyText;
        this.replier = replier;
        this.question = question;
    }
}
