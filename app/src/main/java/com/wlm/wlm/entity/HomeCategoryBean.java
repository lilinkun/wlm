package com.wlm.wlm.entity;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 主页上的种类
 * Created by LG on 2018/12/8.
 */
@Entity
public class HomeCategoryBean implements Serializable {
    private String ArticleId;
    private String CategoryId;
    private String Title;
    private String ArticleContent;
    private String Author;
    private int Flag;
    private boolean IsLink;
    private String Link;
    private boolean IsAuditing;
    private boolean IsDownLoad;
    private String FileUrl;
    private String Summary;
    private String Thumbnail;
    private String Color;
    private int FontSize;
    private String Font;
    private String SortRank;
    private String CreateDate;
    private String CategoryName;
    public static final long serialVersionUID = 123227;

    @Generated(hash = 1660081269)
    public HomeCategoryBean(String ArticleId, String CategoryId, String Title, String ArticleContent, String Author, int Flag, boolean IsLink, String Link, boolean IsAuditing, boolean IsDownLoad, String FileUrl, String Summary, String Thumbnail, String Color, int FontSize, String Font, String SortRank, String CreateDate, String CategoryName) {
        this.ArticleId = ArticleId;
        this.CategoryId = CategoryId;
        this.Title = Title;
        this.ArticleContent = ArticleContent;
        this.Author = Author;
        this.Flag = Flag;
        this.IsLink = IsLink;
        this.Link = Link;
        this.IsAuditing = IsAuditing;
        this.IsDownLoad = IsDownLoad;
        this.FileUrl = FileUrl;
        this.Summary = Summary;
        this.Thumbnail = Thumbnail;
        this.Color = Color;
        this.FontSize = FontSize;
        this.Font = Font;
        this.SortRank = SortRank;
        this.CreateDate = CreateDate;
        this.CategoryName = CategoryName;
    }

    @Generated(hash = 324617625)
    public HomeCategoryBean() {
    }

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

    public int getFlag() {
        return Flag;
    }

    public void setFlag(int flag) {
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

    public String getFont() {
        return Font;
    }

    public void setFont(String font) {
        Font = font;
    }

    public String getSortRank() {
        return SortRank;
    }

    public void setSortRank(String sortRank) {
        SortRank = sortRank;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public boolean getIsLink() {
        return this.IsLink;
    }

    public void setIsLink(boolean IsLink) {
        this.IsLink = IsLink;
    }

    public boolean getIsAuditing() {
        return this.IsAuditing;
    }

    public void setIsAuditing(boolean IsAuditing) {
        this.IsAuditing = IsAuditing;
    }

    public boolean getIsDownLoad() {
        return this.IsDownLoad;
    }

    public void setIsDownLoad(boolean IsDownLoad) {
        this.IsDownLoad = IsDownLoad;
    }
}
