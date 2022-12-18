package com.example.bt_3_assignment;

import android.content.Context;

import java.io.Serializable;

public class Food implements Serializable {
    private Context context;
    private String title;
    private String description;
    private String guid;
    private String pubDate;
    private String link;
    private String media_content;
    private String media_title;
    private String media_description;

    public Food(){}
    public Food(String title, String description, String guid, String pubDate, String link,String media_content, String media_title, String media_description){
        this.title = title;
        this.description = description;
        this.guid = guid;
        this.pubDate = pubDate;
        this.link = link;
        this.media_content = media_content;
        this.media_title = media_title;
        this.media_description = media_description;

    }
    public Food(Context context, String title){
        context = context;
        title = title;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getGuid(){
        return guid;
    }
    public void setGuid(String guid){
        this.guid = guid;
    }

    public String getPubDate(){
        return pubDate;
    }
    public void setPubDate(String pubDate){
        this.pubDate = pubDate;
    }

    public String getLink(){
        return link;
    }
    public void setLink(String link){
        this.link = link;
    }

    public String getMedia_content(){
        return media_content;
    }
    public void setMedia_content(String media_content){
        this.media_content = media_content;
    }
    public String getMedia_title(){
        return media_title;
    }
    public void setMedia_title(String media_title){
        this.media_title = media_title;
    }
    public String getMedia_description(){
        return media_description;
    }
    public void setMedia_description(String media_description){
        this.media_description = media_description;
    }

}
