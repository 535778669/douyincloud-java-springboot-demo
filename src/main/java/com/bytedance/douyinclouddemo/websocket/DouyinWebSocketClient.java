package com.bytedance.douyinclouddemo.websocket;//package org.dy.websocket;//package org.dy.websocket;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//
//import javax.websocket.*;
//import java.net.URI;
//import java.net.http.HttpResponse;
//import java.nio.ByteBuffer;
//import java.util.Timer;
//import java.util.TimerTask;
//
//@ClientEndpoint
//public class DouyinWebSocketClient {
//
//    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
//    private static final String LIVE_URL_DOUYIN = "https://live.douyin.com";
//    private static final String SIGN_API_DOMAIN = "http://129.204.171.194:8081";
//    private static final String SIGN_API_URL = "/Douyin/SignWss";
//    private static final String SIGN_API_KEY = "test-apikey-de9991ea-bf2b-454c-7982-adddfe0581ac-96c0642e-7d1d-87a7-08b2-eff81edae4d3";
//    private static String liveId = "16552558995"; // 替换为实际的直播ID
//    private static String ttwid;
//    private static String roomId;
//    private static String wssUrl;
//    private Session session;
//    private Timer heartbeatTimer;
//
//    public static void main(String[] args) {
//        try {
//            ttwid = getTtwid();
//            String[] roomIdAndUserUniqueId = getRoomId(liveId, ttwid);
//            roomId = roomIdAndUserUniqueId[0];
//            String userUniqueId = roomIdAndUserUniqueId[1];
//            wssUrl = getWss(roomId, userUniqueId);
//
//            // 打印获取到的WebSocket URL
//            System.out.println("Obtained WebSocket URL: " + wssUrl);
//
//            if (wssUrl != null) {
//                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//                container.connectToServer(DouyinWebSocketClient.class, new URI(wssUrl));
//            } else {
//                System.out.println("Failed to get WSS URL.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("Connected to server: " + wssUrl);
//        this.session = session;
//
//        startHeartbeat();
//    }
//
//    @OnMessage
//    public void onMessage(String message) {
//        handleMessage(message);
//    }
//
//    @OnMessage
//    public void onMessage(ByteBuffer message) {
//        // 处理二进制消息
//        handleMessage(message);
//    }
//
//    @OnClose
//    public void onClose(Session session, CloseReason closeReason) {
//        System.out.println("Session closed: " + closeReason);
//        stopHeartbeat();
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        throwable.printStackTrace();
//    }
//
//    private void handleMessage(String message) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            System.out.println("Received message: " + message);
//            JsonNode jsonNode = objectMapper.readTree(message);
//            int type = jsonNode.get("Type").asInt();
//
//            switch (type) {
//                case 1:
//                    // 处理 Type 1 消息
//                    break;
//                case 3:
//                    // 处理 Type 3 消息
//                    break;
//                case 4:
//                    // 处理 Type 4 消息
//                    break;
//                case 5:
//                    // 处理 Type 5 消息
//                    break;
//                case 7:
//                    // 处理 Type 7 消息
//                    break;
//                case 8:
//                    // 处理 Type 8 消息
//                    break;
//                default:
//                    System.out.println("Unknown message type: " + type);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void handleMessage(ByteBuffer buffer) {
//        // 处理二进制消息
//        System.out.println("Received binary message: " + buffer);
//    }
//
//    private void startHeartbeat() {
//        heartbeatTimer = new Timer(true);
//        heartbeatTimer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    byte[] heartbeat = new byte[]{0x3a, 0x02, 0x68, 0x62};
//                    session.getBasicRemote().sendBinary(ByteBuffer.wrap(heartbeat));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 0, 10000);
//    }
//
//    private void stopHeartbeat() {
//        if (heartbeatTimer != null) {
//            heartbeatTimer.cancel();
//        }
//    }
//
//    private static String getTtwid() {
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            HttpGet request = new HttpGet(LIVE_URL_DOUYIN + "/" + liveId);
//            request.setHeader("User-Agent", USER_AGENT);
//            HttpResponse response = client.execute(request);
//            String setCookie = response.getFirstHeader("Set-Cookie").getValue();
//            String ttwid = null;
//
//            for (String cookie : setCookie.split(";")) {
//                if (cookie.startsWith("ttwid=")) {
//                    ttwid = cookie.split("=")[1];
//                    break;
//                }
//            }
//            return ttwid;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private static String[] getRoomId(String liveId, String ttwid) {
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            HttpGet request = new HttpGet(LIVE_URL_DOUYIN + "/" + liveId);
//            request.setHeader("User-Agent", USER_AGENT);
//            request.setHeader("cookie", "ttwid=" + ttwid);
//
//            HttpResponse response = client.execute(request);
//            String responseBody = EntityUtils.toString(response.getEntity());
//            String roomId = null;
//            String userUniqueId = null;
//
//            // 使用正则表达式提取 roomId
//            String roomIdPattern = "roomId\\\":\\\"(\\d+)\\\"";
//            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(roomIdPattern);
//            java.util.regex.Matcher matcher = pattern.matcher(responseBody);
//
//            if (matcher.find()) {
//                roomId = matcher.group(1);
//            }
//
//            // 使用正则表达式提取 userUniqueId
//            String userUniqueIdPattern = "user_unique_id\":\"(\\d+)\"";
//            pattern = java.util.regex.Pattern.compile(userUniqueIdPattern);
//            matcher = pattern.matcher(responseBody);
//
//            if (matcher.find()) {
//                userUniqueId = matcher.group(1);
//            }
//
//            System.out.println("Extracted RoomId: " + roomId);
//            System.out.println("Extracted UserUniqueId: " + userUniqueId);
//
//            return new String[]{roomId, userUniqueId};
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private static String getWss(String roomId, String userUniqueId) {
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            HttpPost request = new HttpPost(SIGN_API_DOMAIN + SIGN_API_URL);
//            request.setHeader("Accept", "*/*");
//            request.setHeader("Content-Type", "application/json;charset=UTF-8");
//
//            String jsonBody = String.format(
//                    "{\"ApiKey\":\"%s\", \"BrowserName\":\"Mozilla\", \"BrowserVersion\":\"%s\", \"RoomId\":\"%s\", \"UserUniqueId\":\"%s\"}",
//                    SIGN_API_KEY, USER_AGENT, roomId, userUniqueId);
//
//            request.setEntity(new StringEntity(jsonBody));
//
//            HttpResponse response = client.execute(request);
//            String responseBody = EntityUtils.toString(response.getEntity());
//
//            // 打印API响应内容
//            System.out.println("API Response: " + responseBody);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(responseBody);
//
//            // 打印解析的关键字段
//            System.out.println("Code: " + jsonNode.get("Code"));
//            System.out.println("Data: " + jsonNode.get("Data"));
//
//            if (jsonNode.has("Code") && jsonNode.get("Code").asInt() == 0) {
//                JsonNode dataNode = jsonNode.get("Data");
//                if (dataNode.has("WssUrl")) {
//                    // 打印返回的所有字段
//                    dataNode.fields().forEachRemaining(field -> {
//                        System.out.println(field.getKey() + ": " + field.getValue());
//                    });
//                    return dataNode.get("WssUrl").asText();
//                } else {
//                    System.out.println("Response missing 'Data' or 'WssUrl'");
//                }
//            } else {
//                System.out.println("Invalid response code or response missing 'Code'");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
