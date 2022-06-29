package com.example.clothme.Models;
//historyNumber integer, username text,occasion text,topId text,bottomId text,outer Id text
public class HistoryModel {
    int historyId;
    String username;
    String occasion;
    String date;
    String time;
    String location;
    String topId;
    String bottomId;
    String outerId;

    public HistoryModel(int historyId, String username, String occasion, String date, String time, String location, String topId, String bottomId, String outerId) {
        this.historyId = historyId;
        this.username = username;
        this.occasion = occasion;
        this.date = date;
        this.time = time;
        this.location = location;
        this.topId = topId;
        this.bottomId = bottomId;
        this.outerId = outerId;
    }

    public int getHistoryId() {
        return historyId;
    }

    public String getUsername() {
        return username;
    }

    public String getOccasion() {
        return occasion;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getTopId() {
        return topId;
    }

    public String getBottomId() {
        return bottomId;
    }

    public String getOuterId() {
        return outerId;
    }
}
