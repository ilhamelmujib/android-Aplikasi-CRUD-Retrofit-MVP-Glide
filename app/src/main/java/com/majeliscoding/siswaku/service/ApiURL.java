package com.majeliscoding.siswaku.service;

public class ApiURL {
    //        public final static String BASE_URL = "http://3542a6ff.ngrok.io/api/";
    public final static String BASE_URL  = "http://api-oauth.aliya.asia/";
    public final static String URL_IMAGE= BASE_URL + "people/";

    public static final String LOGIN          = "login";
    public static final String GET_STUDENT    = "people/index";
    public static final String CREATE_STUDENT = "people/store";
    public static final String UPDATE_STUDENT = "people/update/{id}";
    public static final String DELETE_STUDENT = "people/delete/{id}";

    public static final String LIST_HOBBY     = "hobby/index";
    public static final String LIST_PROFESSION= "profession/index";

}
