package com.bytedance.douyinclouddemo.pojoUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gift {
    //礼物ID
    private Integer giftId;
    //礼物名称
    private String giftName;

    //private String groupId;
    //礼物数量
    private Integer giftCount;

    //private Integer repeatCount;
    //礼物价值
    private Integer diamondCount;

}
