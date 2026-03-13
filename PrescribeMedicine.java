package Doctorfunction;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import linkdatabase.linkdatabase;

public class PrescribeMedicine implements ActionListener {

    private JFrame frame;
    private JTextField appointmentIdField;
    private JButton queryButton;
    private JTextField patientNameField;
    private JTextField symptomDescriptionField;
    private JComboBox<String> drugCategoryComboBox;
    private JComboBox<String> drug1ComboBox;
    private JComboBox<String> drug2ComboBox;
    private JComboBox<String> drug3ComboBox;
    private JTextField totalCostField;
    private JButton calculateButton; // 添加查询按钮
    private JButton confirmButton;

    // 存储药品信息的列表
    private List<String> drugNames = new ArrayList<>();

    public PrescribeMedicine() {
        frame = new JFrame("病人开药");
        frame.setBounds(300, 100, 600, 700);
        frame.getContentPane().setLayout(null);

        // 病人预约号输入框
        JLabel appointmentIdLabel = new JLabel("病人预约号:");
        appointmentIdLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        appointmentIdLabel.setBounds(50, 50, 120, 30);
        frame.getContentPane().add(appointmentIdLabel);

        appointmentIdField = new JTextField();
        appointmentIdField.setFont(new Font("宋体", Font.PLAIN, 20));
        appointmentIdField.setBounds(180, 50, 150, 30);
        frame.getContentPane().add(appointmentIdField);

        // 查询按钮
        queryButton = new JButton("查询");
        queryButton.setFont(new Font("宋体", Font.PLAIN, 20));
        queryButton.setBounds(350, 50, 80, 30);
        queryButton.addActionListener(this);
        frame.getContentPane().add(queryButton);

        // 病人姓名展示框
        JLabel patientNameLabel = new JLabel("病人姓名:");
        patientNameLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        patientNameLabel.setBounds(50, 100, 120, 30);
        frame.getContentPane().add(patientNameLabel);

        patientNameField = new JTextField();
        patientNameField.setFont(new Font("宋体", Font.PLAIN, 20));
        patientNameField.setBounds(180, 100, 300, 30);
        patientNameField.setEditable(false);
        frame.getContentPane().add(patientNameField);

        // 病状描述展示框
        JLabel symptomDescriptionLabel = new JLabel("病状描述:");
        symptomDescriptionLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        symptomDescriptionLabel.setBounds(50, 150, 120, 30);
        frame.getContentPane().add(symptomDescriptionLabel);

        symptomDescriptionField = new JTextField();
        symptomDescriptionField.setFont(new Font("宋体", Font.PLAIN, 20));
        symptomDescriptionField.setBounds(180, 150, 300, 30);
        symptomDescriptionField.setEditable(false);
        frame.getContentPane().add(symptomDescriptionField);

        // 药品类别下拉框
        JLabel drugCategoryLabel = new JLabel("药品类别:");
        drugCategoryLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        drugCategoryLabel.setBounds(50, 220, 120, 30);
        frame.getContentPane().add(drugCategoryLabel);

        drugCategoryComboBox = new JComboBox<>();
        drugCategoryComboBox.setFont(new Font("宋体", Font.PLAIN, 20));
        drugCategoryComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"中药", "西药", "生物制品"}));
        drugCategoryComboBox.setBounds(180, 220, 150, 30);
        drugCategoryComboBox.addActionListener(this);
        frame.getContentPane().add(drugCategoryComboBox);

        // 开方药品1下拉框
        JLabel drug1Label = new JLabel("开方药品1:");
        drug1Label.setFont(new Font("宋体", Font.PLAIN, 20));
        drug1Label.setBounds(50, 280, 120, 30);
        frame.getContentPane().add(drug1Label);

        drug1ComboBox = new JComboBox<>();
        drug1ComboBox.setFont(new Font("宋体", Font.PLAIN, 20));
        drug1ComboBox.setBounds(180, 280, 150, 30);
        frame.getContentPane().add(drug1ComboBox);

        // 开方药品2下拉框
        JLabel drug2Label = new JLabel("开方药品2:");
        drug2Label.setFont(new Font("宋体", Font.PLAIN, 20));
        drug2Label.setBounds(50, 340, 120, 30);
        frame.getContentPane().add(drug2Label);

        drug2ComboBox = new JComboBox<>();
        drug2ComboBox.setFont(new Font("宋体", Font.PLAIN, 20));
        drug2ComboBox.setBounds(180, 340, 150, 30);
        frame.getContentPane().add(drug2ComboBox);

        // 开方药品3下拉框
        JLabel drug3Label = new JLabel("开方药品3:");
        drug3Label.setFont(new Font("宋体", Font.PLAIN, 20));
        drug3Label.setBounds(50, 400, 120, 30);
        frame.getContentPane().add(drug3Label);

        drug3ComboBox = new JComboBox<>();
        drug3ComboBox.setFont(new Font("宋体", Font.PLAIN, 20));
        drug3ComboBox.setBounds(180, 400, 150, 30);
        frame.getContentPane().add(drug3ComboBox);

        // 总费用输入框
        JLabel totalCostLabel = new JLabel("总费用:");
        totalCostLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        totalCostLabel.setBounds(50, 460, 120, 30);
        frame.getContentPane().add(totalCostLabel);

        totalCostField = new JTextField();
        totalCostField.setFont(new Font("宋体", Font.PLAIN, 20));
        totalCostField.setBounds(180, 460, 150, 30);
        totalCostField.setEditable(false);
        frame.getContentPane().add(totalCostField);

        // 添加查询按钮
        calculateButton = new JButton("查询");
        calculateButton.setFont(new Font("宋体", Font.PLAIN, 20));
        calculateButton.setBounds(350, 460, 80, 30);
        calculateButton.addActionListener(this);
        frame.getContentPane().add(calculateButton);

        // 确定按钮
        confirmButton = new JButton("确定");
        confirmButton.setFont(new Font("宋体", Font.PLAIN, 20));
        confirmButton.setBounds(250, 550, 80, 30);
        confirmButton.addActionListener(this);
        frame.getContentPane().add(confirmButton);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == queryButton) {
            String appointmentId = appointmentIdField.getText().trim();
            if ("".equals(appointmentId)) {
                JOptionPane.showMessageDialog(null, "请输入病人预约号！", "系统提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // 根据预约号查询病人信息
            queryPatientInfo(appointmentId);
        } else if (e.getSource() == drugCategoryComboBox) {
            // 根据选择的药品类别更新下拉框内容
            updateDrugComboBoxes((String) drugCategoryComboBox.getSelectedItem());
        } else if (e.getSource() == calculateButton) {
            // 计算并显示总费用
            double totalCost = getTotalCost();
            totalCostField.setText(String.format("%.2f", totalCost));
        } else if (e.getSource() == confirmButton) {
            int confirm = JOptionPane.showConfirmDialog(null, "是否提交？", "系统提示", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String appointmentId = appointmentIdField.getText().trim();
                String drug1 = (String) drug1ComboBox.getSelectedItem();
                String drug2 = (String) drug2ComboBox.getSelectedItem();
                String drug3 = (String) drug3ComboBox.getSelectedItem();
                double totalCost = getTotalCost();
                // 插入数据到数据库
                insertPrescription(appointmentId, drug1, drug2, drug3, totalCost);
                JOptionPane.showMessageDialog(null, "提交成功！", "系统提示", JOptionPane.PLAIN_MESSAGE);
                frame.dispose();
            }
        }
    }

    private void queryPatientInfo(String appointmentId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = linkdatabase.getConnection();
            String sql = "SELECT patient_name, symptom_description FROM appointment_records WHERE appointment_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, appointmentId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                patientNameField.setText(rs.getString("patient_name"));
                symptomDescriptionField.setText(rs.getString("symptom_description"));
            } else {
                JOptionPane.showMessageDialog(frame, "未找到该预约号对应的病人信息", "提示", JOptionPane.WARNING_MESSAGE);
                patientNameField.setText("");
                symptomDescriptionField.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "查询出错: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            linkdatabase.closeAll(rs, pstmt, conn);
        }
    }

    private void updateDrugComboBoxes(String drugCategory) {
        drugNames.clear();
        drugNames.add("无");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = linkdatabase.getConnection();
            String sql = "SELECT drug_name FROM drug_info WHERE drug_category = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, drugCategory);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String drugName = rs.getString("drug_name");
                drugNames.add(drugName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "查询药品出错: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            linkdatabase.closeAll(rs, pstmt, conn);
        }

        DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(drugNames.toArray(new String[0]));
        drug1ComboBox.setModel(model1);
        DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(drugNames.toArray(new String[0]));
        drug2ComboBox.setModel(model2);
        DefaultComboBoxModel<String> model3 = new DefaultComboBoxModel<>(drugNames.toArray(new String[0]));
        drug3ComboBox.setModel(model3);

        drug1ComboBox.setSelectedIndex(0);
        drug2ComboBox.setSelectedIndex(0);
        drug3ComboBox.setSelectedIndex(0);
    }

    private double getTotalCost() {
        double cost1 = 0;
        double cost2 = 0;
        double cost3 = 0;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String drug1 = (String) drug1ComboBox.getSelectedItem();
        String drug2 = (String) drug2ComboBox.getSelectedItem();
        String drug3 = (String) drug3ComboBox.getSelectedItem();

        try {
            conn = linkdatabase.getConnection();

            if (drug1 != null &&!"无".equals(drug1)) {
                String sql = "SELECT selling_price FROM drug_info WHERE drug_name = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, drug1);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    cost1 = rs.getDouble("selling_price");
                }
            }

            if (drug2 != null &&!"无".equals(drug2)) {
                String sql = "SELECT selling_price FROM drug_info WHERE drug_name = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, drug2);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    cost2 = rs.getDouble("selling_price");
                }
            }

            if (drug3 != null &&!"无".equals(drug3)) {
                String sql = "SELECT selling_price FROM drug_info WHERE drug_name = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, drug3);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    cost3 = rs.getDouble("selling_price");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "查询药品价格出错: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            linkdatabase.closeAll(rs, pstmt, conn);
        }

        return cost1 + cost2 + cost3;
    }

    private void insertPrescription(String appointmentId, String drug1, String drug2, String drug3, double totalCost) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = linkdatabase.getConnection();
            String sql = "INSERT INTO patient_prescription (appointment_id, prescribed_drug_1, prescribed_drug_2, prescribed_drug_3, total_cost) VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, appointmentId);
            ps.setString(2, drug1);
            ps.setString(3, drug2);
            ps.setString(4, drug3);
            ps.setDouble(5, totalCost);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "插入数据出错，请稍后重试！", "系统提示", JOptionPane.ERROR_MESSAGE);
        } finally {
            linkdatabase.closeAll(null, ps, con);
        }
    }
    }
