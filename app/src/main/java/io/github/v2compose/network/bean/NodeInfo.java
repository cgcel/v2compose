package io.github.v2compose.network.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.github.v2compose.util.AvatarUtils;
import io.github.v2compose.util.Check;

/**
 * Created by ghui on 27/05/2017.
 * 节点详情
 * https://www.v2ex.com/api/nodes/show.json?name=qna
 */

public class NodeInfo extends BaseInfo implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("title")
    private String title;
    @SerializedName("topics")
    private int topics;
    @SerializedName("stars")
    private int stars;
    @SerializedName("header")
    private String header = "";
    @SerializedName("created")
    private long created;
    @SerializedName("avatar_large")
    private String avatar;

    @Override
    public String toString() {
        return "NodeInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", topics=" + topics +
                ", stars=" + stars +
                ", header='" + header + '\'' +
                ", created=" + created +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTopics() {
        return topics;
    }

    public void setTopics(int topics) {
        this.topics = topics;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getAvatar() {
        return AvatarUtils.adjustAvatar(avatar);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean isValid() {
        return Check.notEmpty(name);
    }
}
