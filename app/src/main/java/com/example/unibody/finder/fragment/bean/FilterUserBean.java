package com.example.unibody.finder.fragment.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



import java.util.List;


public class FilterUserBean implements Serializable {
    private List<FilterUsersBean> results;

    public List<FilterUsersBean> getFilterUsers() {
        return results;
    }

    public void setFilterUsers(List<com.example.unibody.finder.fragment.bean.FilterUserBean.FilterUsersBean> filterUsers) {
        this.results = filterUsers;
    }

    public static class FilterUsersBean implements Serializable {
        private String username;
        private String gender;
        private String major;
        private String university;
        private List<Double> loc ;
        private String status;
        private String distance;
        private String avatar_url;

//        public String getUsername() {
//            return username;
//        }
//
//        public void setUsername(String username) {
//            this.username = username;
//        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getUniversity() {
            return university;
        }

        public void setUniversity(String university) {
            this.university = university;
        }

        public String getMajor() {
            return major;
        }

        public void setBirth(String major) {
            this.major = major;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<Double> getLoc(){
            return loc;
        }
        public void setLoc(List<Double> loc){
            this.loc = loc;
        }
    }
}