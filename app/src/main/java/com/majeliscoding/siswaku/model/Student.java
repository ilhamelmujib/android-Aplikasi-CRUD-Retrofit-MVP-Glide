package com.majeliscoding.siswaku.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class Student implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("born_place")
    @Expose
    private String bornPlace;
    @SerializedName("born_date")
    @Expose
    private String bornDate;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("profession_id")
    @Expose
    private Integer professionId;
    @SerializedName("hobby_id")
    @Expose
    private Integer hobbyId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("deleted_by")
    @Expose
    private Object deletedBy;
    @SerializedName("hobby")
    @Expose
    private Hobby hobby;
    @SerializedName("profession")
    @Expose
    private Profession profession;
}
