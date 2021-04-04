package com.igorlb.instagram.main.gallery;

public class Media {
    private final String path;
    private final int type;
    private final String folder;
    private long duration;

    public Media(String path, int type, String folder) {
        this.path = path;
        this.type = type;
        this.folder = folder;
    }

    public Media(String path, int type, String folder, long duration) {
        this.path = path;
        this.type = type;
        this.folder = folder;
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public int getType() {
        return type;
    }

    public String getFolder() {
        return folder;
    }

    public long getDuration() {
        return duration;
    }
}
