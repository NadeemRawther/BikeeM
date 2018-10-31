package com.nads.bikeem;

import android.graphics.BitmapFactory;

/**
 * Created by Admin on 10/22/2017.
 */
public class News {

    private String headLine;
    private String body;
    private int imageId;

    public void News(){

    }
    public void News(String headLine,String body,int imageId){
        this.imageId = imageId;
        this.headLine = headLine;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getHeadLine() {
        return headLine;
    }

    public int getImageId() {
        return imageId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
