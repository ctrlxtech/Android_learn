package com.example.androidclient;

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
