package io.github.v2compose.network.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;
import io.github.v2compose.util.AvatarUtils;
import io.github.v2compose.util.Check;
import io.github.v2compose.util.Utils;

/**
 * Created by ghui on 01/06/2017.
 * https://www.v2ex.com/member/ghui
 */

@Pick("div#Wrapper")
public class UserPageInfo extends BaseInfo {
    @Pick("h1")
    private String userName;
    @Pick(value = "img.avatar", attr = Attrs.SRC)
    private String avatar;
    @Pick("td[valign=top] > span.gray")
    private String desc;
    @Pick("strong.online")
    private String online;
    @Pick(value = "div.fr input", attr = "onclick")
    private String followOnClick;
    @Pick(value = "div.fr input[value*=lock]", attr = "onclick")
    private String blockOnClick;
    @Pick("div.box:has(div.cell_tabs) > div.cell.item")
    private List<TopicItem> topicItems;
    @Pick("div.box:last-child > div.dock_area")
    private List<ReplyDockItem> dockItems;
    @Pick("div.box:last-child div.reply_content")
    private List<ReplyContentItem> replyContentItems;

    private List<Item> items;
    private List<ReplyItem> replyItems;


    public boolean hadFollowed() {
        return Check.notEmpty(followOnClick) && followOnClick.contains("取消");
    }

    public boolean hadBlocked() {
        return Check.notEmpty(blockOnClick) && blockOnClick.contains("unblock");
    }

    public void updateBlockUrl(boolean toBlock) {
        if (toBlock) {
            blockOnClick = blockOnClick.replace("unblock", "block");
        } else {
            blockOnClick = blockOnClick.replace("block", "unblock");
        }
    }

    //    if (confirm('确认要取消对 diskerjtr 的关注？')) { location.href = '/unfollow/128373?once=15154'; }
    public String getFollowUrl() {
        return getUrl(followOnClick);
    }

    //    if (confirm('确认要解除对 diskerjtr 的屏蔽？')) { location.href = '/unblock/128373?t=1456813618'; }
    public String getBlockUrl() {
        return getUrl(blockOnClick);
    }

    private String getUrl(String onclick) {
        if (Check.notEmpty(onclick)) {
            String reg = "{ location.href = '";
            int start = onclick.indexOf(reg) + reg.length();
            int end = onclick.lastIndexOf("'");
            return onclick.substring(start, end);
        }
        return null;
    }

    private List<ReplyItem> getReplyItems() {
        if (Check.isEmpty(dockItems)) return null;
        if (replyItems == null) {
            replyItems = new ArrayList<>(dockItems.size());
        } else {
            replyItems.clear();
        }
        for (int i = 0; i < dockItems.size(); i++) {
            replyItems.add(new ReplyItem(dockItems.get(i), replyContentItems.get(i)));
        }
        return replyItems;
    }

    public List<Item> getItems() {
        if (items == null) {
            items = new ArrayList<>(Utils.listSize(dockItems) + Utils.listSize(replyContentItems));
        } else {
            items.clear();
        }

        if (Check.notEmpty(topicItems)) {
            items.addAll(topicItems);
        }
        List<ReplyItem> replyItems = getReplyItems();
        if (Check.notEmpty(replyItems)) {
            items.addAll(replyItems);
        }
        return items;
    }

    public boolean isOnline() {
        return Check.notEmpty(online) && online.equals("ONLINE");
    }


    public String getUserName() {
        return userName;
    }

    public String getAvatar() {
        return AvatarUtils.adjustAvatar(avatar);
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "UserPageInfo{" +
                "userName='" + userName + '\'' +
                ", followOnClick='" + followOnClick + '\'' +
                ", blockOnClick='" + blockOnClick + '\'' +
                ", avatar='" + avatar + '\'' +
                ", desc='" + desc + '\'' +
                ", online='" + online + '\'' +
                ", topicItems=" + topicItems +
                ", dockItems=" + dockItems +
                ", replyContentItems=" + replyContentItems +
                ", items=" + items +
                ", replyItems=" + replyItems +
                '}';
    }

    @Override
    public boolean isValid() {
        return Check.notEmpty(userName);
    }

    public abstract static class Item implements Serializable {
        public abstract String getTopicLink();
    }

    public static class TopicItem extends Item {
        @Pick("strong > a[href^=/member/]:first-child")
        private String userName;
        @Pick("a.node")
        private String tag;
        @Pick(value = "a.node", attr = Attrs.HREF)
        private String tagLink;
        @Pick("span.item_title")
        private String title;
        @Pick(value = "span.item_title a", attr = Attrs.HREF)
        private String link;
        @Pick("span.small.fade:last-child")
        private String time;
        @Pick("a[class^=count_]")
        private int replyNum;

        @Override
        public String toString() {
            return "TopicItem{" +
                    "userName='" + userName + '\'' +
                    ", tag='" + tag + '\'' +
                    ", tagLink='" + tagLink + '\'' +
                    ", title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", time='" + time + '\'' +
                    ", replyNum=" + replyNum +
                    '}';
        }

        public String getUserName() {
            return userName;
        }

        public String getTag() {
            return tag;
        }

        public String getTagLink() {
            return tagLink;
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public String getTime() {
            return time;
        }

        public int getReplyNum() {
            return replyNum;
        }

        @Override
        public String getTopicLink() {
            return link;
        }
    }

    public static class ReplyItem extends Item {
        private ReplyDockItem dockItem;
        private ReplyContentItem contentItem;

        public ReplyItem(ReplyDockItem dockItem, ReplyContentItem contentItem) {
            this.dockItem = dockItem;
            this.contentItem = contentItem;
        }

        @Override
        public String toString() {
            return "ReplyItem{" +
                    "dockItem=" + dockItem +
                    ", contentItem=" + contentItem +
                    '}';
        }

        public String getTitle() {
            return dockItem.getTitle();
        }

        public String getLink() {
            return dockItem.getLink();
        }

        public String getTime() {
            return dockItem.getTime();
        }

        public String getContent() {
            return contentItem.getContent();
        }

        @Override
        public String getTopicLink() {
            return dockItem.getLink();
        }
    }

    private static class ReplyDockItem implements Serializable {
        @Pick("span.gray")
        private String title;
        @Pick(value = "span.gray > a", attr = Attrs.HREF)
        private String link;
        @Pick("span.fade")
        private String time;

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public String getTime() {
            return time;
        }

        @Override
        public String toString() {
            return "ReplyDockItem{" +
                    "title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    private static class ReplyContentItem implements Serializable {
        @Pick(attr = Attrs.INNER_HTML)
        private String content;

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "ReplyContentItem{" +
                    "content='" + content + '\'' +
                    '}';
        }
    }
}
