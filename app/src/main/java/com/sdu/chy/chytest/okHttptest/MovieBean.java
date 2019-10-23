package com.sdu.chy.chytest.okHttptest;

public class MovieBean {

    private String movieName;
    private String coverImg;
    private String summary;

    public MovieBean(){
    }

    public MovieBean(String movieName, String coverImg,String summary){
        this.movieName = movieName;
        this.coverImg = coverImg;
        this.summary = summary;
    }

    public void setMovieName(String movieName){
        this.movieName = movieName;
    }
    public String getMovieName(){
        return this.movieName;
    }

    public void setCoverImg(String coverImg){
        this.coverImg = coverImg;
    }
    public String getCoverImg(){return this.coverImg;}

    public void setSummary(String summary){
        this.summary = summary;
    }
    public String getSummary(){return this.summary;}

}
