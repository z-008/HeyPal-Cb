package com.example.apple.heypal;

/**
 * Created by apple on 9/15/17.
 */

public class ListItem {

 private String head;
 private String desc;

    public ListItem(String head, String desc) {
        this.head = head;
        this.desc = desc;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }
}

