package Hospitaladmin;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import linkdatabase.linkdatabase;

public class Alter_Drug implements ActionListener {
    private JFrame frame;
    private JTextField text_drug_id;
    private JTextField text_drug_name;
    private JTextField text_specification;
    private JTextField text_purchase_price;
    private JTextField text_selling_price;
    private JComboBox<String> comboBox_is_prescription;
    private JButton button_alter;
    private JButton button_cancel;
    private String drugId;

    // 新增药品类别下拉框
    private JComboBox<String> comboBox_drug_category;

    public Alter_Drug(String drugId) {
        this.drugId = drugId;
        try {
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("修改药品信息");
        frame.setBounds(380, 50, 600, 670); // 与医生信息修改模块尺寸一致
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        // 药品 ID
        JLabel label_drug_id = new JLabel("药品 ID");
        label_drug_id.setFont(new Font("宋体", Font.PLAIN, 25));
        label_drug_id.setBounds(120, 50, 120, 30);
        frame.getContentPane().add(label_drug_id);
        text_drug_id = new JTextField(drugId);
        text_drug_id.setFont(new Font("宋体", Font.PLAIN, 20));
        text_drug_id.setBounds(240, 50, 130, 30);
        text_drug_id.setColumns(10);
        frame.getContentPane().add(text_drug_id);

        // 药品名称
        JLabel label_drug_name = new JLabel("药品名称");
        label_drug_name.setFont(new Font("宋体", Font.PLAIN, 25));
        label_drug_name.setBounds(120, 100, 120, 30);
        frame.getContentPane().add(label_drug_name);
        text_drug_name = new JTextField();
        text_drug_name.setFont(new Font("宋体", Font.PLAIN, 20));
        text_drug_name.setBounds(240, 100, 130, 30);
        frame.getContentPane().add(text_drug_name);

        // 规格
        JLabel label_specification = new JLabel("规格");
        label_specification.setFont(new Font("宋体", Font.PLAIN, 25));
        label_specification.setBounds(120, 150, 120, 30);
        frame.getContentPane().add(label_specification);
        text_specification = new JTextField();
        text_specification.setFont(new Font("宋体", Font.PLAIN, 20));
        text_specification.setBounds(240, 150, 130, 30);
        frame.getContentPane().add(text_specification);

        // 进价
        JLabel label_purchase_price = new JLabel("进价");
        label_purchase_price.setFont(new Font("宋体", Font.PLAIN, 25));
        label_purchase_price.setBounds(120, 200, 120, 30);
        frame.getContentPane().add(label_purchase_price);
        text_purchase_price = new JTextField();
        text_purchase_price.setFont(new Font("宋体", Font.PLAIN, 20));
        text_purchase_price.setBounds(240, 200, 130, 30);
        frame.getContentPane().add(text_purchase_price);

        // 售价
        JLabel label_selling_price = new JLabel("售价");
        label_selling_price.setFont(new Font("宋体", Font.PLAIN, 25));
        label_selling_price.setBounds(120, 250, 120, 30);
        frame.getContentPane().add(label_selling_price);
        text_selling_price = new JTextField();
        text_selling_price.setFont(new Font("宋体", Font.PLAIN, 20));
        text_selling_price.setBounds(240, 250, 130, 30);
        frame.getContentPane().add(text_selling_price);

        // 是否为处方药
        JLabel label_is_prescription = new JLabel("是否为处方药");
        label_is_prescription.setFont(new Font("宋体", Font.PLAIN, 25));
        label_is_prescription.setBounds(120, 300, 120, 30);
        frame.getContentPane().add(label_is_prescription);
        comboBox_is_prescription = new JComboBox<>();
        comboBox_is_prescription.setFont(new Font("宋体", Font.PLAIN, 20));
        comboBox_is_prescription.setModel(new DefaultComboBoxModel<>(new String[]{"非处方药", "处方药"}));
        comboBox_is_prescription.setToolTipText("非处方药");
        comboBox_is_prescription.setBounds(240, 300, 130, 30);
        frame.getContentPane().add(comboBox_is_prescription);

        // 新增药品类别标签和下拉框
        JLabel label_drug_category = new JLabel("药品类别");
        label_drug_category.setFont(new Font("宋体", Font.PLAIN, 25));
        label_drug_category.setBounds(120, 350, 120, 30);
        frame.getContentPane().add(label_drug_category);
        comboBox_drug_category = new JComboBox<>();
        comboBox_drug_category.setFont(new Font("宋体", Font.PLAIN, 20));
        comboBox_drug_category.setModel(new DefaultComboBoxModel<>(new String[]{"中药", "西药", "生物制药"}));
        comboBox_drug_category.setBounds(240, 350, 130, 30);
        frame.getContentPane().add(comboBox_drug_category);

        // 修改按钮
        button_alter = new JButton("修改");
        button_alter.setBounds(90, 570, 120, 40);
        frame.getContentPane().add(button_alter);
        button_alter.addActionListener(this);

        // 取消按钮
        button_cancel = new JButton("取消");
        button_cancel.setBounds(280, 570, 120, 40);
        frame.getContentPane().add(button_cancel);
        button_cancel.addActionListener(this);

        // 加载原有药品信息
        loadDrugInfo();

        frame.setVisible(true);
    }

    private void loadDrugInfo() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = linkdatabase.getConnection();
            // 查询语句中添加药品类别字段
            String sql = "SELECT drug_id, drug_name, specification, purchase_price, selling_price, is_prescription, drug_category FROM drug_info WHERE drug_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, drugId);
            rs = ps.executeQuery();
            if (rs.next()) {
                text_drug_id.setText(rs.getString("drug_id"));
                text_drug_name.setText(rs.getString("drug_name"));
                text_specification.setText(rs.getString("specification"));
                text_purchase_price.setText(String.valueOf(rs.getDouble("purchase_price")));
                text_selling_price.setText(String.valueOf(rs.getDouble("selling_price")));
                comboBox_is_prescription.setSelectedIndex(rs.getInt("is_prescription"));
                // 设置药品类别下拉框的选中项
                String drugCategory = rs.getString("drug_category");
                if (drugCategory != null) {
                    comboBox_drug_category.setSelectedItem(drugCategory);
                }
            }
        } catch (SQLException | HeadlessException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "加载药品信息出错，请稍后重试！", "系统提示", JOptionPane.ERROR_MESSAGE);
        } finally {
            linkdatabase.closeAll(rs, ps, con);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button_alter) {
            String newDrugId = text_drug_id.getText().trim();
            String drugName = text_drug_name.getText().trim();
            String specification = text_specification.getText().trim();
            String purchasePriceStr = text_purchase_price.getText().trim();
            String sellingPriceStr = text_selling_price.getText().trim();
            String isPrescriptionStr = (String) comboBox_is_prescription.getSelectedItem();
            int isPrescription = isPrescriptionStr.equals("处方药") ? 1 : 0;
            String drugCategory = (String) comboBox_drug_category.getSelectedItem();

            if ("".equals(newDrugId) || "".equals(drugName) || "".equals(specification) || "".equals(purchasePriceStr) || "".equals(sellingPriceStr) || "".equals(drugCategory)) {
                JOptionPane.showMessageDialog(null, "请输入完整信息！", "系统提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double purchasePrice = Double.parseDouble(purchasePriceStr);
            double sellingPrice = Double.parseDouble(sellingPriceStr);

            int confirm = JOptionPane.showConfirmDialog(null, "确认修改药品信息？", "系统提示", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Connection con = null;
                PreparedStatement ps = null;
                try {
                    con = linkdatabase.getConnection();
                    // 更新语句中添加药品类别字段
                    String sql = "UPDATE drug_info SET drug_id = ?, drug_name = ?, specification = ?, purchase_price = ?, selling_price = ?, is_prescription = ?, drug_category = ? WHERE drug_id = ?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, newDrugId);
                    ps.setString(2, drugName);
                    ps.setString(3, specification);
                    ps.setDouble(4, purchasePrice);
                    ps.setDouble(5, sellingPrice);
                    ps.setInt(6, isPrescription);
                    ps.setString(7, drugCategory);
                    ps.setString(8, drugId);
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(null, "修改成功！", "系统提示", JOptionPane.PLAIN_MESSAGE);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败，请稍后重试！", "系统提示", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException | HeadlessException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "修改出错，请稍后重试！", "系统提示", JOptionPane.ERROR_MESSAGE);
                } finally {
                    linkdatabase.closeAll(null, ps, con);
                }
            }
        } else if (e.getSource() == button_cancel) {
            frame.dispose();
        }
    }
}