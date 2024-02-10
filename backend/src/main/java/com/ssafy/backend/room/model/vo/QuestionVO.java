package com.ssafy.backend.room.model.vo;

import com.ssafy.backend.room.model.domain.redis.QuestionRedis;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionVO {
    private String questionId;
    private String userId;

    public QuestionVO() {
    }

    public QuestionVO(String questionId, String userId, String session, String data) {
        setQuestionId(questionId);
        setUserId(userId);
        setSession(session);
        setData(data);
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String session;
    private String data;

    public QuestionVO(String userId, String session, String data) {
        this.userId = userId;
        this.session = session;
        this.data = data;
    }

    public QuestionRedis toEntity(){
        return QuestionRedis.builder()
                .userId(this.userId)
                .session(this.session)
                .questionContent(this.data)
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                " questionId : '" + questionId + '\'' +
                ", userId : '" + userId + '\'' +
                ", session: '" + session + '\'' +
                ", data : '" + data + '\'' +
                '}';
    }
}
