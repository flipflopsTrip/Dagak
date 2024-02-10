package com.ssafy.backend.user.model.domain;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.ssafy.backend.common.response.BaseResponseStatus.INSUFFICIENT_POINT;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@ToString
@Getter
public class User {


    @Column
    private Integer userPoint, userTotalStudyTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mokkojiId")
    private Mokkoji mokkojiId;


    @Column
    private String userPassword, userName,
            modifyUserPasswordTime, userPhonenumber, userBirthday,
            userEmail, userNickname, userPicture, todayDagakId, userStatusMessage, kakaoEmail, googleEmail;

    @Column
    private LocalDateTime createdDate;

    @Column
    @Id
    private String userId;

    public boolean checkPassword(String encryptedPassword) {
        return this.userPassword.equals(encryptedPassword);
    }

    public void usePoint(int usePoint) {
        if(this.userPoint - usePoint <0) throw new BaseException(INSUFFICIENT_POINT);
        this.userPoint -= usePoint;
    }

    public void saveMokkoji(Mokkoji mokkoji) {
        this.mokkojiId = mokkoji;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public User(String userId) {
        if (userId != null) {
            this.userId = userId;
        } else {
            // do something...
        }
    }

    public void setKakaoEmail(String kakaoEmail) {
        this.kakaoEmail = kakaoEmail;
    }

    public void setGoogleEmail(String googleEmail) {
        this.googleEmail = googleEmail;
    }

    public void setUserEmail(String newEmail) { this.userEmail=newEmail;
    }

    public void setUserStatusMessage(String userStatusMessage) {
        this.userStatusMessage = userStatusMessage;
    }
    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }
    public void changeImage(String url) {
        this.userPicture = url;
    }
}
