package com.sdu.chy.chytest.recyclerViewTest;

public class Goods {
    private String goodsName;
    private String goodsImg;
    private String goodsDescription;
    private Integer goodsType;  //1表示我的商品0表示别人的商品
    private String publisherName;

    public Goods(String goodsName, String goodsImg, String goodsDescription, Integer goodsType,String publisherName) {
        this.goodsName = goodsName;
        this.goodsImg = goodsImg;
        this.goodsDescription = goodsDescription;
        this.goodsType = goodsType;
        this.publisherName = publisherName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
