package com.example.gszs.bean;

public class Dynamics {
    private int did;
    private int uid;
    private int tid;
    private String content;
    private String likecount;
    private String createtime;
    private int isdel;
    private String nickname;
    private String time;


    public Dynamics() {
        this.did = did;
        this.uid = uid;
        this.tid = tid;
        this.content = content;
        this.likecount = likecount;
        this.createtime = createtime;
        this.isdel = isdel;
        this.nickname = nickname;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTime() {
        return time;
    }
}
