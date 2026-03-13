package Hospitaladmin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.JTextComponent;

import linkdatabase.linkdatabase;

public class Delete_Drug implements ActionListener {
    private JFrame frame;
    private JTextField text_drugId;
    private JButton button_query;
    private JButton button_delete;
    private JButton button_cancel;
    private JTextArea textArea_info;

    public Delete_Drug() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("删除药品信息");
        frame.setBounds(380, 100, 600, 600);
        frame.getContentPane().setLayout(null);

        JLabel label_drugId = new JLabel("药品编号");
        label_drugId.setFont(new Font("宋体", Font.PLAIN, 23));
        label_drugId.setBounds(120, 50, 100, 30);
        frame.getContentPane().add(label_drugId);

        text_drugId = new JTextField();
        text_drugId.setFont(new Font("宋体", Font.PLAIN, 20));
        text_drugId.setBounds(240, 50, 130, 30);
        frame.getContentPane().add(text_drugId);
        text_drugId.setColumns(10);

        button_query = new JButton("查询");
        button_query.setBounds(380, 50, 120, 30);
        frame.getContentPane().add(button_query);
        button_query.addActionListener(this);

        textArea_info = new JTextArea();
        textArea_info.setFont(new Font("宋体", Font.PLAIN, 20));
        textArea_info.setBounds(120, 100, 380, 300);
        textArea_info.setEditable(false);
        frame.getContentPane().add(textArea_info);

        button_delete = new JButton("删除");
        button_delete.setBounds(90, 470, 120, 40);
        frame.getContentPane().add(button_delete);
        button_delete.addActionListener(this);

        button_cancel = new JButton("取消");
        button_cancel.setBounds(280, 470, 120, 40);
        frame.getContentPane().add(button_cancel);
        button_cancel.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button_query) {
            String drugIdStr = text_drugId.getText().trim();
            if ("".equals(drugIdStr)) {
                JOptionPane.showMessageDialog(null, "请输入药品编号！", "系统提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                con = linkdatabase.getConnection();
                // 修改查询语句，添加 drug_category 字段
                String sql = "SELECT drug_name, specification, purchase_price, selling_price, is_prescription, drug_category FROM drug_info WHERE drug_id = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, drugIdStr);
                rs = ps.executeQuery();
                StringBuilder info = new StringBuilder();
                if (rs.next()) {
                    String drugName = rs.getString("drug_name");
                    String specification = rs.getString("specification");
                    double purchasePrice = rs.getDouble("purchase_price");
                    double sellingPrice = rs.getDouble("selling_price");
                    int isPrescription = rs.getInt("is_prescription");
                    String prescriptionStr = isPrescription == 1 ? "处方药" : "非处方药";
                    // 获取并添加药品类别信息
                    String drugCategory = rs.getString("drug_category");

                    info.append("药品名称: ").append(drugName).append("\n");
                    info.append("规格: ").append(specification).append("\n");
                    info.append("进价: ").append(purchasePrice).append("\n");
                    info.append("售价: ").append(sellingPrice).append("\n");
                    info.append("是否为处方药: ").append(prescriptionStr).append("\n");
                    info.append("药品类别: ").append(drugCategory).append("\n");
                } else {
                    info.append("未找到该药品信息！");
                }
                textArea_info.setText(info.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "查询出错，请稍后重试！", "系统提示", JOptionPane.ERROR_MESSAGE);
            } finally {
                linkdatabase.closeAll(rs, ps, con);
            }
        } else if (e.getSource() == button_delete) {
            String drugIdStr = text_drugId.getText().trim();
            if ("".equals(drugIdStr)) {
                JOptionPane.showMessageDialog(null, "请输入药品编号！", "系统提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "确认删除该药品信息？", "系统提示", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Connection con = null;
                PreparedStatement ps = null;
                try {
                    con = linkdatabase.getConnection();
                    String sql = "DELETE FROM drug_info WHERE drug_id = ?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, drugIdStr);
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(null, "删除成功！", "系统提示", JOptionPane.PLAIN_MESSAGE);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "删除失败，请稍后重试！", "系统提示", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "删除出错，请稍后重试！", "系统提示", JOptionPane.ERROR_MESSAGE);
                } finally {
                    linkdatabase.closeAll(null, ps, con);
                }
            }
        } else if (e.getSource() == button_cancel) {
            frame.dispose();
        }
    }
}