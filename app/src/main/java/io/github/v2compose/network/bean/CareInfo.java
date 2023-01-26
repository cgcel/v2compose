package io.github.v2compose.network.bean;

import java.io.Serializable;
import java.util.List;

import io.github.v2compose.util.AvatarUtils;
import io.github.v2compose.util.Check;
import io.github.v2compose.util.Utils;
import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by ghui on 12/05/2017.
 * https://www.v2ex.com/my/following?p=1
 */

@Pick("div#Wrapper")
public class CareInfo extends BaseInfo {
    @Pick("div.fr.f12 strong.gray")
    private int total;
    @Pick("div.cell.item")
    private List<Item> items;

    @Override
    public String toString() {
        return "CareInfo{" +
                "total=" + total +
                ", items=" + items +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean isValid() {
        if (Utils.listSize(items) <= 0) return true;
        return Check.notEmpty(items.get(0).userName);
    }

    public static class Item implements Serializable {
        @Pick(value = "img.avatar", attr = Attrs.SRC)
        private String avatar;
        @Pick("strong a[href^=/member/]")
        private String userName;
        @Pick(value = "span.small.fade", attr = Attrs.OWN_TEXT)
        private String time;
        @Pick("span.item_title a[href^=/t/]")
        private String title;
        @Pick(value = "span.item_title a[href^=/t/]", attr = Attrs.HREF)
        private String link;
        @Pick("a[class^=count_]")
        private int comentNum;
        @Pick("a.node")
        private String tagName;
        @Pick(value = "a.node", attr = Attrs.HREF)
        private String tagLink;

        @Override
        public String toString() {
            return "Item{" +
                    "avatar='" + avatar + '\'' +
                    ", userName='" + userName + '\'' +
                    ", time='" + getTime() + '\'' +
                    ", title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", comentNum='" + comentNum + '\'' +
                    ", tagName='" + tagName + '\'' +
                    ", tagLink='" + tagLink + '\'' +
                    '}';
        }

        public String getTime() {
            //  •  36 天前  •  最后回复来自
            String result = null;
            try {
                result = time.trim().split("•")[2].trim().replaceAll(" ", "");
            } catch (Exception e) {
            }
            return result;
        }

        public String getAvatar() {
            return AvatarUtils.adjustAvatar(avatar);
        }

        public String getUserName() {
            return userName;
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public int getComentNum() {
            return comentNum;
        }

        public String getTagName() {
            return tagName;
        }

        public String getTagLink() {
            return tagLink;
        }
    }
}
