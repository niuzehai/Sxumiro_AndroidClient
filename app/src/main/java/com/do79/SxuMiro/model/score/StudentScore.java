package com.do79.SxuMiro.model.score;

import java.util.ArrayList;

public class StudentScore {
    public static final String URL_STUDENT_SCORE = "http://do79.com/sxumiro/score.html";

    String date;
    String id;
    String name;
    String status;
    ArrayList<ScoreDetail> score_detail;

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<ScoreDetail> getScore_detail() {
        return score_detail;
    }

    @Override
    public String toString() {
        return "StudentScore{" +
                "date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", score_detail=" + score_detail +
                '}';
    }
}
