package com.wlm.wlm.entity;

/**
 * Created by LG on 2019/9/23.
 */
public class ArticleDetailBean {
    private String ArticleId;
    private String CategoryId;
    private String Title;
    private String ArticleContent;
    private String Author;
    private String Flag;
    private boolean IsLink;
    private String Link;
    private boolean IsAuditing;
    private boolean IsDownLoad;
    private String FileUrl;
    private String Summary;
    private String Thumbnail;
    private String Color;
    private int FontSize;
    private int SortRank;
    private int CreateDate;

    public String getArticleId() {
        return ArticleId;
    }

    public void setArticleId(String articleId) {
        ArticleId = articleId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArticleContent() {
        return ArticleContent;
    }

    public void setArticleContent(String articleContent) {
        ArticleContent = articleContent;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public boolean isLink() {
        return IsLink;
    }

    public void setLink(boolean link) {
        IsLink = link;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public boolean isAuditing() {
        return IsAuditing;
    }

    public void setAuditing(boolean auditing) {
        IsAuditing = auditing;
    }

    public boolean isDownLoad() {
        return IsDownLoad;
    }

    public void setDownLoad(boolean downLoad) {
        IsDownLoad = downLoad;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getFontSize() {
        return FontSize;
    }

    public void setFontSize(int fontSize) {
        FontSize = fontSize;
    }

    public int getSortRank() {
        return SortRank;
    }

    public void setSortRank(int sortRank) {
        SortRank = sortRank;
    }

    public int getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(int createDate) {
        CreateDate = createDate;
    }
}
