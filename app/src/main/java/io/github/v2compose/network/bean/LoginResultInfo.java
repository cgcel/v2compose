package io.github.v2compose.network.bean;

import io.github.v2compose.util.Check;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by ghui on 16/08/2017.
 */

public class LoginResultInfo extends BaseInfo {
    @Pick(value = "[href^=/member]", attr = "href")
    private String userLink;
    @Pick(value = "img[src*=avatar/]", attr = "src")
    private String avatar;

    @Override
    public boolean isValid() {
        return Check.notEmpty(avatar) && Check.notEmpty(avatar);
    }

    @Override
    public String toString() {
        return "LoginResultInfo{" +
                "userLink='" + userLink + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public String getUserName() {
        if (Check.isEmpty(userLink)) {
            return null;
        }
        return userLink.split("/")[2];
    }

    public String getAvatar() {
        if (Check.isEmpty(avatar)) return null;
        return avatar.replace("normal.png", "large.png");
    }

}
