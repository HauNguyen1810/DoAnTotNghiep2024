package GUI.Panel;

import java.awt.*;
import javax.swing.*;
import GUI.Component.PanelShadow;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class TrangChu extends JPanel {

    JPanel top, center, bar1, bar2;
    PanelShadow content[];
    JPanel info[];
    JLabel title, subTit, infoDetail[], objDetail[], objDetail1[], infoIcon[];
        String[][] getSt = {
        {"Tính chính xác", "tinhchinhxac_128px.svg", "<html>Hệ thống quản lý kho điện thoại cung cấp<br> thông tin tồn kho và giao dịch chính xác,<br> giúp đảm bảo sự nhất quán và chính xác<br> trong quản lý hàng hóa.</html>"},
        {"Tính bảo mật", "tinhbaomat_128px.svg", "<html>Bảo mật dữ liệu cao và kiểm soát quyền truy<br> cập chặt chẽ đảm bảo rằng thông tin về<br> hàng hóa và khách hàng được bảo vệ an<br> toàn khỏi các mối đe dọa an ninh mạng.</html>"},
        {"Tính hiệu quả", "tinhhieuqua_128px.svg", "<html>Hệ thống giúp tối ưu hóa quy trình quản lý<br> kho bằng cách tự động hóa các công việc<br> như nhập, xuất hàng và đơn hàng, tăng<br> cường hiệu suất và tiết kiệm thời gian.</html>"},
    };
    Color MainColor = new Color(255, 255, 255);
    Color FontColor = new Color(96, 125, 139);
    Color BackgroundColor = new Color(240, 247, 250);
    Color HowerFontColor = new Color(225, 230, 232);

    private void initComponent() {
        this.setBackground(new Color(24, 24, 24));
        this.setBounds(0, 200, 300, 1200);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        top = new JPanel();
        top.setBackground(MainColor);
        top.setPreferredSize(new Dimension(1100, 200));
        top.setLayout(new FlowLayout(1, 0, 10));

        JLabel slogan = new JLabel();
        slogan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/slogan.png")));
        top.add(slogan);

        this.add(top, BorderLayout.NORTH);

        center = new JPanel();
        center.setBackground(BackgroundColor);
        center.setPreferredSize(new Dimension(1100, 800));
        center.setLayout(new FlowLayout(1, 50, 50));

        content = new PanelShadow[getSt.length];
        info = new JPanel[3];
        infoDetail = new JLabel[3];
        objDetail = new JLabel[3];
        objDetail1 = new JLabel[3];

        infoIcon = new JLabel[3];

        for (int i = 0; i < getSt.length; i++) {
              
              content[i] = new PanelShadow(getSt[i][1], getSt[i][0], getSt[i][2]);
              center.add(content[i]);
//            content[i] = new PanelShadow();
//            content[i].setPreferredSize(new Dimension(300, 450));
//            content[i].setBackground(MainColor);
//            content[i].setLayout(new FlowLayout(1, 0, 10));
//
//            info[i] = new JPanel();
//            info[i].setPreferredSize(new Dimension(250, 150));
//            info[i].setBackground(BackgroundColor);
//            info[i].setLayout(null);
//
//            infoIcon[i] = new JLabel();
//            infoIcon[i].setBounds(60, 20, 120, 120);
//            infoIcon[i].setIcon(new javax.swing.ImageIcon(getClass().getResource(iconArr[i])));
//            info[i].add(infoIcon[i]);
//
//            content[i].add(info[i]);
//
//            infoDetail[i] = new JLabel(tkArr[i]);
//            infoDetail[i].setPreferredSize(new Dimension(190, 60));
//            infoDetail[i].setFont(new Font("Segoe UI", Font.BOLD, 16));
//            content[i].add(infoDetail[i]);
//
//            objDetail[i] = new JLabel(obj1Arr[i]);
//            objDetail[i].setPreferredSize(new Dimension(220, 20));
//            objDetail[i].setFont(new Font("Segoe UI", Font.PLAIN, 15));
//            content[i].add(objDetail[i]);
//
//            objDetail[i] = new JLabel(obj2Arr[i]);
//            objDetail[i].setPreferredSize(new Dimension(220, 20));
//            objDetail[i].setFont(new Font("Segoe UI", Font.PLAIN, 15));
//            content[i].add(objDetail[i]);
//
//            center.add(content[i]);

        }

        this.add(center, BorderLayout.CENTER);

    }

    public TrangChu() {
        initComponent();
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
    }


}
