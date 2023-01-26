package io.github.v2compose.network.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import io.github.v2compose.util.Check;
import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by ghui on 05/06/2017.
 */

@Pick("div#Wrapper")
public class CreateTopicPageInfo extends BaseInfo {
    @Pick(value = "input[name=once]", attr = "value")
    private String once;
    @Pick(value = "a.node")
    private List<HotTitle> hotNodesText;
    @Pick("div.problem")
    private Problem problem;
    private LinkedHashSet<String> hotIds;

    public Problem getProblem() {
        return problem;
    }

    public Map<String, String> toPostMap(String title, String content, String nodeId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
        map.put("node_name", nodeId);
        map.put("once", once);
        return map;
    }

    @Override
    public String toString() {
        return "CreateTopicPageInfo{" +
                "once='" + once + '\'' +
                ", hotNodes=" + hotNodesText +
                '}';
    }

    @Override
    public boolean isValid() {
        return Check.notEmpty(once);
    }

    public LinkedHashSet<String> getHotNodeIds() {
        if (hotIds != null) return hotIds;
        hotIds = new LinkedHashSet<>();
        for (HotTitle hotTitle : hotNodesText) {
            String idText = hotTitle.href;
            String id = idText.substring(idText.indexOf("'") + 1, idText.lastIndexOf("'"));
            hotIds.add(id);
        }
        return hotIds;
    }

    public static class HotTitle implements Serializable {
        @Pick(attr = Attrs.HREF)
        public String href;
    }

    public static class Problem implements Serializable {
        @Pick(attr = Attrs.OWN_TEXT)
        private String title;
        @Pick("ul li")
        private List<String> tips;

        public boolean isEmpty() {
            return Check.isEmpty(tips) && Check.isEmpty(title);
        }

        public List<String> getTips() {
            return tips;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "Problem{" +
                    "title='" + title + '\'' +
                    ", tips=" + tips +
                    '}';
        }
    }
}
