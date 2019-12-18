package com.tiennn.appbandoanvanphong.model;

import java.io.Serializable;

public class Nguoidung implements Serializable {
    public int ID;
    public String Taikhoan;
    public String Matkhau;

    public Nguoidung(int ID, String taikhoan, String matkhau) {
        this.ID = ID;
        Taikhoan = taikhoan;
        Matkhau = matkhau;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTaikhoan() {
        return Taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        Taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return Matkhau;
    }

    public void setMatkhau(String matkhau) {
        Matkhau = matkhau;
    }
}
