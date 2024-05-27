package GUI.Panel;

import BUS.*;
import DTO.*;
import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import GUI.Main;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import helper.Formater;
import helper.Validation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;

public final class TaoDonDatHang extends JPanel implements ItemListener, ActionListener {

    PanelBorderRadius right, left;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter, left_top, main, content_right_bottom, content_btn;
    JTable tableDonDatHang, tableSanPham;
    JScrollPane scrollTableDonDatHang, scrollTableSanPham;
    DefaultTableModel tblModel, tblModelSP;
    ButtonCustom btnAddSp, btnEditSP, btnDelete, btnImport, btnDatHang;
    InputForm txtMaphieu, txtNhanVien, txtMaSp, txtTenSp, txtDongia, txtSoLuongImei;
    SelectForm cbxNhaCungCap, cbxCauhinh, cbxPtNhap;
    JTextField txtTimKiem;
    JLabel lbltongtien;
    Main m;
    Color BackgroundColor = new Color(240, 247, 250);
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    SanPhamBUS spBUS = new SanPhamBUS();
    NhaCungCapBUS nccBus = new NhaCungCapBUS();
    PhienBanSanPhamBUS phienbanBus = new PhienBanSanPhamBUS();
    DungLuongRamBUS ramBus = new DungLuongRamBUS();
    DungLuongRomBUS romBus = new DungLuongRomBUS();
    DonDatHangBUS donDatHangBUS = new DonDatHangBUS();
    MauSacBUS mausacBus = new MauSacBUS();
    NhanVienDTO nvDto;

    ArrayList<DTO.SanPhamDTO> listSP = spBUS.getAll();
    ArrayList<PhienBanSanPhamDTO> ch = new ArrayList<>();
    ArrayList<ChiTietDonDatHangDTO> chitietphieu;
    HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> chitietsanpham = new HashMap<>();
    int maDonDatHang;
    int rowPhieuSelect = -1;
    private ButtonCustom scanImei, importImei;

    public TaoDonDatHang(NhanVienDTO nv, String type, Main m) {
        this.nvDto = nv;
        this.m = m;
        maDonDatHang= donDatHangBUS.donDatHangDAO.getAutoIncrement();
        chitietphieu = new ArrayList<>();
        initComponent(type);
        loadDataTalbeSanPham(listSP);
    }

