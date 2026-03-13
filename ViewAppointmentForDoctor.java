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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import geng.model.Login;
import linkdatabase.linkdatabase;

public class ViewAppointmentForDoctor implements ActionListener {

    private JFrame frame;
    private Login login;
    private JComboBox<String> queryComboBox;
    private JTextField queryTextField;
    private JButton queryButton;
    private JButton allButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    private static final String[] COLUMN_NAMES = {
            "预约号", "预约人姓名", "年龄", "性别", "症状描述", "预约科室", "联系电话", "预约时间"
    };

    public ViewAppointmentForDoctor(Login login) {
        this.login = login;
        frame = new JFrame("查看预约信息");
        frame.setBounds(330, 150, 800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        // 查询条件选择下拉框
        String[] queryOptions = {
                "预约号", "预约人姓名", "年龄", "性别", "症状描述", "预约科室", "联系电话", "预约时间"
        };
        queryComboBox = new JComboBox<>(queryOptions);
        queryComboBox.setBounds(20, 20, 120, 30);
        frame.add(queryComboBox);

        // 查询条件输入框，拉长输入框
        queryTextField = new JTextField();
        queryTextField.setBounds(150, 20, 450, 30);
        frame.add(queryTextField);

        // 查询按钮
        queryButton = new JButton("查询");
        queryButton.setBounds(610, 20, 80, 30);
        queryButton.addActionListener(this);
        frame.add(queryButton);

        // 查询全部按钮
        allButton = new JButton("查询全部");
        allButton.setBounds(700, 20, 100, 30);
        allButton.addActionListener(this);
        frame.add(allButton);

        // 结果表格，放大显示窗口
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(20, 70, 750, 500);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == queryButton) {
            performQuery();
        } else if (e.getSource() == allButton) {
            performAllQuery();
        }
    }

    private void performQuery() {
        String queryType = (String) queryComboBox.getSelectedItem();
        String queryValue = queryTextField.getText().trim();
        if (queryValue.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "请输入查询值", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = linkdatabase.getConnection();
            String sql = "SELECT * FROM appointment_records WHERE " + getColumnName(queryType) + " = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, queryValue);
            rs = pstmt.executeQuery();
            displayResults(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "查询出错: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            linkdatabase.closeAll(rs, pstmt, conn);
        }
    }

    private void performAllQuery() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = linkdatabase.getConnection();
            String sql = "SELECT * FROM appointment_records";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            displayResults(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "查询出错: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            linkdatabase.closeAll(rs, pstmt, conn);
        }
    }

    private void displayResults(ResultSet rs) throws SQLException {
        tableModel.setRowCount(0);
        while (rs.next()) {
            List<Object> row = new ArrayList<>();
            for (String columnName : COLUMN_NAMES) {
                row.add(rs.getString(getColumnName(columnName)));
            }
            tableModel.addRow(row.toArray());
        }
    }

    private String getColumnName(String displayName) {
        switch (displayName) {
            case "预约号":
                return "appointment_id";
            case "预约人姓名":
                return "patient_name";
            case "年龄":
                return "age";
            case "性别":
                return "gender";
            case "症状描述":
                return "symptom_description";
            case "预约科室":
                return "appointment_department";
            case "联系电话":
                return "contact_phone";
            case "预约时间":
                return "appointment_time";
            default:
                return null;
        }
    }
    }