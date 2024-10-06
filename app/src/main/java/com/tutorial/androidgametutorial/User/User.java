package com.tutorial.androidgametutorial.User;

import java.util.Date;

public class User {
    String username;
    String password;
    String UID;
    String progression;
    Date sessionExpiryDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public User() {
    }

    public User(String username, String password, String UID, String progression) {
        this.username = username;
        this.password = password;
        this.UID = UID;
        this.progression = progression;
    }

    public String getProgression() {
        return progression;
    }

    public void setProgression(String progression) {
        this.progression = progression;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }
}
