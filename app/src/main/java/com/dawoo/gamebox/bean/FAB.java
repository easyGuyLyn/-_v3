package com.dawoo.gamebox.bean;

/**
 * Created by benson on 18-1-1.
 */

public class FAB {
    /**
     * activity : {"activityId":"59dc5d43bdca54dc01ea0befbc250146","description":" My name's Roy  ","normalEffect":"/fserver/files/floatImage/floatpic/panel-second.png","location":"right","language":"zh_CN","distanceTop":null,"distanceSide":10}
     */

    private ActivityBean activity;

    public ActivityBean getActivity() {
        return activity;
    }

    public void setActivity(ActivityBean activity) {
        this.activity = activity;
    }

    public static class ActivityBean {
        /**
         * activityId : 59dc5d43bdca54dc01ea0befbc250146
         * description :  My name's Roy
         * normalEffect : /fserver/files/floatImage/floatpic/panel-second.png
         * location : right
         * language : zh_CN
         * distanceTop : null
         * distanceSide : 10
         */

        private String activityId;
        private String description;
        private String normalEffect;
        private String location;
        private String language;
        private Object distanceTop;
        private int distanceSide;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getNormalEffect() {
            return normalEffect;
        }

        public void setNormalEffect(String normalEffect) {
            this.normalEffect = normalEffect;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public Object getDistanceTop() {
            return distanceTop;
        }

        public void setDistanceTop(Object distanceTop) {
            this.distanceTop = distanceTop;
        }

        public int getDistanceSide() {
            return distanceSide;
        }

        public void setDistanceSide(int distanceSide) {
            this.distanceSide = distanceSide;
        }
    }
}
