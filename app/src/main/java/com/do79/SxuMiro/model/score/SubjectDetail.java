package com.do79.SxuMiro.model.score;

public class SubjectDetail {
    String course_name;
    String credit_hour;
    String gpa;
    String property;
    String score;
    String score_next;

    public String getScore_next() {
        return score_next;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getCredit_hour() {
        return credit_hour;
    }

    public String getGpa() {
        return gpa;
    }

    public String getProperty() {
        return property;
    }

    public String getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "SubjectDetail{" +
                "course_name='" + course_name + '\'' +
                ", credit_hour='" + credit_hour + '\'' +
                ", gpa='" + gpa + '\'' +
                ", property='" + property + '\'' +
                ", score='" + score + '\'' +
                ", score_next='" + score_next + '\'' +
                '}';
    }
}
