package com.baitaner.common.domain.response;

import java.io.Serializable;

/**
 * Created by zliu on 15/1/28.
 “serverHost”:”127.0.0.1",
 "servePrort:”80”,
 “sessionKey":**,
 “expire":”” //单位秒
 */
public class AuthResponse implements Serializable{

    private static final long serialVersionUID = 3276947150016059612L;

    private String serverHost;
    private Integer serverPort;
    private String sessionKey;
    private Long expire;

    @Override
    public String toString() {
        return "AuthResponse{" +
                "serverHost='" + serverHost + '\'' +
                ", serverPort=" + serverPort +
                ", sessionKey='" + sessionKey + '\'' +
                ", expire=" + expire +
                '}';
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
