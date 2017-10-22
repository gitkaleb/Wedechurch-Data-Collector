package com.gcme.wedechurchdatacollector.Services;

/**
 * Created by kzone on 2/4/2017.
 */

public class RequestServices {
//    public static String MainAPI = "http://wede.myims.org/api"; // Main Server server
    public static String MainAPI = "http://wede.myims.org/api"; // Main Server server
    public static String MainServices = "service"; // Main Server server
    public static String PARAM = "param"; // Main Server server
    public enum AUTHENTICATION {
        API_URL("/api"),
        USER_EMAIL("user_name"),
        USER_PASS("user_pass"),
        SERVICE("log_in");

        private final String name;
        AUTHENTICATION(String s) {
            this.name = s;
        }
        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
    public enum REGISTRATION_REQUEST {

        SERVICE("register"),
        ChurchSERVICE("registerchurch"),
        SCHEDULESERVICE("registerschedule"),

        FIRST_NAME("first_name"),
        LAST_NAME("last_name"),
        USER_NAME("user_name"),
        EMAIL("email"),
        MOBILE_NUMBER("phone_number"),
        PASSWORD("user_pass"),
        SEX("sex"),
        COUNTRY("country");

        private final String name;
        REGISTRATION_REQUEST(String s) {
            this.name = s;
        }
        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
    public enum FORGOT_PASSWORD_REQUEST {
        API_URL("/hulu-api/forget-password"),
        USER_OLD_PASSWORD("hul_password_old"),
        USER_NEW_PASSWORD("hul_password");
        private final String name;
        FORGOT_PASSWORD_REQUEST(String s) {
            this.name = s;
        }
        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
    public enum GAME_RESULTS {
        API_URL("/hulu-api/results"),
        RESULT_OFFSET("postData");
        private final String name;
        GAME_RESULTS(String s) {
            this.name = s;
        }
        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
    public enum HULU_NEWS {
        API_URL("/hulu-api/top-news"),
        RESULT_OFFSET("postData");
        private final String name;
        HULU_NEWS(String s) {
            this.name = s;
        }
        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
    public enum GET_MAX_BET {
        API_URL("/hulu-api/max-bet"),
        SELECTED_GAME_ID("gameID"),
        SELECTED_TEAM_ID("team");
        private final String name;
        GET_MAX_BET(String s) {
            this.name = s;
        }
        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum GET_ALL_EVENTS {
        SERVICE("event_get");
        private final String name;
        GET_ALL_EVENTS(String s) {
            this.name = s;
        }
        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
    public enum GET_ALL_CHURCHS{
        SERVICE("church_get");
        private final String name;
        GET_ALL_CHURCHS(String s) {
            this.name = s;
        }
        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
