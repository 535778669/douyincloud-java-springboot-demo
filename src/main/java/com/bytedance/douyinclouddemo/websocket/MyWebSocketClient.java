package com.bytedance.douyinclouddemo.websocket;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.bytedance.douyinclouddemo.pojo.Barrage;
import com.bytedance.douyinclouddemo.pojoUtil.BarragePoJo;
import com.bytedance.douyinclouddemo.pojoUtil.Gift;
import com.bytedance.douyinclouddemo.pojoUtil.TypeEntity;
import com.bytedance.douyinclouddemo.service.impl.RedissonService;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.annotation.Resource;
import javax.websocket.*;
import java.util.List;

@ClientEndpoint
public class MyWebSocketClient {

    /*  public static void main(String[] args) {
          WebSocketContainer container = ContainerProvider.getWebSocketContainer();
          String uri = "ws://127.0.0.1:8888";
          System.out.println("Connecting to " + uri);
          try {
              container.connectToServer(MyWebSocketClient.class, URI.create(uri));
          } catch (Exception e) {
              e.printStackTrace();
          }
      }*/
    @Resource
    private RedissonService redissonService;
    private long snowflakeNextId;

    @OnOpen
    public void onOpen(Session session) {
        snowflakeNextId = IdUtil.getSnowflakeNextId();
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(String message) {
        //这个时候应该新创建一个游戏租

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println("输出获取到的信息:" + message);
            int type = objectMapper.readTree(message).get("Type").asInt();
            if (type == 1) {
                //用户进入了直播间
                //Type1Entity type1Entity = objectMapper.readValue(message, Type1Entity.class);
                // 处理 type1Entity
                // System.out.println("走到了1");
                //开始存入数据库信息,
            } else if (type == 3) {
                //用户评论
                TypeEntity types = objectMapper.readValue(message, TypeEntity.class);
                //开始判断用户评论，以及数据
                String content = types.getData().getContent();
                String nickName = types.getData().getUser().getNickName();
                String avatar = types.getData().getUser().getAvatar();
                Barrage barrage;
                switch (content) {
                    case "1":
                    case "红":
                        //开始存入redis中
                        if (ObjectUtil.isEmpty(redissonService.getBarrageFromRedis(snowflakeNextId + "-" + nickName + "-" + 0))) {
                            System.out.println("用户" + nickName + "加入了红方");
                            BarragePoJo barragePoJo = new BarragePoJo(String.valueOf(snowflakeNextId), nickName, avatar, 0, 0, 1, 2, null, 0);
                            redissonService.saveBarrageToRedis(snowflakeNextId + "-" + nickName + "-" + 0, barragePoJo);
                        } else {
                            System.out.println("用户" + nickName + "加入了蓝方,无法再次更改位置了");
                        }
                        break;
                    case "2":
                    case "蓝":
                        if (ObjectUtil.isEmpty(redissonService.getBarrageFromRedis(snowflakeNextId + "-" + nickName + "-" + 0))) {
                            System.out.println("用户" + nickName + "加入了蓝方");
                            BarragePoJo barragePoJo = new BarragePoJo(String.valueOf(snowflakeNextId), nickName, avatar, 0, 0, 2, 2, null, 0);
                            redissonService.saveBarrageToRedis(snowflakeNextId + "-" + nickName + "-" + 0, barragePoJo);
                        } else {
                            System.out.println("用户" + nickName + "加入了红方,无法再次更改位置了");
                        }
                        break;
                    case "上":
                    case "上路":
                        BarragePoJo barrageFromRedis = redissonService.getBarrageFromRedis(snowflakeNextId + "-" + nickName + "-" + 0);
                        if (ObjectUtil.isNotEmpty(barrageFromRedis)) {
                            barrageFromRedis.setUserType(1);
                            redissonService.saveBarrageToRedis(snowflakeNextId + "-" + nickName + "-" + 0, barrageFromRedis);
                        }
                        break;
                    case "中":
                    case "中路":
                        BarragePoJo barrageFromRedis1 = redissonService.getBarrageFromRedis(snowflakeNextId + "-" + nickName + "-" + 0);
                        if (ObjectUtil.isNotEmpty(barrageFromRedis1)) {
                            barrageFromRedis1.setUserType(2);
                            redissonService.saveBarrageToRedis(snowflakeNextId + "-" + nickName + "-" + 0, barrageFromRedis1);
                        }
                        break;
                    case "下":
                    case "下路":
                        BarragePoJo barrageFromRedis2 = redissonService.getBarrageFromRedis(snowflakeNextId + "-" + nickName + "-" + 0);
                        if (ObjectUtil.isNotEmpty(barrageFromRedis2)) {
                            barrageFromRedis2.setUserType(3);
                            redissonService.saveBarrageToRedis(snowflakeNextId + "-" + nickName + "-" + 0, barrageFromRedis2);
                        }
                        break;
                }
                System.out.println("走到了3");
            } else if (type == 4) {
                //用户点赞处理
                TypeEntity type4Entity = objectMapper.readValue(message, TypeEntity.class);
                //获取用户本次点赞数量
                String nickName = type4Entity.getData().getUser().getNickName();
                BarragePoJo barrageFromRedis2 = redissonService.getBarrageFromRedis(snowflakeNextId + "-" + nickName + "-" + 0);
                if (ObjectUtil.isNotEmpty(barrageFromRedis2)) {
                    barrageFromRedis2.setUserNumberOfLikes(barrageFromRedis2.getUserNumberOfLikes() + type4Entity.getData().getCount());
                    redissonService.saveBarrageToRedis(snowflakeNextId + "-" + nickName + "-" + 0, barrageFromRedis2);
                }
                // 处理 type4Entity
                System.out.println("走到了4");
            } else if (type == 5) {
                //赠送礼物
                TypeEntity type4Entity = objectMapper.readValue(message, TypeEntity.class);
                String nickName = type4Entity.getData().getUser().getNickName();
                BarragePoJo barrageFromRedis2 = redissonService.getBarrageFromRedis(snowflakeNextId + "-" + nickName + "-" + 0);
                if (ObjectUtil.isNotEmpty(barrageFromRedis2)) {
                    //礼物ID为关键信息
                    List<Gift> giftList = barrageFromRedis2.getGiftList();
                    if (ObjectUtil.isNotEmpty(giftList)) {
                        boolean st = true;
                        for (Gift item : giftList) {
                            if (item.getGiftId().equals(type4Entity.getData().getGiftId())) {
                                item.setGiftCount(item.getGiftCount() + type4Entity.getData().getCount());
                                st = false;
                            }
                        }
                        if (st) {
                            giftList.add(new Gift(type4Entity.getData().getGiftId(), type4Entity.getData().getGiftName(), type4Entity.getData().getGiftCount(), type4Entity.getData().getDiamondCount()));
                        }
                    }
                }
                // 处理 type4Entity
                System.out.println("走到了5");
            } else if (type == 7) {
                //累计处理信息处理
                //Type1Entity type4Entity = objectMapper.readValue(message, Type1Entity.class);

                //System.out.println("走到了7");
            } else if (type == 8) {
                TypeEntity type4Entity = objectMapper.readValue(message, TypeEntity.class);
                //直播结束
                System.out.println("走到了8");
                //数据结束
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session closed: " + closeReason);
    }


}
