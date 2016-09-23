package com.do79.SxuMiro.model.school_activities;

import com.do79.SxuMiro.model.school_activities.Author;
import com.do79.SxuMiro.model.school_activities.CustomFields;
import com.do79.SxuMiro.model.school_activities.Tags;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class SchoolActivities implements Serializable {

    public static final String URL_FRESH_NEWS = "http://do79.com/sxumiro/test_school_activities/page";

    public static final String URL_FRESH_NEWS_DETAIL = "http://do79.com/sxumiro/test_school_activities/content/";

    //文章id
    private String id;
    //文章标题
    private String title;
    //文章地址
    private String url;
    //发布日期
    private String date;
    //缩略图
    private String thumb_c;
    //评论数
    private String comment_count;
    //作者
    private com.do79.SxuMiro.model.school_activities.Author author;
    //自定义字段
    private com.do79.SxuMiro.model.school_activities.CustomFields custom_fields;
    //标签
    private com.do79.SxuMiro.model.school_activities.Tags tags;

    public SchoolActivities() {
    }

    public static String getUrlFreshNews(int page) {
        return URL_FRESH_NEWS + page + ".html";
    }

    public static String getUrlFreshNewsDetail(String id) {
        return URL_FRESH_NEWS_DETAIL + id + ".html";
    }


    public static ArrayList<SchoolActivities> parse(JSONArray postsArray) {

        ArrayList<SchoolActivities> freshNewses = new ArrayList<>();

        for (int i = 0; i < postsArray.length(); i++) {

            SchoolActivities schoolActivities = new SchoolActivities();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            schoolActivities.setId(jsonObject.optString("id"));
            schoolActivities.setUrl(jsonObject.optString("url"));
            schoolActivities.setTitle(jsonObject.optString("title"));
            schoolActivities.setDate(jsonObject.optString("date"));
            schoolActivities.setComment_count(jsonObject.optString("comment_count"));
            schoolActivities.setAuthor(com.do79.SxuMiro.model.school_activities.Author.parse(jsonObject.optJSONObject("author")));
            schoolActivities.setCustomFields(CustomFields.parse(jsonObject.optJSONObject("custom_fields")));
            schoolActivities.setTags(Tags.parse(jsonObject.optJSONArray("tags")));

            freshNewses.add(schoolActivities);

        }
        return freshNewses;
    }


    public static ArrayList<SchoolActivities> parseCache(JSONArray postsArray) {

        ArrayList<SchoolActivities> schoolActivitiess = new ArrayList<>();

        for (int i = 0; i < postsArray.length(); i++) {

            SchoolActivities schoolActivities = new SchoolActivities();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            schoolActivities.setId(jsonObject.optString("id"));
            schoolActivities.setUrl(jsonObject.optString("url"));
            schoolActivities.setTitle(jsonObject.optString("title"));
            schoolActivities.setDate(jsonObject.optString("date"));
            schoolActivities.setComment_count(jsonObject.optString("comment_count"));
            schoolActivities.setAuthor(Author.parse(jsonObject.optJSONObject("author")));
            schoolActivities.setCustomFields(CustomFields.parseCache(jsonObject.optJSONObject("custom_fields")));
            schoolActivities.setTags(Tags.parseCache(jsonObject.optJSONObject("tags")));

            schoolActivitiess.add(schoolActivities);

        }
        return schoolActivitiess;
    }

    @Override
    public String toString() {
        return "SchoolActivities{" +
                "tags=" + tags +
                ", customFields=" + custom_fields +
                ", author=" + author +
                ", comment_count='" + comment_count + '\'' +
                ", thumb_c='" + thumb_c + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumb_c() {
        return thumb_c;
    }

    public void setThumb_c(String thumb_c) {
        this.thumb_c = thumb_c;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public CustomFields getCustomFields() {
        return custom_fields;
    }

    public void setCustomFields(CustomFields customFields) {
        this.custom_fields = customFields;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }
}
