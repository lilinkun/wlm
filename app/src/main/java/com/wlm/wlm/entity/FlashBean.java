package com.wlm.wlm.entity;

/**
 * flash详情
 * Created by LG on 2018/12/8.
 */
public class FlashBean {
    private String FlashID;
    private String FlashName;
    private String FlashPic;
    private String FlashOrder;
    private String FlashUrl;
    private String BackColor;
    private String CreateDate;
    private boolean Display;
    private int Style;

    public String getFlashID() {
        return FlashID;
    }

    public void setFlashID(String flashID) {
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

    public String getFlashOrder() {
        return FlashOrder;
    }

    public void setFlashOrder(String flashOrder) {
        FlashOrder = flashOrder;
    }

    public String getFlashUrl() {
        return FlashUrl;
    }

    public void setFlashUrl(String flashUrl) {
        FlashUrl = flashUrl;
    }

    public String getBackColor() {
        return BackColor;
    }

    public void setBackColor(String backColor) {
        BackColor = backColor;
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
}
