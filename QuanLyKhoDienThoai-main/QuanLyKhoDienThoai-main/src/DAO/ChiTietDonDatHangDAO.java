package DAO;

import DTO.ChiTietDonDatHangDTO;
import DTO.ChiTietPhieuNhapDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

/**
 *
 * @author Nguyen Trung Hau
 */
public class ChiTietDonDatHangDAO implements ChiTietInterface<ChiTietDonDatHangDTO> {

    public static ChiTietDonDatHangDAO getInstance() {
        return new ChiTietDonDatHangDAO();
    }

    @Override
    public int insert(ArrayList<ChiTietDonDatHangDTO> t) {
        int result = 0;
        for (int i = 0; i < t.size(); i++) {
            try {
                Connection con = (Connection) JDBCUtil.getConnection();
                String sql = "INSERT INTO `ctdondathang`(`madondathang`, `maphienbansp`, `soluong`, `dongia`, `hinhthucnhap`) VALUES (?,?,?,?,?)";
                PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                pst.setInt(1, t.get(i).getMaphieu());
                pst.setInt(2, t.get(i).getMaphienbansp());
                pst.setInt(3, t.get(i).getSoluong());
                pst.setInt(4, t.get(i).getDongia());
                pst.setInt(5, t.get(i).getPhuongthucnnhap());
                result = pst.executeUpdate();
                JDBCUtil.closeConnection(con);
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            //PhienBanSanPhamDAO.getInstance().updateSoLuongTon(t.get(i).getMaphienbansp(), t.get(i).getSoluong());
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM ctdondathang WHERE madondathang = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(ArrayList<ChiTietDonDatHangDTO> t, String pk) {
        int result = this.delete(pk);
        if (result != 0) {
            result = this.insert(t);
        }
        return result;
    }

    @Override
    public ArrayList<ChiTietDonDatHangDTO> selectAll(String t) {
        ArrayList<ChiTietDonDatHangDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM ctdondathang WHERE madondathang = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int maphieu = rs.getInt("madondathang");
                int maphienbansp = rs.getInt("maphienbansp");
                int dongia = rs.getInt("dongia");
                int soluong = rs.getInt("soluong");
                int phuongthucnhap = rs.getInt("hinhthucnhap");
                ChiTietDonDatHangDTO ctphieu = new ChiTietDonDatHangDTO(phuongthucnhap, maphieu, maphienbansp, soluong, dongia);
                result.add(ctphieu);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    public int getSoLuongDatByPhieuNhap(int maphieu) {
        int soLuongDat = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT soluong FROM ctdondathang WHERE madondathang = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, maphieu);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                soLuongDat = rs.getInt("soluong");
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietDonDatHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soLuongDat;
    }

}
