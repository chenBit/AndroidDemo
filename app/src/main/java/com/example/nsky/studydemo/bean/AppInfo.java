package com.example.nsky.studydemo.bean;

import java.io.Serializable;

/**
 * Created by NSKY on 2018/7/3.
 */

public class AppInfo implements Serializable{
    public String url;
    public String name;
    public int progress;
    public int status;

    public AppInfo(String name, String url){
        this.name = name;
        this.url = url;
    }
}
