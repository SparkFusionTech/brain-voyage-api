package com.sparkfusion.quiz.brainvoyage.api.dto.image_search;

public class PixabayImageDto {

    private String webformatURL;
    private Integer webformatWidth;
    private Integer webformatHeight;

    private Integer views;
    private Integer likes;

    private Integer user_id;
    private String user;
    private String userImageURL;

    public PixabayImageDto() {
    }

    public PixabayImageDto(
            String webformatURL,
            Integer webformatWidth,
            Integer webformatHeight,
            Integer views,
            Integer likes,
            Integer user_id,
            String user,
            String userImageURL
    ) {
        this.webformatURL = webformatURL;
        this.webformatWidth = webformatWidth;
        this.webformatHeight = webformatHeight;
        this.views = views;
        this.likes = likes;
        this.user_id = user_id;
        this.user = user;
        this.userImageURL = userImageURL;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public Integer getWebformatWidth() {
        return webformatWidth;
    }

    public void setWebformatWidth(Integer webformatWidth) {
        this.webformatWidth = webformatWidth;
    }

    public Integer getWebformatHeight() {
        return webformatHeight;
    }

    public void setWebformatHeight(Integer webformatHeight) {
        this.webformatHeight = webformatHeight;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }
}
