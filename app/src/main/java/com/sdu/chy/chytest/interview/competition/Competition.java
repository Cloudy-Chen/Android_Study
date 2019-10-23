package com.sdu.chy.chytest.interview.competition;

public class Competition {
    private String id;
    private String name;
    private String time;
    private String team1_name;
    private String team1_score;
    private String team1_avatar;
    private String team2_name;
    private String team2_score;
    private String team2_avatar;

    public Competition(String id, String name, String time, String team1_name, String team1_score, String team1_avatar, String team2_name, String team2_score, String team2_avatar) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.team1_name = team1_name;
        this.team1_score = team1_score;
        this.team1_avatar = team1_avatar;
        this.team2_name = team2_name;
        this.team2_score = team2_score;
        this.team2_avatar = team2_avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeam1_name() {
        return team1_name;
    }

    public void setTeam1_name(String team1_name) {
        this.team1_name = team1_name;
    }

    public String getTeam1_score() {
        return team1_score;
    }

    public void setTeam1_score(String team1_score) {
        this.team1_score = team1_score;
    }

    public String getTeam1_avatar() {
        return team1_avatar;
    }

    public void setTeam1_avatar(String team1_avatar) {
        this.team1_avatar = team1_avatar;
    }

    public String getTeam2_name() {
        return team2_name;
    }

    public void setTeam2_name(String team2_name) {
        this.team2_name = team2_name;
    }

    public String getTeam2_score() {
        return team2_score;
    }

    public void setTeam2_score(String team2_score) {
        this.team2_score = team2_score;
    }

    public String getTeam2_avatar() {
        return team2_avatar;
    }

    public void setTeam2_avatar(String team2_avatar) {
        this.team2_avatar = team2_avatar;
    }
}
