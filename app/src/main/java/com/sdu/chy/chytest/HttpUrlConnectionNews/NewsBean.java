package com.sdu.chy.chytest.HttpUrlConnectionNews;

public class NewsBean {

    private String title;
    private String description;

    public NewsBean(){
    }

    public NewsBean(String title,String description){
        this.title = title;
        this.description = description;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){return this.description;}

}
