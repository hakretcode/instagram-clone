package com.igorlb.instagram.main.gallery;

public class Media {
    private final String path;
    private final int type;
    private long duration;

    public Media(String path, int type) {
        this.path = path;
        this.type = type;
    }

    public Media(String path, int type, long duration) {
        this.path = path;
        this.type = type;
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public int getType() {
        return type;
    }

    public long getDuration() {
        return duration;
    }
}
