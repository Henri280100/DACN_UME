package com.example.ume_frontend.Model;

import java.io.Serializable;
import java.util.List;

public class PosterModel {
    private List<Data> Data;

    private String message;

    public List<Data> getData() {
        return Data;
    }

    public void setData(List<Data> Data) {
        this.Data = Data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PosterModel(List<PosterModel.Data> data, String message) {
        Data = data;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ClassPojo [Data = " + Data + ", message = " + message + "]";
    }

    public static class Data implements Serializable {
        private int idUser;

        private String commentNumber;

        private String idPoster;

        private String imgPoster;

        private String createOn;

        private String content;

        private String likeNumber;

        public Data(int idUser, String commentNumber, String idPoster, String imgPoster, String createOn, String content, String likeNumber) {
            this.idUser = idUser;
            this.commentNumber = commentNumber;
            this.idPoster = idPoster;
            this.imgPoster = imgPoster;
            this.createOn = createOn;
            this.content = content;
            this.likeNumber = likeNumber;
        }

        public int getIdUser() {
            return idUser;
        }

        public void setIdUser(int idUser) {
            this.idUser = idUser;
        }

        public String getCommentNumber() {
            return commentNumber;
        }

        public void setCommentNumber(String commentNumber) {
            this.commentNumber = commentNumber;
        }

        public String getIdPoster() {
            return idPoster;
        }

        public void setIdPoster(String idPoster) {
            this.idPoster = idPoster;
        }

        public String getImgPoster() {
            return imgPoster;
        }

        public void setImgPoster(String imgPoster) {
            this.imgPoster = imgPoster;
        }

        public String getCreateOn() {
            return createOn;
        }

        public void setCreateOn(String createOn) {
            this.createOn = createOn;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLikeNumber() {
            return likeNumber;
        }

        public void setLikeNumber(String likeNumber) {
            this.likeNumber = likeNumber;
        }

        @Override
        public String toString() {
            return "ClassPojo [idUser = " + idUser + ", commentNumber = " + commentNumber + ", idPoster = " + idPoster + ", imgPoster = " + imgPoster + ", createOn = " + createOn + ", content = " + content + ", likeNumber = " + likeNumber + "]";
        }
    }
}