    public void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 5));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 5));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(5, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(5, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }

    private void initComponent(String type) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Phiếu nhập
        tableDonDatHang = new JTable();
        scrollTableDonDatHang = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã SP", "Tên sản phẩm", "RAM", "ROM", "Màu sắc", "Đơn giá", "Số lượng"};
        tblModel.setColumnIdentifiers(header);
        tableDonDatHang.setModel(tblModel);
        scrollTableDonDatHang.setViewportView(tableDonDatHang);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableDonDatHang.getColumnModel();
        for (int i = 0; i < 8; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        tableDonDatHang.getColumnModel().getColumn(2).setPreferredWidth(300);
        tableDonDatHang.setDefaultEditor(Object.class, null);
        tableDonDatHang.setFocusable(false);
        scrollTableDonDatHang.setViewportView(tableDonDatHang);

        tableDonDatHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = tableDonDatHang.getSelectedRow();
                if (index != -1) {
                    setFormChiTietPhieu(chitietphieu.get(index));
                    rowPhieuSelect = index;
                    actionbtn("update");
                }
            }
        });

        // Table sản phẩm
        tableSanPham = new JTable();
        scrollTableSanPham = new JScrollPane();
        tblModelSP = new DefaultTableModel();

        String[] headerSP = new String[]{"Mã SP", "Tên sản phẩm", "Số lượng tồn"};
        tblModelSP.setColumnIdentifiers(headerSP);
        tableSanPham.setModel(tblModelSP);
        scrollTableSanPham.setViewportView(tableSanPham);
        tableSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableSanPham.setDefaultEditor(Object.class, null);
        tableSanPham.setFocusable(false);
        scrollTableSanPham.setViewportView(tableSanPham);

        tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = tableSanPham.getSelectedRow();
                if (index != -1) {
                    resetForm();
                    setInfoSanPham(listSP.get(index));
                    ChiTietDonDatHangDTO ctp = checkTonTai();
                    if (ctp == null) {
                        actionbtn("add");
                    } else {
                        actionbtn("update");
                        setFormChiTietPhieu(ctp);
                    }
                }
            }
        });

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(5, 5));
        this.add(contentCenter, BorderLayout.CENTER);

        left = new PanelBorderRadius();
        left.setLayout(new BorderLayout(0, 5));
        left.setBackground(Color.white);

        left_top = new JPanel(); // Chứa tất cả phần ở phía trái trên cùng
        left_top.setLayout(new BorderLayout());
        left_top.setBorder(new EmptyBorder(5, 5, 10, 10));
        left_top.setOpaque(false);

        JPanel content_top, content_left, content_right, content_right_top;
        content_top = new JPanel(new GridLayout(1, 2, 5, 5));
        content_top.setOpaque(false);
        content_left = new JPanel(new BorderLayout(5, 5));
        content_left.setOpaque(false);
        content_left.setPreferredSize(new Dimension(0, 300));

        txtTimKiem = new JTextField();
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Tên sản phẩm, mã sản phẩm...");
        txtTimKiem.putClientProperty("JTextField.showClearButton", true);
        txtTimKiem.putClientProperty("JTextField.leadingIcon", new FlatSVGIcon("./icon/search.svg"));

        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ArrayList<SanPhamDTO> rs = spBUS.search(txtTimKiem.getText());
                loadDataTalbeSanPham(rs);
            }
        });

        txtTimKiem.setPreferredSize(new Dimension(100, 40));
        content_left.add(txtTimKiem, BorderLayout.NORTH);
        content_left.add(scrollTableSanPham, BorderLayout.CENTER);

        content_right = new JPanel(new BorderLayout(5, 5));
        content_right.setOpaque(false);

        content_right_top = new JPanel(new BorderLayout());
        content_right_top.setPreferredSize(new Dimension(100, 260));
        txtMaSp = new InputForm("Mã sản phẩm");
        txtMaSp.setEditable(false);
        txtTenSp = new InputForm("Tên sản phẩm");
        txtTenSp.setEditable(false);

        String[] arrCauhinh = {"Chọn sản phẩm"};
        JPanel content_right_top_cbx = new JPanel(new BorderLayout());
        content_right_top_cbx.setPreferredSize(new Dimension(100, 180));
        cbxCauhinh = new SelectForm("Cấu hình", arrCauhinh);
        cbxCauhinh.cbb.addItemListener(this);
        txtDongia = new InputForm("Giá nhập");
        PlainDocument dongia = (PlainDocument) txtDongia.getTxtForm().getDocument();
        dongia.setDocumentFilter((new NumericDocumentFilter()));
        String[] arrPtNhap = {"Nhập theo lô", "Nhập từng máy"};
        cbxPtNhap = new SelectForm("Phương thức nhập", arrPtNhap);
        cbxPtNhap.cbb.addItemListener(this);
        cbxPtNhap.setPreferredSize(new Dimension(100, 90));
        content_right_top_cbx.add(cbxCauhinh, BorderLayout.WEST);
        content_right_top_cbx.add(txtDongia, BorderLayout.CENTER);
        content_right_top_cbx.add(cbxPtNhap, BorderLayout.SOUTH);
        content_right_top.add(txtMaSp, BorderLayout.WEST);
        content_right_top.add(txtTenSp, BorderLayout.CENTER);
        content_right_top.add(content_right_top_cbx, BorderLayout.SOUTH);

        content_right_bottom = new JPanel(new CardLayout());

        JPanel card_content_one = new JPanel(new BorderLayout());
        card_content_one.setBackground(Color.white);
        card_content_one.setPreferredSize(new Dimension(100, 90));
        JPanel card_content_one_model = new JPanel(new BorderLayout());
        card_content_one_model.setPreferredSize(new Dimension(100, 90));
        txtSoLuongImei = new InputForm("Số lượng");
        PlainDocument soluong = (PlainDocument) txtSoLuongImei.getTxtForm().getDocument();
        soluong.setDocumentFilter((new NumericDocumentFilter()));
        card_content_one_model.add(txtSoLuongImei, BorderLayout.EAST);
        card_content_one.add(card_content_one_model, BorderLayout.NORTH);

        JPanel card_content_two_model = new JPanel(new BorderLayout());
        card_content_two_model.setBorder(new EmptyBorder(10, 10, 10, 10));

        card_content_two_model.setSize(new Dimension(0, 100));
        card_content_two_model.setBackground(Color.white);
        content_right_bottom.add(card_content_one);
        content_right_bottom.add(card_content_two_model);

        content_right.add(content_right_top, BorderLayout.NORTH);
        content_right.add(content_right_bottom, BorderLayout.CENTER);

        content_top.add(content_left);
        content_top.add(content_right);

        content_btn = new JPanel();
        content_btn.setPreferredSize(new Dimension(0, 47));
        content_btn.setLayout(new GridLayout(1, 4, 5, 5));
        content_btn.setBorder(new EmptyBorder(8, 5, 0, 10));
        content_btn.setOpaque(false);
        btnAddSp = new ButtonCustom("Thêm sản phẩm", "success", 14);
        btnEditSP = new ButtonCustom("Sửa sản phẩm", "warning", 14);
        btnDelete = new ButtonCustom("Xoá sản phẩm", "danger", 14);
        btnImport = new ButtonCustom("Nhập Excel", "excel", 14);
        btnAddSp.addActionListener(this);
        btnEditSP.addActionListener(this);
        btnDelete.addActionListener(this);
        btnImport.addActionListener(this);
        btnEditSP.setEnabled(false);
        btnDelete.setEnabled(false);
        content_btn.add(btnAddSp);
        content_btn.add(btnImport);
        content_btn.add(btnEditSP);
        content_btn.add(btnDelete);

        left_top.add(content_top, BorderLayout.CENTER);

        main = new JPanel();
        main.setOpaque(false);
        main.setPreferredSize(new Dimension(0, 250));
        main.setBorder(new EmptyBorder(0, 5, 10, 10));
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.add(scrollTableDonDatHang);
        left.add(left_top, BorderLayout.CENTER);
        left.add(main, BorderLayout.SOUTH);

        right = new PanelBorderRadius();
        right.setPreferredSize(new Dimension(320, 0));
        right.setBorder(new EmptyBorder(5, 5, 5, 5));
        right.setLayout(new BorderLayout());

        JPanel right_top, right_center, right_bottom, pn_tongtien;
        right_top = new JPanel(new GridLayout(4, 1, 0, 0));
        right_top.setPreferredSize(new Dimension(300, 360));
        right_top.setOpaque(false);
        txtMaphieu = new InputForm("Mã đơn đặt hàng");
        txtMaphieu.setText("DDH" + maDonDatHang);
        txtMaphieu.setEditable(false);
        txtNhanVien = new InputForm("Nhân viên nhập");
        txtNhanVien.setText(nvDto.getHoten());
        txtNhanVien.setEditable(false);
        cbxNhaCungCap = new SelectForm("Nhà cung cấp", nccBus.getArrTenNhaCungCap());
        right_top.add(txtMaphieu);
        right_top.add(txtNhanVien);
        right_top.add(cbxNhaCungCap);

        right_center = new JPanel();
        right_center.setPreferredSize(new Dimension(100, 100));
        right_center.setOpaque(false);

        right_bottom = new JPanel(new GridLayout(2, 1));
        right_bottom.setPreferredSize(new Dimension(300, 100));
        right_bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        right_bottom.setOpaque(false);

        pn_tongtien = new JPanel(new FlowLayout(1, 20, 0));
        pn_tongtien.setOpaque(false);
        JLabel lbltien = new JLabel("TỔNG TIỀN: ");
        lbltien.setFont(new Font(FlatRobotoFont.FAMILY, 1, 18));
        lbltongtien = new JLabel("0đ");
        lbltongtien.setFont(new Font(FlatRobotoFont.FAMILY, 1, 18));
        lbltien.setForeground(new Color(255, 51, 51));
        pn_tongtien.add(lbltien);
        pn_tongtien.add(lbltongtien);
        right_bottom.add(pn_tongtien);

        btnDatHang = new ButtonCustom("Đặt hàng", "excel", 14);
        btnDatHang.addActionListener(this);
        right_bottom.add(btnDatHang);
        left_top.add(content_btn, BorderLayout.SOUTH);

        right.add(right_top, BorderLayout.NORTH);
        right.add(right_center, BorderLayout.CENTER);
        right.add(right_bottom, BorderLayout.SOUTH);

        contentCenter.add(left, BorderLayout.CENTER);
        contentCenter.add(right, BorderLayout.EAST);
    }

    public void loadDataTalbeSanPham(ArrayList<DTO.SanPhamDTO> result) {
        tblModelSP.setRowCount(0);
        for (DTO.SanPhamDTO sp : result) {
            tblModelSP.addRow(new Object[]{sp.getMasp(), sp.getTensp(), sp.getSoluongton()});
        }
    }

    public void loadDataTableChiTietPhieu(ArrayList<ChiTietDonDatHangDTO> ctPhieu) {
        tblModel.setRowCount(0);
        int size = ctPhieu.size();
        for (int i = 0; i < size; i++) {
            PhienBanSanPhamDTO pb = phienbanBus.getByMaPhienBan(ctPhieu.get(i).getMaphienbansp());
            tblModel.addRow(new Object[]{
                    i + 1, pb.getMasp(), spBUS.getByMaSP(pb.getMasp()).getTensp(), ramBus.getKichThuocById(pb.getRam()) + "GB",
                    romBus.getKichThuocById(pb.getRom()) + "GB", mausacBus.getTenMau(pb.getMausac()),
                    Formater.FormatVND(ctPhieu.get(i).getDongia()), ctPhieu.get(i).getSoluong()
            });
        }
        lbltongtien.setText(Formater.FormatVND(donDatHangBUS.getTongTien(ctPhieu)));
    }

    public void setInfoSanPham(SanPhamDTO sp) {
        this.txtMaSp.setText(Integer.toString(sp.getMasp()));
        this.txtTenSp.setText(sp.getTensp());
        ch = phienbanBus.getAll(sp.getMasp());
        cbxCauhinh.setArr(getCauHinhPhienBan(sp.getMasp()));
        this.txtDongia.setText(Integer.toString(ch.get(0).getGianhap()));
    }

    public ChiTietDonDatHangDTO getInfoChiTietPhieu() {

        int masp = Integer.parseInt(txtMaSp.getText());
        int maphienbansp = ch.get(cbxCauhinh.cbb.getSelectedIndex()).getMaphienbansp();
        int gianhap = Integer.parseInt(txtDongia.getText());
        int phuongthucnhap = cbxPtNhap.getSelectedIndex();
        int soluong = Integer.parseInt(txtSoLuongImei.getText().trim());
        ChiTietDonDatHangDTO ctphieu = new ChiTietDonDatHangDTO(phuongthucnhap, maDonDatHang, maphienbansp, soluong, gianhap);
        return ctphieu;
    }

    public boolean validateNhap() {
        int phuongthuc = cbxPtNhap.getSelectedIndex();
        if (Validation.isEmpty(txtMaSp.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm", "Chọn sản phẩm", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmail(txtDongia.getText())) {
            JOptionPane.showMessageDialog(this, "Giá không được để rỗng !", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmpty(txtSoLuongImei.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng sản phẩm", "Nhập số lượng sản phẩm", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        if (source == cbxCauhinh.cbb) {
            int index = cbxCauhinh.cbb.getSelectedIndex();
            this.txtDongia.setText(Integer.toString(ch.get(index).getGianhap()));
            ChiTietDonDatHangDTO ctp = checkTonTai();
            if (ctp == null) {
                actionbtn("add");
                this.txtSoLuongImei.setText("");
            } else {
                actionbtn("update");
            }
        } else if (source == cbxPtNhap.cbb) {
            int index = cbxPtNhap.cbb.getSelectedIndex();
            CardLayout c = (CardLayout) content_right_bottom.getLayout();
            switch (index) {
                case 0 ->
                        c.first(content_right_bottom);
                case 1 ->
                        c.last(content_right_bottom);
            }
        } else if(source == btnImport ) {
            JOptionPane.showMessageDialog(this, "Tính năng chưa phát triển");
        }
    }

    public void addCtPhieu() {
        ChiTietDonDatHangDTO ctphieu = getInfoChiTietPhieu();
        ChiTietDonDatHangDTO p = donDatHangBUS.findCT(chitietphieu, ctphieu.getMaphienbansp());
        if (p == null) {
            chitietphieu.add(ctphieu);
            loadDataTableChiTietPhieu(chitietphieu);
            resetForm();
        } else {
            int input = JOptionPane.showConfirmDialog(this, "Sản phẩm đã tồn tại trong phiếu !\nBạn có muốn chỉnh sửa không ?", "Sản phẩm đã tồn tại !", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
//                setFormChiTietPhieu(ctphieu.getMaphienbansp());
            }
        }
    }

    public ChiTietDonDatHangDTO checkTonTai() {
        int mapb = ch.get(cbxCauhinh.cbb.getSelectedIndex()).getMaphienbansp();
        ChiTietDonDatHangDTO p = donDatHangBUS.findCT(chitietphieu, mapb);
        return p;
    }

    public void actionbtn(String type) {
        boolean val_1 = type.equals("add");
        boolean val_2 = type.equals("update");
        btnAddSp.setEnabled(val_1);
        btnImport.setEnabled(val_1);
        btnEditSP.setEnabled(val_2);
        btnDelete.setEnabled(val_2);
        content_btn.revalidate();
        content_btn.repaint();
    }

    public void setFormChiTietPhieu(ChiTietDonDatHangDTO phieu) {
        PhienBanSanPhamDTO pb = phienbanBus.getByMaPhienBan(phieu.getMaphienbansp());
        this.txtMaSp.setText(Integer.toString(pb.getMasp()));
        this.txtTenSp.setText(spBUS.getByMaSP(pb.getMasp()).getTensp());
        this.cbxCauhinh.setArr(getCauHinhPhienBan(pb.getMasp()));
        this.cbxCauhinh.setSelectedIndex(phienbanBus.getIndexByMaPhienBan(ch, phieu.getMaphienbansp()));
        this.txtDongia.setText(Integer.toString(phieu.getDongia()));
    }

    public String[] getCauHinhPhienBan(int masp) {
        ch = phienbanBus.getAll(masp);
        int size = ch.size();
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = romBus.getKichThuocById(ch.get(i).getRom()) + "GB - "
                    + ramBus.getKichThuocById(ch.get(i).getRam()) + "GB - " + mausacBus.getTenMau(ch.get(i).getMausac());
        }
        return arr;
    }

    public void resetForm() {
        this.txtMaSp.setText("");
        this.txtTenSp.setText("");
        String[] arr = {"Chọn sản phẩm"};
        this.cbxCauhinh.setArr(arr);
        this.txtDongia.setText("");
        this.txtSoLuongImei.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnAddSp && validateNhap()) {
            addCtPhieu();

        } else if (source == btnDelete) {
            int index = tableDonDatHang.getSelectedRow();
            chitietphieu.remove(index);
            actionbtn("add");
            loadDataTableChiTietPhieu(chitietphieu);
            resetForm();
        } else if (source == btnEditSP) {
            int ptnhap = cbxPtNhap.getSelectedIndex();
            chitietphieu.get(rowPhieuSelect).setPhuongthucnnhap(ptnhap);
            int soluong = Integer.parseInt(txtSoLuongImei.getText().trim());
            chitietphieu.get(rowPhieuSelect).setSoluong(soluong);
            loadDataTableChiTietPhieu(chitietphieu);
        } else if (source == btnDatHang) {
            eventBtnDatHang();
        } else if(source == btnImport) {
            JOptionPane.showMessageDialog(this, "Chức năng không khả dụng !", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void eventBtnDatHang() {
        if (chitietphieu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong phiếu !", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
        } else {
            int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn tạo phiếu đặt hàng !", "Xác nhận tạo phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
                int mancc = nccBus.getByIndex(cbxNhaCungCap.getSelectedIndex()).getMancc();
                long now = System.currentTimeMillis();
                Timestamp currenTime = new Timestamp(now);
                DonDatHangDTO donDatHang = new DonDatHangDTO(mancc, maDonDatHang, nvDto.getManv(), currenTime, donDatHangBUS.getTongTien(chitietphieu), 1);
                boolean result = donDatHangBUS.add(donDatHang, chitietphieu, chitietsanpham);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Đặt hàng thành công !");
                    DonDatHang pnlDonDatHang = new DonDatHang(m, nvDto);
                    m.setPanel(pnlDonDatHang);
                } else {
                    JOptionPane.showMessageDialog(this, "Đặt hàng không thành công !", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
