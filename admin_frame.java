package Hospitaladmin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Jtable.Jtable;

public class admin_frame extends Jtable {

    JFrame frame;
    JMenuBar menuBar = new JMenuBar();//菜单栏组件
    JLabel l1 = new JLabel("欢迎进入");
    JLabel l2 = new JLabel("杏林云枢·智慧协同诊疗系统");
    JMenu menu1 = new JMenu("科室信息管理");
    JMenu menu2 = new JMenu("医生信息管理");
    JMenu menu3 = new JMenu("病房信息管理");
    JMenu menu4 = new JMenu("床位信息管理");
    JMenu menu5 = new JMenu("病人信息管理");
    JMenu menu6 = new JMenu("药品信息管理"); // 新增药品信息管理菜单
    JMenuItem insert_department, manage_department;
    JMenuItem insert_doctor, manage_doctor;
    JMenuItem insert_ward, manage_ward;
    JMenuItem insert_bed, manage_bed;
    JMenuItem insert_patient, manage_patient;
    JMenuItem Alter_Bed, Delete_Bed;
    JMenuItem Alter_Department, Delete_Department;
    JMenuItem Alter_Doctor, Delete_Doctor;
    JMenuItem Alter_Patient, Delete_Patient;
    JMenuItem Alter_Ward, Delete_Ward;
    JMenuItem insert_drug, manage_drug; // 新增插入和管理药品信息菜单项
    JMenuItem Alter_Drug, Delete_Drug; // 新增修改和删除药品信息菜单项

    JToolBar jToolBar;
    JButton jButton1, jButton2, jButton3, jButton4, jButton5, jButton6, jButton7;

    private Jtable table = new Jtable() {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel dm = null;

    public admin_frame() {
        // 初始化主界面
        frame = new JFrame("医院内部信息管理");
        frame.setBounds(330, 200, 800, 650);
        String path = "C:\\Users\\86175\\Desktop\\杏林云枢·智慧协同诊疗系统\\杏林云枢·智慧协同诊疗系统\\HospitalWardManagementSystem\\lib\\Main.png";
        ImageIcon backgroundimage = new ImageIcon(path);
        JLabel jLabel = new JLabel(backgroundimage);
        jLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        JPanel jPanel1 = (JPanel) frame.getContentPane();
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);
        frame.getLayeredPane().add(jLabel, new Integer(Integer.MIN_VALUE));
        frame.setVisible(true);

        l1.setBounds(90, 70, 450, 40);
        l1.setFont(new Font("微软雅黑", Font.BOLD, 40));
        l2.setBounds(220, 120, 550, 43);
        l2.setFont(new Font("微软雅黑", Font.BOLD, 40));

        // 初始化科室信息管理菜单项
        insert_department = new JMenuItem("插入科室");
        manage_department = new JMenuItem("管理科室");
        Alter_Department = new JMenuItem("修改科室信息");
        Delete_Department = new JMenuItem("删除科室信息");

        // 初始化医生信息管理菜单项
        insert_doctor = new JMenuItem("插入医生");
        manage_doctor = new JMenuItem("管理医生");
        Alter_Doctor = new JMenuItem("修改医生信息");
        Delete_Doctor = new JMenuItem("删除医生信息");

        // 初始化病房信息管理菜单项
        insert_ward = new JMenuItem("插入病房");
        manage_ward = new JMenuItem("管理病房");
        Alter_Ward = new JMenuItem("修改病房信息");
        Delete_Ward = new JMenuItem("删除病房信息");

        // 初始化床位信息管理菜单项
        insert_bed = new JMenuItem("插入床位");
        manage_bed = new JMenuItem("管理床位");
        Alter_Bed = new JMenuItem("修改床位信息");
        Delete_Bed = new JMenuItem("删除床位信息");

        // 初始化病人信息管理菜单项
        insert_patient = new JMenuItem("插入病人");
        manage_patient = new JMenuItem("管理病人");
        Alter_Patient = new JMenuItem("修改病人信息");
        Delete_Patient = new JMenuItem("删除病人信息");

        // 初始化药品信息管理菜单项
        insert_drug = new JMenuItem("插入药品");
        manage_drug = new JMenuItem("管理药品");
        Alter_Drug = new JMenuItem("修改药品信息");
        Delete_Drug = new JMenuItem("删除药品信息");

        // 将菜单项加入菜单
        menu1.add(Alter_Department);
        menu1.add(Delete_Department);
        menu1.add(insert_department);
        menu1.add(manage_department);

        menu2.add(Alter_Doctor);
        menu2.add(Delete_Doctor);
        menu2.add(insert_doctor);
        menu2.add(manage_doctor);

        menu3.add(Alter_Ward);
        menu3.add(Delete_Ward);
        menu3.add(insert_ward);
        menu3.add(manage_ward);

        menu4.add(Alter_Bed);
        menu4.add(Delete_Bed);
        menu4.add(insert_bed);
        menu4.add(manage_bed);

        menu5.add(Alter_Patient);
        menu5.add(Delete_Patient);
        menu5.add(insert_patient);
        menu5.add(manage_patient);

        menu6.add(Alter_Drug);
        menu6.add(Delete_Drug);
        menu6.add(insert_drug);
        menu6.add(manage_drug);

        // 将菜单加入菜单栏
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);
        menuBar.add(menu5);
        menuBar.add(menu6);

