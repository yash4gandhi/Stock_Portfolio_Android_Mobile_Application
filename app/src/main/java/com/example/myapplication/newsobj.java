package com.example.myapplication;

public class newsobj {
    private String news;
    private String img;
    private String channel;
    private String time;

    public newsobj() {
    }

    public newsobj(String time, String news, String img, String channel) {
        this.news = news;
        this.img = img;
        this.channel = channel;
        this.time = time;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
