package com.hltt;

public class Package {
    public int code;
    public String message;
    public Object data;

    public Package(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Package(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public static Package error(int code, String message){
        return new Package(code, message, null);
    }

    public static Package success(String message){
        return new Package(0, message, null);
    }

    public static Package success(String message, Object data){
        return new Package(0, message, data);
    }
}
