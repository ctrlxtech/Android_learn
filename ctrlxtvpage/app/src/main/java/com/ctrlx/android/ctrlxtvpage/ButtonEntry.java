package com.ctrlx.android.ctrlxtvpage;

/**
 * Created by kevin on 1/7/15.
 */

public class ButtonEntry {
    String id;
    String controlInfo;


    public String getID(){
        return id;
    }

    public String getControl(){
        return controlInfo;
    }

    public ButtonEntry(String id, String controlInfo) {
        super();
        this.id = id;
        this.controlInfo = controlInfo;
    }
}
