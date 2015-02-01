package com.baitaner.common.domain.history;

import com.baitaner.common.domain.BasePojo;

import java.sql.Timestamp;

/**
 * Created by zliu on 15/1/26.
   <id property="id" column="id" />
 <result property="loginName" column="login_name" />
 <result property="userName" column="login_name" />
 <result property="email" column="email" />
 <result property="authEmail" column="auth_email" />
 <result property="phone" column="phone" />
 <result property="isAuth" column="is_auth" />
 <result property="password" column="password" />
 <result property="status" column="status" />
 <result property="role" column="role" />
 <result property="groupId" column="group_id" />
 <result property="registerTime" column="register_time" />
 <result property="loginTime" column="login_time" />
 */
public class Message extends BasePojo {
    private static final long serialVersionUID = 4432519750264280259L;
    private Long userId;
    private Timestamp createTime;
    private Integer status;
    private String content;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
