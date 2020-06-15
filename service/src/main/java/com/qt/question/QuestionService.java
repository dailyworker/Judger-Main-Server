package com.qt.question;

import com.qt.domain.contest.Contest;
import com.qt.domain.question.Question;
import com.qt.domain.question.Reply;
import com.qt.domain.question.dto.QuestionInfo;
import com.qt.domain.question.dto.ReplyInfo;
import com.qt.ext.ContestRepository;
import com.qt.ext.NotFoundContestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionService {

    private final ContestRepository contestRepository;
    private final QuestionRepository questionRepository;
    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    public QuestionService(ContestRepository contestRepository, QuestionRepository questionRepository, ReplyRepository replyRepository, ModelMapper modelMapper) {
        this.contestRepository = contestRepository;
        this.questionRepository = questionRepository;
        this.replyRepository = replyRepository;
        this.modelMapper = modelMapper;
    }

    public Long saveQuestion(Long contestId, QuestionInfo questionInfo) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(NotFoundContestException::new);

        questionInfo.setContest(contest);
        Question question = questionInfo.toEntity();

        return questionRepository.save(question).getId();
    }

    public Long saveReply(Long questionId, ReplyInfo replyInfo) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(NotFoundReplyException::new);

        replyInfo.setQuestion(question);
        Reply reply = replyInfo.toEntity();

        return replyRepository.save(reply).getId();
    }

    @Transactional(readOnly = true)
    public QuestionInfo questionFindById(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(NotFoundQuestionException::new);
        return modelMapper.map(question, QuestionInfo.class);
    }

    @Transactional(readOnly = true)
    public ReplyInfo replyFindById(Long replyId){
        Reply reply = replyRepository.findById(replyId).orElseThrow(NotFoundReplyException::new);
        return modelMapper.map(reply, ReplyInfo.class);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
