package io.github.v2compose.network.bean;


import android.text.TextUtils;

import androidx.compose.runtime.Stable;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import io.github.v2compose.util.AvatarUtils;
import io.github.v2compose.util.Check;
import io.github.v2compose.util.UriUtils;
import me.ghui.fruit.annotations.Pick;


/**
 * Created by ghui on 04/04/2017.
 */

@Stable
@Pick("div#Wrapper")
public class NewsInfo extends BaseInfo {

    @Pick(value = "input.super.special.button", attr = "value")
    private String unRead;
    @Pick("div.cell.item")
    private List<Item> items;
    @Pick("form[action=/2fa]")
    private String twoStepStr;

    private boolean isTwoStepError() {
        return Check.notEmpty(twoStepStr) && twoStepStr.contains("两步验证");
    }

    public int getUnReadCount() {
        if (Check.isEmpty(unRead)) return 0;
        else {
            return Integer.parseInt(unRead.split(" ")[0]);
        }
    }

    public List<Item> getItems() {
        return items != null ? items : Collections.emptyList();
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "items=" + items +
                '}';
    }

    @Override
    public boolean isValid() {
        if (isTwoStepError()) return false;
        return Check.isEmpty(items) || Check.notEmpty(items.get(0).userName);
    }

    @Stable
    public static class Item implements Serializable {
        @Pick(value = "span.item_title > a")
        private String title;
        @Pick(value = "span.item_title > a", attr = "href")
        private String linkPath;
        @Pick(value = "td > a > img", attr = "src")
        private String avatar;
        @Pick(value = "td > a", attr = "href")
        private String avatarLink;
        @Pick(value = "span.small.fade > strong > a")
        private String userName;
        @Pick(value = "span.small.fade:last-child", attr = "ownText")
        private String time;
        @Pick(value = "span.small.fade > a")
        private String tagName;
        @Pick(value = "span.small.fade > a", attr = "href")
        private String tagLink;
        @Pick("a[class^=count_]")
        private int replies;

        private String id;

        public String getId() {
            if (TextUtils.isEmpty(id)) {
//                /t/638047#reply0
                id = UriUtils.getLastSegment(linkPath);
            }
            return id;
        }

        public int getReplies() {
            return replies;
        }

        public void setReplies(int replies) {
            this.replies = replies;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLinkPath() {
            return linkPath;
        }


        public void setLinkPath(String linkPath) {
            this.linkPath = linkPath;
        }

        public String getAvatar() {
            return AvatarUtils.adjustAvatar(avatar);
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatarLink() {
            return avatarLink;
        }

        public void setAvatarLink(String avatarLink) {
            this.avatarLink = avatarLink;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTime() {
            if (!Check.isEmpty(time) && time.contains("•")) {
                time = time.split("•")[0];
            }
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public String getTagId() {
            if (Check.isEmpty(tagLink)) return null;
            return tagLink.substring(tagLink.lastIndexOf("/") + 1);
        }

        public String getTagLink() {
            return tagLink;
        }

        public void setTagLink(String tagLink) {
            this.tagLink = tagLink;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", linkPath='" + linkPath + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", avatarLink='" + avatarLink + '\'' +
                    ", userName='" + userName + '\'' +
                    ", time='" + time + '\'' +
                    ", tagName='" + tagName + '\'' +
                    ", tagLink='" + tagLink + '\'' +
                    ", replies=" + replies +
                    '}';
        }

    }

}
