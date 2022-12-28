package io.github.v2compose.network.bean;

import java.io.Serializable;
import java.util.List;

import io.github.v2compose.util.AvatarUtils;
import io.github.v2compose.util.Check;
import io.github.v2compose.util.Utils;
import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by ghui on 17/05/2017.
 * https://www.v2ex.com/my/topics
 */

@Pick("div#Wrapper")
public class TopicStarInfo extends BaseInfo {
    @Pick("div.fr.f12 strong.gray")
    private int total = 0;
    @Pick("div.cell.item")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "TopicStarInfo{" +
                "total=" + total +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean isValid() {
        if (Utils.listSize(items) <= 0) return true;
        return Check.notEmpty(items.get(0).title);
    }

    public static class Item implements Serializable {
        @Pick(value = "td>a[href^=/member]", attr = Attrs.HREF)
        private String userLink;
        @Pick(value = "img.avatar", attr = Attrs.SRC)
        private String avatar;
        @Pick("span.item_title")
        private String title;
        @Pick(value = "span.item_title a", attr = Attrs.HREF)
        private String link;
        @Pick("a[class^=count_]")
        private int commentNum;
        @Pick("a.node")
        private String tag;
        @Pick(value = "a.node", attr = Attrs.HREF)
        private String tagLink;
        @Pick(value = "span.small.fade", attr = Attrs.OWN_TEXT)
        private String time;

        @Override
        public String toString() {
            return "Item{" +
                    "userLink='" + userLink + '\'' +
                    "userName='" + getUserName() + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", commentNum=" + commentNum +
                    ", tag='" + tag + '\'' +
                    ", tagLink='" + tagLink + '\'' +
                    '}';
        }

        public String getTime() {
            //   • •  36 天前  •  最后回复来自
            if (Check.isEmpty(time) || !time.contains("前")) return "";
            time = time.replaceAll(" ", "");
            int endIndex = time.indexOf("前");
            int startIndex = 0;
            for (int i = endIndex - 1; i >= 0; i--) {
                if (time.charAt(i) == '•') {
                    startIndex = i;
                    break;
                }
            }
            return time.substring(startIndex + 1, endIndex + 1).trim();
        }

        public String getUserName() {
            if (Check.isEmpty(userLink)) return null;
            else {
                return userLink.substring(userLink.lastIndexOf("/") + 1);
            }
        }

        public String getUserLink() {
            return userLink;
        }

        public String getAvatar() {
            return AvatarUtils.adjustAvatar(avatar);
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public String getTag() {
            return tag;
        }

        public String getTagLink() {
            return tagLink;
        }
    }
}
