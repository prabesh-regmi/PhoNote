package com.prabesh.phonote.Modal;


public class DataModel {

    private String dataId;
    private boolean sell;
    private String date, name;
    private Integer weight, rate, total;
    private String addedBy;
    private long addedTime, modifiedTime;

    public DataModel() {
    }

    public DataModel(String dataId, boolean sell, String date, String name, Integer weight, Integer rate, Integer total, String addedBy, long addedTime, long modifiedTime) {
        this.dataId = dataId;
        this.sell = sell;
        this.date = date;
        this.name = name;
        this.weight = weight;
        this.rate = rate;
        this.total = total;
        this.addedBy = addedBy;
        this.addedTime = addedTime;
        this.modifiedTime = modifiedTime;
    }

    public Integer getTotal() {
        return weight*rate;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public boolean isSell() {
        return sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public long getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(long addedTime) {
        this.addedTime = addedTime;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
