package com.loginradius.androidsdk.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 9/30/2016.
 */
public class CustomizeCustomfield {



        @SerializedName("customfields1")
        @Expose
        private String customfields1;
        @SerializedName("customfields2")
        @Expose
        private String customfields2;
        @SerializedName("customfields3")
        @Expose
        private String customfields3;
        @SerializedName("customfields4")
        @Expose
        private String customfields4;
        @SerializedName("customfields5")
        @Expose
        private String customfields5;

        /**
         *
         * @return
         * The customfields1
         */
        public String getCustomfields1() {
            return customfields1;
        }

        /**
         *
         * @param customfields1
         * The customfields1
         */
        public void setCustomfields1(String customfields1) {
            this.customfields1 = customfields1;
        }

        /**
         *
         * @return
         * The customfields2
         */
        public String getCustomfields2() {
            return customfields2;
        }

        /**
         *
         * @param customfields2
         * The customfields2
         */
        public void setCustomfields2(String customfields2) {
            this.customfields2 = customfields2;
        }

        /**
         *
         * @return
         * The customfields3
         */
        public String getCustomfields3() {
            return customfields3;
        }

        /**
         *
         * @param customfields3
         * The customfields3
         */
        public void setCustomfields3(String customfields3) {
            this.customfields3 = customfields3;
        }

        /**
         *
         * @return
         * The customfields4
         */
        public String getCustomfields4() {
            return customfields4;
        }

        /**
         *
         * @param customfields4
         * The customfields4
         */
        public void setCustomfields4(String customfields4) {
            this.customfields4 = customfields4;
        }

        /**
         *
         * @return
         * The customfields5
         */
        public String getCustomfields5() {
            return customfields5;
        }

        /**
         *
         * @param customfields5
         * The customfields5
         */
        public void setCustomfields5(String customfields5) {
            this.customfields5 = customfields5;
        }
}
