package com.example.pan.showshortmessage;

/**
 * Created by pan on 2017/5/14.
 */

public class Msg {

    public String number;
    public String content;
    public String time;

    public Msg(String number) {
        this.number = number;
        this.content = content;
        this.time = time;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {

        return number;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}

