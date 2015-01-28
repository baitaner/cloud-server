package com.baitaner.common.domain.base;

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
public class User extends BasePojo {
    private static final long serialVersionUID = 4386115402106072569L;
    private String loginName;
    private String userName;
    private String email;
    private String groupEmail;
    private String icon;
    private String phone;
    private Integer isAuth;
    private String password;
    private Integer status;
    private Integer role;
    private Long groupId;
    private Timestamp registerTime;
    private Timestamp loginTime;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupEmail() {
        return groupEmail;
    }

    public void setGroupEmail(String groupEmail) {
        this.groupEmail = groupEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }
}
