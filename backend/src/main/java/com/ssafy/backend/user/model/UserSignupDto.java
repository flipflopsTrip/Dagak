package com.ssafy.backend.user.model;

import com.ssafy.backend.user.domain.User;

public class UserSignupDto {
    private String userId, userBirthday, userName, userPassword, userPhonenumber, userEmail, userNickname;

    public UserSignupDto(String userId, String userBirthday, String userName, String userPassword, String userPhonenumber, String userEmail, String userNickname) {
        setUserId(userId);
        setUserBirthday(userBirthday);
        setUserName(userName);
        setUserPassword(userPassword);
        setUserPhonenumber(userPhonenumber);
        setUserEmail(userEmail);
        setUserNickname(userNickname);
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UserSignupDto{" +
                "userId='" + userId + '\'' +
                '}';
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhonenumber() {
        return userPhonenumber;
    }

    public void setUserPhonenumber(String userPhonenumber) {
        this.userPhonenumber = userPhonenumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public User toEntity() {
        return User.builder().
                userId(this.userId).
                userPassword(this.userPassword).
                userName(this.userName).
                userEmail(this.userEmail).build();
    }
    public UserSignupDto(){

    }
    public UserSignupDto(String userId) {
        this.userId = userId;
    }
}
