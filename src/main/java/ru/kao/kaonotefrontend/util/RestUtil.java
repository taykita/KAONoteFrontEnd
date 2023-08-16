package ru.kao.kaonotefrontend.util;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Queue;

public class RestUtil {
    String body = "{\"path\" : \"/account/admin@admin\"," +
            "\"method\" : \"GET\"}";
    public static String bodyConstruct(Queue<Pair<String, String>> bodyNodes) {
        StringBuilder body = new StringBuilder();
        Pair<String, String> firstNode = bodyNodes.poll();

        body.append('{')
                .append('\"').append(firstNode.getLeft()).append('\"')
                .append(" : ")
                .append('\"').append(firstNode.getRight()).append('\"');
        bodyNodes.forEach(node -> body.append(',')
                .append('\"').append(node.getLeft()).append('\"')
                .append(" : ")
                .append('\"').append(node.getRight()).append('\"'));
        body.append('}');

        return body.toString();
    }
}
