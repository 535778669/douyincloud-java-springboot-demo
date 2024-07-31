package com.bytedance.douyinclouddemo.pojoUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarragePoJo {

    private String liveId;

    private String userName;

    private String userPhoto;
    //点赞数
    private Integer userNumberOfLikes;
    //总点赞数
    private Integer totalNumberOfLikes;
    //红方还是蓝方
    private Integer userType;
    //上中下路
    private Integer road;
    //礼物信息
    private List<Gift> giftList;
    //调用状态
    private Integer type;
}
