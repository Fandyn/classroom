package com.entry;

public class Message {
    private String message;
    private boolean state;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Message(String message, boolean state) {
        this.message = message;
        this.state = state;
    }

    public Message(String message, boolean state, Object data) {
        this.message = message;
        this.data = data;
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
