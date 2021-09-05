package me.obrecht.developerslife.model;

import com.google.gson.annotations.SerializedName;

public class Post {
    int id;

    @SerializedName("type")
    Post.Type type;
    String description;
    String gifURL;
    String embedId;

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGifURL() {
        return gifURL;
    }

    public void setGifURL(String gifURL) {
        this.gifURL = gifURL;
    }

    public String getEmbedId() {
        return embedId;
    }

    public void setEmbedId(String embedId) {
        this.embedId = embedId;
    }

    public enum Type {
        @SerializedName("gif")
        GIF,
        @SerializedName("coub")
        COUB
    }
}
