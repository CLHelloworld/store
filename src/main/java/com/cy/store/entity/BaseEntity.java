package com.cy.store.entity;

import java.util.Date;
import java.util.Objects;

//作為實體Entity的共用Entity使用繼承方式裝回去
//@Data
public class BaseEntity {

    private String createdUser;
    private Date createdTime;
    private Date modifiedUser;
    private Date modifiedTime;

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Date modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(createdUser, that.createdUser) && Objects.equals(createdTime, that.createdTime) && Objects.equals(modifiedUser, that.modifiedUser) && Objects.equals(modifiedTime, that.modifiedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdUser, createdTime, modifiedUser, modifiedTime);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createdUser='" + createdUser + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedUser=" + modifiedUser +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
