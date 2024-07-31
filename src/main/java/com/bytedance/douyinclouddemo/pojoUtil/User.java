package com.bytedance.douyinclouddemo.pojoUtil;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @JsonProperty("FollowingCount")
    private int followingCount;

    @JsonProperty("Id")
    private long id;

    @JsonProperty("ShortId")
    private String shortId;

    @JsonProperty("DisplayId")
    private String displayId;

    @JsonProperty("NickName")
    private String nickName;

    @JsonProperty("Level")
    private int level;

    @JsonProperty("PayLevel")
    private int payLevel;

    @JsonProperty("Gender")
    private int gender;

    @JsonProperty("Birthday")
    private long birthday;

    @JsonProperty("Telephone")
    private String telephone;

    @JsonProperty("Avatar")
    private String avatar;

    @JsonProperty("SecUid")
    private String secUid;

    @JsonProperty("FansClub")
    private FansClub fansClub;

    @JsonProperty("FollowerCount")
    private int followerCount;

    @JsonProperty("FollowStatus")
    private int followStatus;
}
