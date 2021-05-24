package com.totalproject;

public class User {
    private String name;
    private int point;

    private int hight_score[];

    public User(String name) {
        this.name = name;
        point = 5;
        hight_score = new int[2];
    }

    public int[] getHight_score() {
        return hight_score;
    }

    public void setHight_score(int first_play, int second_play) {
        this.hight_score[0] = first_play;
        this.hight_score[1] = second_play;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
