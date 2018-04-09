package com.example.track_hailing;

/**
 * Created by 叶泽锐 on 2018/4/8.
 */

public class Orders {

    private String OrId;
    private String name;

    private double price;
    private int dayNum;

    private String originDate;
    private String destinateDate;

    private String address;
    private String status;

    private int size = 0;

    public Orders(String OrId, String name, double price, int dayNum, String originDate,
                  String destinateDate, String address, String status) {
        this.OrId = OrId;
        this.name = name;
        this.price = price;
        this.dayNum = dayNum;
        this.originDate = originDate;
        this.destinateDate = destinateDate;
        this.address = address;
        this.status = status;
    }

    public String getOrId() {
        return OrId;
    }

    public int getDayNum() {
        return dayNum;
    }

    public double getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public String getDestinateDate() {
        return destinateDate;
    }

    public String getOriginDate() {
        return originDate;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }


    public int getSize() {
        return size;
    }


    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }
}