        frame.setJMenuBar(menuBar);
        frame.add(l1);
        frame.add(l2);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        // 添加事件监听器
        insert_department.addActionListener(listen);
        manage_department.addActionListener(listen);

        insert_doctor.addActionListener(listen);
        manage_doctor.addActionListener(listen);

        insert_ward.addActionListener(listen);
        manage_ward.addActionListener(listen);

        insert_bed.addActionListener(listen);
        manage_bed.addActionListener(listen);

        insert_patient.addActionListener(listen);
        manage_patient.addActionListener(listen);

        Alter_Bed.addActionListener(listen);
        Delete_Bed.addActionListener(listen);

        Alter_Department.addActionListener(listen);
        Delete_Department.addActionListener(listen);

        Alter_Doctor.addActionListener(listen);
        Delete_Doctor.addActionListener(listen);

        Alter_Patient.addActionListener(listen);
        Delete_Patient.addActionListener(listen);

        Alter_Ward.addActionListener(listen);
        Delete_Ward.addActionListener(listen);

        insert_drug.addActionListener(listen); // 为药品信息管理菜单项添加监听器
        manage_drug.addActionListener(listen);
        Alter_Drug.addActionListener(listen);
        Delete_Drug.addActionListener(listen);

        jToolBar = new JToolBar();
        jToolBar.setBounds(0, 550, 800, 50);
        jToolBar.setFloatable(false);
        frame.add(jToolBar);

        String[] names = {"工作证号", "姓名", "性别", "年龄", "出生日期", "职称", "电话", "所属科室"};
        DefaultTableModel dm = new DefaultTableModel(names, 0);
    }

    public static void main(String[] args) {
        new admin_frame();
    }

    ActionListener listen = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // 处理科室信息管理事件
            if (e.getSource() == insert_department) {
                new Insert_Department();
            }
            if (e.getSource() == manage_department) {
                new Manage_Department();
            }

            // 处理医生信息管理事件
            if (e.getSource() == insert_doctor) {
                new Insert_Doctor();
            }
            if (e.getSource() == manage_doctor) {
                new Manage_Doctor();
            }

            // 处理病房信息管理事件
            if (e.getSource() == insert_ward) {
                new Insert_Ward();
            }
            if (e.getSource() == manage_ward) {
                new Manage_Ward();
            }

            // 处理床位信息管理事件
            if (e.getSource() == insert_bed) {
                new Insert_Bed();
            }
            if (e.getSource() == manage_bed) {
                new Manage_Bed();
            }

            // 处理病人信息管理事件
            if (e.getSource() == insert_patient) {
                new Insert_Patient();
            }
            if (e.getSource() == manage_patient) {
                new Manage_Patient();
            }

            // 处理床位信息修改和删除事件
            if (e.getSource() == Alter_Bed) {
                String wno = JOptionPane.showInputDialog("请输入病房号：");
                String Bno = JOptionPane.showInputDialog("请输入病床号：");
                new Alter_Bed(wno, Bno);
            }
            if (e.getSource() == Delete_Bed) {
                new Delete_Bed();
            }

            // 处理科室信息修改和删除事件
            if (e.getSource() == Alter_Department) {
                String originalDeptname = JOptionPane.showInputDialog("输入修改的科室名称：");
                if (originalDeptname != null && !originalDeptname.trim().isEmpty()) {
                    new Alter_Department(originalDeptname);
                }
            }
            if (e.getSource() == Delete_Department) {
                new Delete_Department();
            }

            // 处理医生信息修改和删除事件
            if (e.getSource() == Alter_Doctor) {
                String doctorId = JOptionPane.showInputDialog("输入要修改的医生工作证号：");
                if (doctorId != null && !doctorId.trim().isEmpty()) {
                    new Alter_Doctor(doctorId);
                }
            }
            if (e.getSource() == Delete_Doctor) {
                new Delete_Doctor();
            }

            // 处理病房信息修改和删除事件
            if (e.getSource() == Alter_Ward) {
                String wardId = JOptionPane.showInputDialog("请输入要修改的病房号：");
                if (wardId != null && !wardId.trim().isEmpty()) {
                    new Alter_Ward(wardId);
                }
            }
            if (e.getSource() == Delete_Ward) {
                new Delete_Ward();
            }

            // 处理病人信息修改和删除事件
            if (e.getSource() == Alter_Patient) {
                String Pno = JOptionPane.showInputDialog("输入要修改的病人号：");
                if (Pno != null && !Pno.trim().isEmpty()) {
                    new Alter_Patient(Pno);
                }
            }
            if (e.getSource() == Delete_Patient) {
                new Delete_Patient();
            }

            // 处理药品信息管理事件
            if (e.getSource() == insert_drug) {
                new Insert_Drug();
            }
            if (e.getSource() == manage_drug) {
                new Manage_Drug(); // 假设存在Manage_Drug类用于查询药品信息
            }
            if (e.getSource() == Alter_Drug) {
                String drugId = JOptionPane.showInputDialog("输入要修改的药品编号：");
                if (drugId != null && !drugId.trim().isEmpty()) {
                    new Alter_Drug(drugId);
                }
            }
            if (e.getSource() == Delete_Drug) {
                new Delete_Drug(); // 假设存在Delete_Drug类用于删除药品信息
            }
        }
    };
}