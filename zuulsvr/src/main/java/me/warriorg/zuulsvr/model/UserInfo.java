package me.warriorg.zuulsvr.model;

/**
 * @author: 高士勇
 * @date: 2019-01-06
 */
public class UserInfo {
    String organizationId;
    String userId;

    public String getOrganizationId() {
        return this.organizationId;
    }


    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
