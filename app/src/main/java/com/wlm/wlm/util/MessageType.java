package com.wlm.wlm.util;

import com.wlm.wlm.R;

/**
 * Created by LG on 2019/9/24.
 */
public enum MessageType {
    NEW1(1,"商品活动", R.mipmap.ic_news_1,"","b3d59342-ff07-481c-86b2-41b85e82ad48"),
    NEW2(2,"系统消息",R.mipmap.ic_news_2,"","2197a9a6-ca8d-4d1c-b843-188285723329"),
    NEW3(3,"客服消息",R.mipmap.ic_news_3,"","c0bd0362-0a20-46ca-be77-9e0208bb4555"),
    NEW4(4,"分享消息",R.mipmap.ic_news_4,"","ab6894c6-9f10-450a-a1c7-1a4ea27bba50");


    private int typeId;
    private String typeName;
    private int drawBg;
    private String content;
    private String CategoryId;

    private MessageType(int typeId, String typeName, int bg,String content,String CategoryId){
        this.typeName = typeName;
        this.typeId = typeId;
        this.drawBg = bg;
        this.content = content;
        this.CategoryId = CategoryId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getDrawBg() {
        return drawBg;
    }

    public void setDrawBg(int drawBg) {
        this.drawBg = drawBg;
    }

    public static MessageType getVipByValue(String type) {
        for (MessageType p : values()) {
            if (p.getTypeName().equals(type))
                return p;
        }
        return null;
    }

    public static MessageType getVipById(int type) {
        for (MessageType p : values()) {
            if (p.getTypeId()== type)
                return p;
        }
        return null;
    }
}

