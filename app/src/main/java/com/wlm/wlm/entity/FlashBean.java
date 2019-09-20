package com.wlm.wlm.entity;

/**
 * flash详情
 * Created by LG on 2018/12/8.
 */
public class FlashBean {
    private int FlashID;
    private String FlashName;
    private String FlashPic;
    private int FlashOrder;
    private String FlashUrl;
    private String CreateDate;
    private boolean Display;
    private int Style;
    private String BackColor;

    public int getFlashID() {
        return FlashID;
    }

    public void setFlashID(int flashID) {
        FlashID = flashID;
    }

    public String getFlashName() {
        return FlashName;
    }

    public void setFlashName(String flashName) {
        FlashName = flashName;
    }

    public String getFlashPic() {
        return FlashPic;
    }

    public void setFlashPic(String flashPic) {
        FlashPic = flashPic;
    }

    public int getFlashOrder() {
        return FlashOrder;
    }

    public void setFlashOrder(int flashOrder) {
        FlashOrder = flashOrder;
    }

    public String getFlashUrl() {
        return FlashUrl;
    }

    public void setFlashUrl(String flashUrl) {
        FlashUrl = flashUrl;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public boolean isDisplay() {
        return Display;
    }

    public void setDisplay(boolean display) {
        Display = display;
    }

    public int getStyle() {
        return Style;
    }

    public void setStyle(int style) {
        Style = style;
    }

    public String getBackColor() {
        return BackColor;
    }

    public void setBackColor(String backColor) {
        BackColor = backColor;
    }
}
