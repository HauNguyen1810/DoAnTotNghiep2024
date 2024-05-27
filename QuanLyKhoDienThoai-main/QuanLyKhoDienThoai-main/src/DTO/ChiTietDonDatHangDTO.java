package DTO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nguyen Trung Hau
 */
public class ChiTietDonDatHangDTO extends ChiTietPhieuDTO{
    private int phuongthucnnhap;

    public ChiTietDonDatHangDTO(int phuongthucnnhap) {
        this.phuongthucnnhap = phuongthucnnhap;
    }

    public ChiTietDonDatHangDTO(int phuongthucnnhap, int maphieu, int maphienbansp, int soluong, int dongia) {
        super(maphieu, maphienbansp, soluong, dongia);
        this.phuongthucnnhap = phuongthucnnhap;
    }

    public int getPhuongthucnnhap() {
        return phuongthucnnhap;
    }

    public void setPhuongthucnnhap(int phuongthucnnhap) {
        this.phuongthucnnhap = phuongthucnnhap;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.phuongthucnnhap;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChiTietDonDatHangDTO other = (ChiTietDonDatHangDTO) obj;
        return this.phuongthucnnhap == other.phuongthucnnhap;
    }
}
