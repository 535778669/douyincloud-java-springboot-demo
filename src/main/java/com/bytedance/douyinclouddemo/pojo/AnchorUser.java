package com.bytedance.douyinclouddemo.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("anchor_user")
public class AnchorUser {
    @TableId
    private String id;
    private String account;
    private String password;
    private String roleId;
    private String token;
}
