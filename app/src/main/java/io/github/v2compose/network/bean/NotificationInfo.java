package io.github.v2compose.network.bean;

import java.io.Serializable;
import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;
import io.github.v2compose.network.NetConstants;
import io.github.v2compose.util.AvatarUtils;
import io.github.v2compose.util.Check;
import io.github.v2compose.util.Utils;

/**
 * Created by ghui on 10/05/2017.
 */

@Pick("div#Main div.box")
public class NotificationInfo extends BaseInfo {
    @Pick("div.fr.f12 strong")
    private int total;
    @Pick("div.cell[id^=n_]")
    private List<Reply> replies;

    public int getTotal() {
        return total;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "NotificationInfo{" +
                "total=" + total +
                ", replies=" + replies +
                '}';
    }

    @Override
    public boolean isValid() {
        if (Utils.listSize(replies) <= 0) return true;
        return Check.notEmpty(replies.get(0).name);
    }

    public static class Reply implements Serializable {
        @Pick("a[href^=/member/] strong")
        private String name;
        @Pick(value = "a[href^=/member/] img", attr = Attrs.SRC)
        private String avatar;
        @Pick(value = "span.fade")
        private String title;
        @Pick(value = "a[href^=/t/]", attr = Attrs.HREF)
        private String link;
        @Pick(value = "div.payload", attr = Attrs.INNER_HTML)
        private String content;
        @Pick("span.snow")
        private String time;

        @Override
        public String toString() {
            return "Reply{" +
                    "name='" + name + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", content='" + content + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }

        public String getLink() {
            return NetConstants.BASE_URL + link;
        }


        public String getTitle() {
            if (Check.notEmpty(title))
                return title.replaceFirst(name, "").trim();
            return title;
        }

        public String getName() {
            return name;
        }

        public String getAvatar() {
            return AvatarUtils.adjustAvatar(avatar);
        }

        public String getContent() {
            return content;
        }

        public String getTime() {
            return time;
        }
    }

}
