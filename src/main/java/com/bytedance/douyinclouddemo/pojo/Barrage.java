package com.bytedance.douyinclouddemo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bytedance.douyinclouddemo.pojoUtil.Gift;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.Map;

/**
 * 玩家信息数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("barrage")
public class Barrage {

    private String liveId;

    private String userName;

    private String userPhoto;
    //点赞数
    private Integer userNumberOfLikes;
    //用户本场送礼物总价值
    private Integer userTotalValueOfGift;
    //用户本场得分
    private Integer userScore;
    //红方还是蓝方
    private Integer userType;
    //上中下路
    private Integer road;

    //礼物信息
    @TableField(exist = false)
    private List<Gift> giftList;
    private Map<String, Gift> giftMap;

}
