package com.majeliscoding.siswaku.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("nama_depan")
    @Expose
    private String namaDepan;
    @SerializedName("nama_belakang")
    @Expose
    private String namaBelakang;
    @SerializedName("tmp_lahir")
    @Expose
    private String tmpLahir;
    @SerializedName("tgl_lahir")
    @Expose
    private String tglLahir;
    @SerializedName("jk")
    @Expose
    private String jk;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("telepon")
    @Expose
    private String telepon;
    @SerializedName("donasi")
    @Expose
    private String donasi;
    @SerializedName("reminder")
    @Expose
    private String reminder;
    @SerializedName("token_id")
    @Expose
    private String tokenId;
}
