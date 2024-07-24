package com.share.platform.api.model;

import java.util.Date;

public class ShopTab {
    private Integer id;

    private String shopCode;

    private String shopName;

    private String shopAdd;

    private String shopContacts;

    private String shopPhone;

    private String shopQualificate;

    private Date createTime;

    private Date updateTime;

    private String createdBy;

    private String updateBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode == null ? null : shopCode.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopAdd() {
        return shopAdd;
    }

    public void setShopAdd(String shopAdd) {
        this.shopAdd = shopAdd == null ? null : shopAdd.trim();
    }

    public String getShopContacts() {
        return shopContacts;
    }

    public void setShopContacts(String shopContacts) {
        this.shopContacts = shopContacts == null ? null : shopContacts.trim();
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone == null ? null : shopPhone.trim();
    }

    public String getShopQualificate() {
        return shopQualificate;
    }

    public void setShopQualificate(String shopQualificate) {
        this.shopQualificate = shopQualificate == null ? null : shopQualificate.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
}