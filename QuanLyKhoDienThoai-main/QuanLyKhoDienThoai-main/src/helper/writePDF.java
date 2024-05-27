package helper;

import BUS.DungLuongRamBUS;
import BUS.DungLuongRomBUS;
import BUS.MauSacBUS;
import DAO.*;
import DTO.*;
import com.itextpdf.text.Chunk;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.Date;

public class writePDF {

    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY");
    Document document = new Document();
    FileOutputStream file;
    JFrame jf = new JFrame();
    FileDialog fd = new FileDialog(jf, "Xuất pdf", FileDialog.SAVE);
    Font fontNormal10;
    Font fontBold15;
    Font fontBold25;
    Font fontBoldItalic15;
    
    
    DungLuongRomBUS romBus = new DungLuongRomBUS();
    DungLuongRamBUS ramBus = new DungLuongRamBUS();
    MauSacBUS mausacBus = new MauSacBUS();

    public writePDF() {
        try {
            fontNormal10 = new Font(BaseFont.createFont("lib/TimesNewRoman/SVN-Times New Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, Font.NORMAL);
            fontBold25 = new Font(BaseFont.createFont("lib/TimesNewRoman/SVN-Times New Roman Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 25, Font.NORMAL);
            fontBold15 = new Font(BaseFont.createFont("lib/TimesNewRoman/SVN-Times New Roman Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 15, Font.NORMAL);
            fontBoldItalic15 = new Font(BaseFont.createFont("lib/TimesNewRoman/SVN-Times New Roman Bold Italic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 15, Font.NORMAL);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(writePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chooseURL(String url) {
        try {
            document.close();
            document = new Document();
            file = new FileOutputStream(url);
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Khong tim thay duong dan file " + url);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Khong goi duoc document !");
        }
    }

    public void setTitle(String title) {
        try {
            Paragraph pdfTitle = new Paragraph(new Phrase(title, fontBold25));
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pdfTitle);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    private String getFile(String name) {
        fd.pack();
        fd.setSize(800, 600);
        fd.validate();
        Rectangle rect = jf.getContentPane().getBounds();
        double width = fd.getBounds().getWidth();
        double height = fd.getBounds().getHeight();
        double x = rect.getCenterX() - (width / 2);
        double y = rect.getCenterY() - (height / 2);
        Point leftCorner = new Point();
        leftCorner.setLocation(x, y);
        fd.setLocation(leftCorner);
        fd.setFile(name);
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("null")) {
            return null;
        }
        return url;
    }

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static Chunk createWhiteSpace(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(" ");
        }
        return new Chunk(builder.toString());
    }
//    public void writePN(int maphieu) {
//    String url = "";
//    try {
//        fd.setTitle("In phiếu nhập");
//        fd.setLocationRelativeTo(null);
//        url = getFile(maphieu + "");
//        if (url.equals("nullnull")) {
//            return;
//        }
//        url = url + ".pdf";
//        file = new FileOutputStream(url);
//        document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, file);
//        document.open();
//
//        Paragraph company = new Paragraph("Hệ thống quản lý điện thoại HPHONE", fontBold15);
//        company.add(new Chunk(createWhiteSpace(20)));
//        Date today = new Date(System.currentTimeMillis());
//        company.setAlignment(Element.ALIGN_LEFT);
//        document.add(company);
//
//        document.add(Chunk.NEWLINE);
//        Paragraph header = new Paragraph("THÔNG TIN PHIẾU NHẬP", fontBold25);
//        header.setAlignment(Element.ALIGN_CENTER);
//        document.add(header);
//        PhieuNhapDTO pn = PhieuNhapDAO.getInstance().selectById(maphieu + "");
//
//        Paragraph paragraph1 = new Paragraph("Mã phiếu: PN-" + pn.getMaphieu(), fontNormal10);
//        String ncc = NhaCungCapDAO.getInstance().selectById(pn.getManhacungcap() + "").getTenncc();
//        Paragraph paragraph2 = new Paragraph("Nhà cung cấp: " + ncc, fontNormal10);
//        paragraph2.add(new Chunk(createWhiteSpace(5)));
//        paragraph2.add(new Chunk("-"));
//        paragraph2.add(new Chunk(createWhiteSpace(5)));
//        String diachincc = NhaCungCapDAO.getInstance().selectById(pn.getManhacungcap() + "").getDiachi();
//        paragraph2.add(new Chunk(diachincc, fontNormal10));
//
//        String ngtao = NhanVienDAO.getInstance().selectById(pn.getManguoitao() + "").getHoten();
//        Paragraph paragraph3 = new Paragraph("Người thực hiện: " + ngtao, fontNormal10);
//        paragraph3.add(new Chunk(createWhiteSpace(5)));
//        paragraph3.add(new Chunk("-"));
//        paragraph3.add(new Chunk(createWhiteSpace(5)));
//        paragraph3.add(new Chunk("Mã nhân viên: " + pn.getManguoitao(), fontNormal10));
//        Paragraph paragraph4 = new Paragraph("Thời gian nhập: " + formatDate.format(pn.getThoigiantao()), fontNormal10);
//        document.add(paragraph1);
//        document.add(paragraph2);
//        document.add(paragraph3);
//        document.add(paragraph4);
//        document.add(Chunk.NEWLINE);
//
//        // Thêm table 7 cột vào file PDF
//        PdfPTable table = new PdfPTable(7); // Thêm cột "Số lượng đặt" nữa
//        table.setWidthPercentage(100);
//        table.setWidths(new float[]{5f, 20f, 20f, 15f, 10f, 10f, 20f}); // Điều chỉnh tỷ lệ chiều rộng của các cột
//        PdfPCell cell;
//
//        // Thêm header cho table
//        table.addCell(new PdfPCell(new Phrase("STT", fontBold15))); // Thêm cột số thứ tự
//        table.addCell(new PdfPCell(new Phrase("Tên sản phẩm", fontBold15)));
//        table.addCell(new PdfPCell(new Phrase("Phiên bản", fontBold15)));
//        table.addCell(new PdfPCell(new Phrase("Giá", fontBold15)));
//        table.addCell(new PdfPCell(new Phrase("Số lượng", fontBold15)));
//        table.addCell(new PdfPCell(new Phrase("Số lượng đặt", fontBold15))); // Cột mới
//        table.addCell(new PdfPCell(new Phrase("Tổng tiền", fontBold15)));
//
//        // Đếm số thứ tự
//        int stt = 0;
//
//        // Truyền thông tin từng chi tiết vào table
//        for (ChiTietPhieuDTO ctp : ChiTietPhieuNhapDAO.getInstance().selectAll(maphieu + "")) {
//            stt++; // Tăng giá trị của biến đếm
//            // Thêm dòng mới vào table
//            table.addCell(new PdfPCell(new Phrase(String.valueOf(stt), fontNormal10))); // Thêm giá trị số thứ tự vào cột số thứ tự
//            // Tiếp tục thêm thông tin sản phẩm như đã làm trước đó
//            SanPhamDTO sp = new SanPhamDAO().selectByPhienBan(ctp.getMaphienbansp() + "");
//            table.addCell(new PdfPCell(new Phrase(sp.getTensp(), fontNormal10)));
//            PhienBanSanPhamDTO pbsp = new PhienBanSanPhamDAO().selectById(ctp.getMaphienbansp());
//            table.addCell(new PdfPCell(new Phrase(romBus.getKichThuocById(pbsp.getRom()) + "GB - "
//                    + ramBus.getKichThuocById(pbsp.getRam()) + "GB - " + mausacBus.getTenMau(pbsp.getMausac()), fontNormal10)));
//            table.addCell(new PdfPCell(new Phrase(formatter.format(ctp.getDongia()) + "đ", fontNormal10)));
//            table.addCell(new PdfPCell(new Phrase(String.valueOf(ctp.getSoluong()), fontNormal10)));
//
//            // Giả sử bạn có thể lấy số lượng đặt từ ChiTietPhieuDTO
//            // Nếu thông tin này không có sẵn, bạn cần lấy từ một nguồn khác, ví dụ: ChiTietPhieuDatDAO
//            int soLuongDat = ChiTietDonDatHangDAO.getInstance().getSoLuongDatByPhieuNhap(ctp.getMaphieu());
//            table.addCell(new PdfPCell(new Phrase(String.valueOf(soLuongDat), fontNormal10))); // Cột mới: Số lượng đặt
//
//            table.addCell(new PdfPCell(new Phrase(formatter.format(ctp.getSoluong() * ctp.getDongia()) + "đ", fontNormal10)));
//        }
//
//        document.add(table);
//        document.add(Chunk.NEWLINE);
//
//        Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thành tiền: " + formatter.format(pn.getTongTien()) + "đ", fontBold15));
//        paraTongThanhToan.setIndentationLeft(300);
//
//        document.add(paraTongThanhToan);
//        document.add(Chunk.NEWLINE);
//        document.add(Chunk.NEWLINE);
//
//        Paragraph paragraph = new Paragraph();
//        paragraph.setIndentationLeft(22);
//        paragraph.add(new Chunk("Người lập phiếu", fontBoldItalic15));
//        paragraph.add(new Chunk(createWhiteSpace(30)));
//        paragraph.add(new Chunk("Nhân viên nhận", fontBoldItalic15));
//        paragraph.add(new Chunk(createWhiteSpace(30)));
//        paragraph.add(new Chunk("Nhà cung cấp", fontBoldItalic15));
//
//        Paragraph sign = new Paragraph();
//        sign.setIndentationLeft(23);
//        sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
//        sign.add(new Chunk(createWhiteSpace(30)));
//        sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
//        sign.add(new Chunk(createWhiteSpace(28)));
//        sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
//        document.add(paragraph);
//        document.add(sign);
//
//        document.close();
//        writer.close();
//        openFile(url);
//
//    } catch (DocumentException | FileNotFoundException ex) {
//        JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
//    }
//}

    public void writePN(int maphieu) {
        String url = "";
        try {
            fd.setTitle("In phiếu nhập");
            fd.setLocationRelativeTo(null);
            url = getFile(maphieu + "");
            if (url.equals("nullnull")) {
                return;
            }
            url = url + ".pdf";
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();

            Paragraph company = new Paragraph("Hệ thống quản lý điện thoại HPHONE", fontBold15);
            company.add(new Chunk(createWhiteSpace(20)));
            Date today = new Date(System.currentTimeMillis());
//            company.add(new Chunk("Thời gian in phiếu: " + formatDate.format(today), fontNormal10));
            company.setAlignment(Element.ALIGN_LEFT);
            document.add(company);
            // Thêm tên công ty vào file PDF
            document.add(Chunk.NEWLINE);
            Paragraph header = new Paragraph("PHIẾU NHẬP KHO", fontBold25);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            PhieuNhapDTO pn = PhieuNhapDAO.getInstance().selectById(maphieu + "");
            // Thêm dòng Paragraph vào file PDF

            Paragraph paragraph1 = new Paragraph("Mã phiếu: PN-" + pn.getMaphieu(), fontNormal10);
            String ncc = NhaCungCapDAO.getInstance().selectById(pn.getManhacungcap() + "").getTenncc();
            Paragraph paragraph2 = new Paragraph("Nhà cung cấp: " + ncc, fontNormal10);
            paragraph2.add(new Chunk(createWhiteSpace(5)));
            paragraph2.add(new Chunk("-"));
            paragraph2.add(new Chunk(createWhiteSpace(5)));
            String diachincc = NhaCungCapDAO.getInstance().selectById(pn.getManhacungcap() + "").getDiachi();
            paragraph2.add(new Chunk(diachincc, fontNormal10));

            String ngtao = NhanVienDAO.getInstance().selectById(pn.getManguoitao() + "").getHoten();
            Paragraph paragraph3 = new Paragraph("Người thực hiện: " + ngtao, fontNormal10);
            paragraph3.add(new Chunk(createWhiteSpace(5)));
            paragraph3.add(new Chunk("-"));
            paragraph3.add(new Chunk(createWhiteSpace(5)));
            paragraph3.add(new Chunk("Mã nhân viên: " + pn.getManguoitao(), fontNormal10));
            Paragraph paragraph4 = new Paragraph("Thời gian nhập: " + formatDate.format(pn.getThoigiantao()), fontNormal10);
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(Chunk.NEWLINE);

            // Thêm table 5 cột vào file PDF
            PdfPTable table = new PdfPTable(6); // Thêm một cột thứ tự nữa
            table.setWidthPercentage(100);
            table.setWidths(new float[]{5f, 25f, 30f, 15f, 10f, 15f}); // Điều chỉnh tỷ lệ chiều rộng của các cột
            PdfPCell cell;

// Thêm header cho table
            table.addCell(new PdfPCell(new Phrase("STT", fontBold15))); // Thêm cột số thứ tự
            table.addCell(new PdfPCell(new Phrase("Tên sản phẩm", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Phiên bản", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Giá", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Số lượng", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Tổng tiền", fontBold15)));

// Đếm số thứ tự
            int stt = 0;

// Truyền thông tin từng chi tiết vào table
            for (ChiTietPhieuDTO ctp : ChiTietPhieuNhapDAO.getInstance().selectAll(maphieu + "")) {
                stt++; // Tăng giá trị của biến đếm
                // Thêm dòng mới vào table
                table.addCell(new PdfPCell(new Phrase(String.valueOf(stt), fontNormal10))); // Thêm giá trị số thứ tự vào cột số thứ tự
                // Tiếp tục thêm thông tin sản phẩm như đã làm trước đó
                SanPhamDTO sp = new SanPhamDAO().selectByPhienBan(ctp.getMaphienbansp() + "");
                table.addCell(new PdfPCell(new Phrase(sp.getTensp(), fontNormal10)));
                PhienBanSanPhamDTO pbsp = new PhienBanSanPhamDAO().selectById(ctp.getMaphienbansp());
                table.addCell(new PdfPCell(new Phrase(romBus.getKichThuocById(pbsp.getRom()) + "GB - "
                        + ramBus.getKichThuocById(pbsp.getRam()) + "GB - " + mausacBus.getTenMau(pbsp.getMausac()), fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(formatter.format(ctp.getDongia()) + "đ", fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ctp.getSoluong()), fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(formatter.format(ctp.getSoluong() * ctp.getDongia()) + "đ", fontNormal10)));
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thành tiền: " + formatter.format(pn.getTongTien()) + "đ", fontBold15));
            paraTongThanhToan.setIndentationRight(300);

            document.add(paraTongThanhToan);
            document.add(Chunk.NEWLINE);

            Paragraph paraTongThanh = new Paragraph(new Phrase("Tổng thành tiền(Viết bằng chữ): ", fontBold15));
            paraTongThanh.setIndentationRight(300);
            document.add(paraTongThanh);
            document.add(Chunk.NEWLINE);
            
            Paragraph dateTitleAccountant = new Paragraph();
            dateTitleAccountant.setAlignment(Element.ALIGN_RIGHT);
            dateTitleAccountant.add(new Chunk("Ngày   Tháng   Năm     ", fontNormal10)); // Thêm tiêu đề "Ngày Tháng Năm"

             document.add(dateTitleAccountant);
             
      
            Paragraph paragraph = new Paragraph();
            paragraph.setIndentationLeft(22);
            paragraph.add(new Chunk("Người lập phiếu", fontBoldItalic15));
            paragraph.add(new Chunk(createWhiteSpace(30)));
            paragraph.add(new Chunk("Người giao hàng", fontBoldItalic15));
            paragraph.add(new Chunk(createWhiteSpace(30)));
            paragraph.add(new Chunk("Thủ kho", fontBoldItalic15));
            paragraph.add(new Chunk(createWhiteSpace(30)));

            paragraph.setSpacingBefore(10f);
            paragraph.setSpacingAfter(10f);
                    
            Paragraph sign = new Paragraph();
            sign.setIndentationLeft(23);
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            sign.add(new Chunk(createWhiteSpace(30)));
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            sign.add(new Chunk(createWhiteSpace(28)));
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            sign.add(new Chunk(createWhiteSpace(30)));

            document.add(paragraph);
            document.add(sign);
           

            document.close();
            writer.close();
            openFile(url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        }

    }

    public void writePX(int maphieu) {
        String url = "";
        try {
            fd.setTitle("In phiếu xuất");
            fd.setLocationRelativeTo(null);
            url = getFile(maphieu + "");
            if (url.equals("nullnull")) {
                return;
            }
            url = url + ".pdf";
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();

//            Paragraph company = new Paragraph("Hệ thống quản lý điện thoại HPHONE", fontBold15);
//            company.add(new Chunk(createWhiteSpace(20)));
//            Date today = new Date(System.currentTimeMillis());
////            company.add(new Chunk("Thời gian in phiếu: " + formatDate.format(today), fontNormal10));
//            company.setAlignment(Element.ALIGN_LEFT);
//            document.add(company);
            // Thêm tên công ty vào file PDF
            document.add(Chunk.NEWLINE);
            Paragraph p1= new Paragraph("Tên tổ chức, cá nhân: Kho điện thoại HPHONE",fontNormal10);
            Paragraph p2= new Paragraph("Địa chỉ: Đội 2, Xã Thư Phú, Huyện Thường Tín, Thành phố Hà Nội",fontNormal10);
            Paragraph p3= new Paragraph("Mã số thuế: 0398745123",fontNormal10);
            document.add(p1);
            document.add(p2);
            document.add(p3);
            Paragraph header = new Paragraph("PHIẾU XUẤT KHO", fontBold25);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            Paragraph p4= new Paragraph("Ngày...tháng...năm... ",fontNormal10);
            p4.setAlignment(Element.ALIGN_CENTER);
            document.add(p4);
            
             PhieuXuatDTO px = PhieuXuatDAO.getInstance().selectById(maphieu + "");
            Paragraph p5= new Paragraph("Họ tên người vận chuyển: ",fontNormal10);
            Paragraph p6= new Paragraph("Phương tiện người vận chuyển: ",fontNormal10);
            Paragraph p7= new Paragraph("Xuất tại: Kho điện thoại HPHONE",fontNormal10);
            String diachikh = KhachHangDAO.getInstance().selectById(px.getMakh() + "").getDiachi();
            Paragraph p8= new Paragraph("Nhập tại: "+diachikh,fontNormal10);
            document.add(p5);
            document.add(p6);
            document.add(p7);
            document.add(p8);
           
            // Thêm dòng Paragraph vào file PDF
////
//            Paragraph paragraph1 = new Paragraph("Mã phiếu: PX-" + px.getMaphieu(), fontNormal10);
//            String hoten = KhachHangDAO.getInstance().selectById(px.getMakh() + "").getHoten();
//            Paragraph paragraph2 = new Paragraph("khách hàng: " + hoten, fontNormal10);
//            paragraph2.add(new Chunk(createWhiteSpace(5)));
//            paragraph2.add(new Chunk("-"));
//            paragraph2.add(new Chunk(createWhiteSpace(5)));
//            String diachikh = KhachHangDAO.getInstance().selectById(px.getMakh() + "").getDiachi();
//            paragraph2.add(new Chunk(diachikh, fontNormal10));

//            String ngtao = NhanVienDAO.getInstance().selectById(px.getManguoitao() + "").getHoten();
//            Paragraph paragraph3 = new Paragraph("Người thực hiện: " + ngtao, fontNormal10);
//            paragraph3.add(new Chunk(createWhiteSpace(5)));
//            paragraph3.add(new Chunk("-"));
//            paragraph3.add(new Chunk(createWhiteSpace(5)));
//            paragraph3.add(new Chunk("Mã nhân viên: " + px.getManguoitao(), fontNormal10));
//            Paragraph paragraph4 = new Paragraph("Thời gian nhập: " + formatDate.format(px.getThoigiantao()), fontNormal10);
//            document.add(paragraph1);
//            document.add(paragraph2);
//            document.add(paragraph3);
//            document.add(paragraph4);
            document.add(Chunk.NEWLINE);
            // Thêm table 5 cột vào file PDF
            // Thêm table 5 cột vào file PDF
            PdfPTable table = new PdfPTable(6); // Thêm một cột thứ tự nữa
            table.setWidthPercentage(100);
            table.setWidths(new float[]{5f, 25f, 30f, 15f, 10f, 15f}); // Điều chỉnh tỷ lệ chiều rộng của các cột
            PdfPCell cell;

// Thêm header cho table
            table.addCell(new PdfPCell(new Phrase("STT", fontBold15))); // Thêm cột số thứ tự
            table.addCell(new PdfPCell(new Phrase("Tên sản phẩm", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Phiên bản", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Giá", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Số lượng", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Tổng tiền", fontBold15)));

// Đếm số thứ tự
            int stt = 0;

// Truyền thông tin từng chi tiết vào table
            for (ChiTietPhieuDTO ctp : ChiTietPhieuXuatDAO.getInstance().selectAll(maphieu + "")) {
                stt++; // Tăng giá trị của biến đếm
                // Thêm dòng mới vào table
                table.addCell(new PdfPCell(new Phrase(String.valueOf(stt), fontNormal10))); // Thêm giá trị số thứ tự vào cột số thứ tự
                // Tiếp tục thêm thông tin sản phẩm như đã làm trước đó
                SanPhamDTO sp = new SanPhamDAO().selectByPhienBan(ctp.getMaphienbansp() + "");
                table.addCell(new PdfPCell(new Phrase(sp.getTensp(), fontNormal10)));
                PhienBanSanPhamDTO pbsp = new PhienBanSanPhamDAO().selectById(ctp.getMaphienbansp());
                table.addCell(new PdfPCell(new Phrase(romBus.getKichThuocById(pbsp.getRom()) + "GB - "
                        + ramBus.getKichThuocById(pbsp.getRam()) + "GB - " + mausacBus.getTenMau(pbsp.getMausac()), fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(formatter.format(ctp.getDongia()) + "đ", fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ctp.getSoluong()), fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(formatter.format(ctp.getSoluong() * ctp.getDongia()) + "đ", fontNormal10)));
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thành tiền: " + formatter.format(px.getTongTien()) + "đ", fontBold15));
            paraTongThanhToan.setIndentationRight(300);

            document.add(paraTongThanhToan);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
             Paragraph dateTitleAccountant = new Paragraph();
            dateTitleAccountant.setAlignment(Element.ALIGN_RIGHT);
            dateTitleAccountant.add(new Chunk("....., Ngày   Tháng   Năm                                    ", fontNormal10)); // Thêm tiêu đề "Ngày Tháng Năm"

             document.add(dateTitleAccountant);
             

            Paragraph paragraphGD = new Paragraph("THỦ TRƯỞNG ĐƠN VỊ",fontBoldItalic15);
            paragraphGD.setIndentationLeft(300);
//            paragraph.setIndentationLeft(22);
//            paragraph.add(new Chunk("Người lập phiếu", fontBoldItalic15));
//            paragraph.add(new Chunk(createWhiteSpace(30)));
//            paragraph.add(new Chunk("Nhân viên nhận", fontBoldItalic15));
//            paragraph.add(new Chunk(createWhiteSpace(30)));
//            paragraph.add(new Chunk("Tổng giám đốc", fontBoldItalic15));
            
            Paragraph sign = new Paragraph("(Ký và ghi rõ họ tên)      ", fontNormal10);
            sign.setIndentationLeft(330);
//            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
//            sign.add(new Chunk(createWhiteSpace(30)));
//            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
//            sign.add(new Chunk(createWhiteSpace(28)));
//            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            document.add(paragraphGD);
            document.add(sign);

//            Paragraph paragraph = new Paragraph();
//            paragraph.setIndentationLeft(22);
//            paragraph.add(new Chunk("Người lập phiếu", fontBoldItalic15));
//            paragraph.add(new Chunk(createWhiteSpace(30)));
//            paragraph.add(new Chunk("Người giao", fontBoldItalic15));
//            paragraph.add(new Chunk(createWhiteSpace(30)));
//            paragraph.add(new Chunk("Khách hàng", fontBoldItalic15));
//
//            Paragraph sign = new Paragraph();
//            sign.setIndentationLeft(20);
//            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
//            sign.add(new Chunk(createWhiteSpace(25)));
//            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
//            sign.add(new Chunk(createWhiteSpace(23)));
//            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
//            document.add(paragraph);
//            document.add(sign);
            document.close();
            writer.close();
            openFile(url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        }
    }

    public void writeDDH(int maphieu) {
        String url = "";
        try {
            fd.setTitle("In đơn đặt hàng");
            fd.setLocationRelativeTo(null);
            url = getFile(maphieu + "");
            if (url.equals("nullnull")) {
                return;
            }
            url = url + ".pdf";
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();

            Paragraph company = new Paragraph("Hệ thống quản lý điện thoại HPHONE", fontBold15);
            company.add(new Chunk(createWhiteSpace(20)));
            Date today = new Date(System.currentTimeMillis());
//            company.add(new Chunk("Thời gian in phiếu: " + formatDate.format(today), fontNormal10));
            company.setAlignment(Element.ALIGN_LEFT);
            document.add(company);
            // Thêm tên công ty vào file PDF
            document.add(Chunk.NEWLINE);
            Paragraph header = new Paragraph("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", fontBold15);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
             Paragraph header2 = new Paragraph("Độc lập - Tự do - Hạnh phúc", fontBold15);
            header2.setAlignment(Element.ALIGN_CENTER);
            document.add(header2);
             Paragraph header3 = new Paragraph("ĐƠN ĐẶT HÀNG", fontBold15);
            header3.setAlignment(Element.ALIGN_CENTER);
            document.add(header3);
            document.add(Chunk.NEWLINE);
            DonDatHangDTO ddh = DonDatHangDAO.getInstance().selectById(maphieu + "");
            // Thêm dòng Paragraph vào file PDF

            Paragraph paragraph1 = new Paragraph("Mã phiếu: DDH-" + ddh.getMaphieu(), fontNormal10);
            String ncc = NhaCungCapDAO.getInstance().selectById(ddh.getManhacungcap() + "").getTenncc();
            Paragraph paragraph2 = new Paragraph("Kính gửi: " + ncc, fontNormal10);
            paragraph2.add(new Chunk(createWhiteSpace(5)));
            paragraph2.add(new Chunk("-"));
            paragraph2.add(new Chunk(createWhiteSpace(5)));
            String diachincc = NhaCungCapDAO.getInstance().selectById(ddh.getManhacungcap() + "").getDiachi();
            paragraph2.add(new Chunk(diachincc, fontNormal10));

//            String ngtao = NhanVienDAO.getInstance().selectById(ddh.getManguoitao() + "").getHoten();
//            Paragraph paragraph3 = new Paragraph("Người thực hiện: " + ngtao, fontNormal10);
//            paragraph3.add(new Chunk(createWhiteSpace(5)));
//            paragraph3.add(new Chunk("-"));
//            paragraph3.add(new Chunk(createWhiteSpace(5)));
//            paragraph3.add(new Chunk("Mã nhân viên: " + ddh.getManguoitao(), fontNormal10));
//            Paragraph paragraph4 = new Paragraph("Thời gian đặt hàng: " + formatDate.format(ddh.getThoigiantao()), fontNormal10);
              Paragraph paragrap5= new Paragraph("Nội dung đặt hàng như sau: ",fontNormal10);
            document.add(paragraph1);
            document.add(paragraph2);
//            document.add(paragraph3);
//            document.add(paragraph4);
            document.add(paragrap5);
            document.add(Chunk.NEWLINE);
            // Thêm table 5 cột vào file PDF
            // Thêm table 5 cột vào file PDF
            PdfPTable table = new PdfPTable(6); // Thêm một cột thứ tự nữa
            table.setWidthPercentage(100);
            table.setWidths(new float[]{5f, 25f, 30f, 15f, 10f, 15f}); // Điều chỉnh tỷ lệ chiều rộng của các cột
            PdfPCell cell;

// Thêm header cho table
            table.addCell(new PdfPCell(new Phrase("STT", fontBold15))); // Thêm cột số thứ tự
            table.addCell(new PdfPCell(new Phrase("Tên sản phẩm", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Phiên bản", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Giá", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Số lượng", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Tổng tiền", fontBold15)));

// Đếm số thứ tự
            int stt = 0;

// Truyền thông tin từng chi tiết vào table
            for (ChiTietPhieuDTO ctp : ChiTietDonDatHangDAO.getInstance().selectAll(maphieu + "")) {
                stt++; // Tăng giá trị của biến đếm
                // Thêm dòng mới vào table
                table.addCell(new PdfPCell(new Phrase(String.valueOf(stt), fontNormal10))); // Thêm giá trị số thứ tự vào cột số thứ tự
                // Tiếp tục thêm thông tin sản phẩm như đã làm trước đó
                SanPhamDTO sp = new SanPhamDAO().selectByPhienBan(ctp.getMaphienbansp() + "");
                table.addCell(new PdfPCell(new Phrase(sp.getTensp(), fontNormal10)));
                PhienBanSanPhamDTO pbsp = new PhienBanSanPhamDAO().selectById(ctp.getMaphienbansp());
                table.addCell(new PdfPCell(new Phrase(romBus.getKichThuocById(pbsp.getRom()) + "GB - "
                        + ramBus.getKichThuocById(pbsp.getRam()) + "GB - " + mausacBus.getTenMau(pbsp.getMausac()), fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(formatter.format(ctp.getDongia()) + "đ", fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ctp.getSoluong()), fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(formatter.format(ctp.getSoluong() * ctp.getDongia()) + "đ", fontNormal10)));
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thành tiền: " + formatter.format(ddh.getTongTien()) + "đ", fontBold15));
            paraTongThanhToan.setIndentationRight(300);
            Paragraph paragrap7= new Paragraph("Thời gian giao hàng:        ",fontNormal10);
           
            Paragraph paragrap6= new Paragraph("Địa điểm giao hàng:        ",fontNormal10);
            document.add(paraTongThanhToan);
            document.add(paragrap7);
            document.add(paragrap6);
            document.add(Chunk.NEWLINE);
            Paragraph dateTitleAccountant = new Paragraph();
            dateTitleAccountant.setAlignment(Element.ALIGN_RIGHT);
            dateTitleAccountant.add(new Chunk("....., Ngày   Tháng   Năm     ", fontNormal10)); // Thêm tiêu đề "Ngày Tháng Năm"

             document.add(dateTitleAccountant);
             

            Paragraph paragraphGD = new Paragraph("Giám Đốc             ",fontBoldItalic15);
            paragraphGD.setIndentationLeft(430);
//            paragraph.setIndentationLeft(22);
//            paragraph.add(new Chunk("Người lập phiếu", fontBoldItalic15));
//            paragraph.add(new Chunk(createWhiteSpace(30)));
//            paragraph.add(new Chunk("Nhân viên nhận", fontBoldItalic15));
//            paragraph.add(new Chunk(createWhiteSpace(30)));
//            paragraph.add(new Chunk("Tổng giám đốc", fontBoldItalic15));
            
            Paragraph sign = new Paragraph("(Ký và ghi rõ họ tên)      ", fontNormal10);
            sign.setIndentationLeft(400);
//            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
//            sign.add(new Chunk(createWhiteSpace(30)));
//            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
//            sign.add(new Chunk(createWhiteSpace(28)));
//            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            document.add(paragraphGD);
            document.add(sign);

            document.close();
            writer.close();
            openFile(url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        }

    }

}
