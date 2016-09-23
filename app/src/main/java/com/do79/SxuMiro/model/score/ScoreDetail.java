package com.do79.SxuMiro.model.score;

import java.util.ArrayList;


public class ScoreDetail {

    String item;
    ArrayList<SubjectDetail> score_list;

    public String getItem() {
        return item;
    }

    public ArrayList<SubjectDetail> getScore_list() {
        return score_list;
    }

    @Override
    public String toString() {
        return "ScoreDetail{" +
                "item='" + item + '\'' +
                ", score_list=" + score_list +
                '}';
    }
}
