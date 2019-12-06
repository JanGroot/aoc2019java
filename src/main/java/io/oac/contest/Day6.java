package io.oac.contest;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 {
    public static void main(String[] args) {
        List<String> lines = getLines();
        Map<String, DefaultMutableTreeNode> nodes = new HashMap<>();
        lines.forEach(s -> {
            String[] split = s.split("\\)");
            nodes.putIfAbsent(split[0], new DefaultMutableTreeNode());
            nodes.putIfAbsent(split[1], new DefaultMutableTreeNode());
            DefaultMutableTreeNode parent = nodes.get(split[0]);
            DefaultMutableTreeNode child = nodes.get(split[1]);
            parent.add(child);
        });
        int total = nodes.values().stream().map(DefaultMutableTreeNode::getLevel).reduce(Integer::sum).get();
        System.out.println(total);
        var me = nodes.get("YOU");
        var santa = nodes.get("SAN");
        var sharedAncestor = (DefaultMutableTreeNode) santa.getSharedAncestor(me);
        int dist = me.getLevel() + santa.getLevel() - (sharedAncestor.getLevel() * 2);
        System.out.println(dist);
    }

    private static List<String> getLines() {
        return Util.getInput("input6");
    }
}

