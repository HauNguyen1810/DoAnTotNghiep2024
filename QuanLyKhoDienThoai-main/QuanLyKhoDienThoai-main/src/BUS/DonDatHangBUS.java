package BUS;

import DAO.ChiTietDonDatHangDAO;
import DAO.DonDatHangDAO;
import DTO.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Nguyen Trung Hau
 */
public class DonDatHangBUS {

    public final DonDatHangDAO donDatHangDAO = new DonDatHangDAO();
    public final ChiTietDonDatHangDAO ctDonDatHangDAO = new ChiTietDonDatHangDAO();

    NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    NhanVienBUS nvBUS = new NhanVienBUS();

    ArrayList<DonDatHangDTO> listDonDatHang;

    public DonDatHangBUS() {
    }

    public ArrayList<DonDatHangDTO> getAll() {
        this.listDonDatHang = donDatHangDAO.selectAll();
        return this.listDonDatHang;
    }

    public ArrayList<DonDatHangDTO> getAllList() {
        return this.listDonDatHang;
    }

    public ArrayList<ChiTietSanPhamDTO> convertHashMapToArray(HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> chitietsanpham) {
        ArrayList<ChiTietSanPhamDTO> result = new ArrayList<>();
        for (ArrayList<ChiTietSanPhamDTO> ctsp : chitietsanpham.values()) {
            result.addAll(ctsp);
        }
        return result;
    }

    public ArrayList<ChiTietDonDatHangDTO> getChiTietPhieu(int madondathang) {
        return ctDonDatHangDAO.selectAll(Integer.toString(madondathang));
    }

    public ArrayList<ChiTietPhieuDTO> getChiTietPhieu_Type(int madondathang) {
        ArrayList<ChiTietDonDatHangDTO> arr = ctDonDatHangDAO.selectAll(Integer.toString(madondathang));
        ArrayList<ChiTietPhieuDTO> result = new ArrayList<>();
        for (ChiTietPhieuDTO i : arr) {
            result.add(i);
        }
        return result;
    }

    public boolean add(DonDatHangDTO phieu, ArrayList<ChiTietDonDatHangDTO> ctPhieu, HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> chitietsanpham) {
        boolean check = donDatHangDAO.insert(phieu) != 0;
        if (check) {
            check = ctDonDatHangDAO.insert(ctPhieu) != 0;
//            check = chitietsanphamDAO.insert_mutiple(convertHashMapToArray(chitietsanpham)) != 0;
        }
        return check;
    }

    public ChiTietDonDatHangDTO findCT(ArrayList<ChiTietDonDatHangDTO> ctphieu, int mapb) {
        ChiTietDonDatHangDTO p = null;
        int i = 0;
        while (i < ctphieu.size() && p == null) {
            if (ctphieu.get(i).getMaphienbansp() == mapb) {
                p = ctphieu.get(i);
            } else {
                i++;
            }
        }
        return p;
    }

    public long getTongTien(ArrayList<ChiTietDonDatHangDTO> ctphieu) {
        long result = 0;
        for (ChiTietDonDatHangDTO item : ctphieu) {
            result += item.getDongia() * item.getSoluong();
        }
        return result;
    }
    
        public ArrayList<DonDatHangDTO> fillerDonDatHang1(String input) {
        ArrayList<DonDatHangDTO> result = new ArrayList<>();
        for (DonDatHangDTO donDatHang : getAll()) {
            if (Integer.toString(donDatHang.getMaphieu()).contains(input)) {
                result.add(donDatHang);
            }
        }

        return result;
    }

    public ArrayList<DonDatHangDTO> fillerDonDatHang(int type, String input, int mancc, int manv, Date time_s, Date time_e, String price_minnn, String price_maxxx) {
        Long price_min = !price_minnn.equals("") ? Long.valueOf(price_minnn) : 0L;
        Long price_max = !price_maxxx.equals("") ? Long.valueOf(price_maxxx) : Long.MAX_VALUE;
        Timestamp time_start = new Timestamp(time_s.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time_e.getTime());
        // Đặt giá trị cho giờ, phút, giây và mili giây của Calendar
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Timestamp time_end = new Timestamp(calendar.getTimeInMillis());
        ArrayList<DonDatHangDTO> result = new ArrayList<>();
        for (DonDatHangDTO donDatHang : getAll()) {
            boolean match = false;
            switch (type) {
                case 0 -> {
                    if (Integer.toString(donDatHang.getMaphieu()).contains(input)
                            || nccBUS.getTenNhaCungCap(donDatHang.getManhacungcap()).toLowerCase().contains(input)
                            || nvBUS.getNameById(donDatHang.getManguoitao()).toLowerCase().contains(input)) {
                        match = true;
                    }
                }
                case 1 -> {
                    if (Integer.toString(donDatHang.getMaphieu()).contains(input)) {
                        match = true;
                    }
                }
                case 2 -> {
                    if (nccBUS.getTenNhaCungCap(donDatHang.getManhacungcap()).toLowerCase().contains(input)) {
                        match = true;
                    }
                }
                case 3 -> {
                    if (nvBUS.getNameById(donDatHang.getManguoitao()).toLowerCase().contains(input)) {
                        match = true;
                    }
                }
            }

            if (match
                    && (manv == 0 || donDatHang.getManguoitao() == manv) && (mancc == 0 || donDatHang.getManhacungcap() == mancc)
                    && (donDatHang.getThoigiantao().compareTo(time_start) >= 0)
                    && (donDatHang.getThoigiantao().compareTo(time_end) <= 0)
                    && donDatHang.getTongTien() >= price_min
                    && donDatHang.getTongTien() <= price_max) {
                result.add(donDatHang);
            }
        }

        return result;
    }

    public boolean checkCancelPn(int maphieu) {
        return donDatHangDAO.checkCancelDDH(maphieu);
    }

    public int cancelDonDatHang(int maphieu) {
        return donDatHangDAO.cancelDonDatHang(maphieu);
    }

}
