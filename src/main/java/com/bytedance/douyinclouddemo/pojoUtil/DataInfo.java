package com.bytedance.douyinclouddemo.pojoUtil;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataInfo {

    @JsonProperty("Count")
    private int count;

    @JsonProperty("Total")
    private int total;

    @JsonProperty("GiftId")
    private int giftId;

    @JsonProperty("GiftName")
    private String giftName;

    @JsonProperty("GroupId")
    private int groupId;

    @JsonProperty("GiftCount")
    private int giftCount;

    @JsonProperty("RepeatCount")
    private int repeatCount;

    @JsonProperty("DiamondCount")
    private int diamondCount;

    @JsonProperty("ToUser")
    private Object toUser; // 你可以指定具体类型

    @JsonProperty("MsgId")
    private long msgId;

    @JsonProperty("MemberCount")
    private int memberCount;

    @JsonProperty("OnlineUserCount")
    private int onlineUserCount;

    @JsonProperty("TotalUserCount")
    private int totalUserCount;

    @JsonProperty("TotalUserCountStr")
    private String totalUserCountStr;

    @JsonProperty("OnlineUserCountStr")
    private String onlineUserCountStr;

    @JsonProperty("User")
    private User user;

    @JsonProperty("Content")
    private String content;

    @JsonProperty("RoomId")
    private long roomId;
}
