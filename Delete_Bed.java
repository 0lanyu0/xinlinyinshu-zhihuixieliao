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
import javax.swing.JTextField;

import linkdatabase.linkdatabase;

public class Delete_Bed implements ActionListener {

    private JFrame frame;
    private JTextField text_Wno;
    private JTextField text_Bno;
    private JButton button1 = new JButton("删除");
    private JButton button2 = new JButton("取消");
    private JButton buttonQuery = new JButton("查询");

    // 用于显示床位信息的标签
    private JLabel label_WnoShow;
    private JLabel label_BnoShow;
    private JLabel label_BstateShow;

    public Delete_Bed() {
        frame = new JFrame("删除床位信息");
        frame.setBounds(380, 50, 600, 670);  // 按照病人删除模块的尺寸和比例修改
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        JLabel label_Wno = new JLabel("病 房 号");
        label_Wno.setFont(new Font("宋体", Font.PLAIN, 25));
        label_Wno.setBounds(120, 50, 120, 30);
        frame.getContentPane().add(label_Wno);

        text_Wno = new JTextField();
        text_Wno.setFont(new Font("宋体", Font.PLAIN, 20));
        text_Wno.setBounds(240, 50, 130, 30);
        text_Wno.setColumns(10);
        frame.getContentPane().add(text_Wno);

        JLabel label_Bno = new JLabel("病 床 号");
        label_Bno.setFont(new Font("宋体", Font.PLAIN, 25));
        label_Bno.setBounds(120, 100, 120, 30);
        frame.getContentPane().add(label_Bno);

        text_Bno = new JTextField();
        text_Bno.setFont(new Font("宋体", Font.PLAIN, 20));
        text_Bno.setBounds(240, 100, 130, 30);
        text_Bno.setColumns(10);
        frame.getContentPane().add(text_Bno);

        // 添加查询按钮
        buttonQuery.setBounds(390, 50, 120, 30);
        frame.getContentPane().add(buttonQuery);
        buttonQuery.addActionListener(this);

        // 用于显示床位信息的标签初始化及布局
        label_WnoShow = new JLabel("病 房 号: ");
        label_WnoShow.setFont(new Font("宋体", Font.PLAIN, 20));
        label_WnoShow.setBounds(120, 150, 300, 30);
        frame.getContentPane().add(label_WnoShow);

        label_BnoShow = new JLabel("病 床 号: ");
        label_BnoShow.setFont(new Font("宋体", Font.PLAIN, 20));
        label_BnoShow.setBounds(120, 200, 300, 30);
        frame.getContentPane().add(label_BnoShow);

        label_BstateShow = new JLabel("目前状态: ");
        label_BstateShow.setFont(new Font("宋体", Font.PLAIN, 20));
        label_BstateShow.setBounds(120, 250, 300, 30);
        frame.getContentPane().add(label_BstateShow);

        button1.setBounds(90, 570, 120, 40);
        frame.getContentPane().add(button1);
        button1.addActionListener(this);

        button2.setBounds(280, 570, 120, 40);
        frame.getContentPane().add(button2);
        button2.addActionListener(this);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonQuery) {
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String Wno = text_Wno.getText().trim();

            try {
                if ("".equals(Wno)) {
                    JOptionPane.showMessageDialog(null, "请输入病房号！", "系统提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                con = linkdatabase.getConnection();
                String querySql = "SELECT Wno, Bno, Bstate FROM Bed WHERE Wno = ?";
                ps = con.prepareStatement(querySql);
                ps.setString(1, Wno);
                rs = ps.executeQuery();

                if (rs.next()) {
                    label_WnoShow.setText("病 房 号: " + rs.getString("Wno"));
                    label_BnoShow.setText("病 床 号: " + rs.getString("Bno"));
                    label_BstateShow.setText("目前状态: " + rs.getString("Bstate"));
                    while (rs.next()) {
                        // 可以考虑将所有结果拼接显示，这里简单处理只显示第一条
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "该病房没有床位信息！", "系统提示", JOptionPane.ERROR_MESSAGE);
                    resetShowLabels();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "查询失败，请检查输入！", "系统提示", JOptionPane.ERROR_MESSAGE);
                resetShowLabels();
            } finally {
                linkdatabase.closeAll(rs, ps, con);
            }
        } else if (e.getSource() == button1) {
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String Wno = text_Wno.getText().trim();
            String Bno = text_Bno.getText().trim();

            try {
                if ("".equals(Wno) || "".equals(Bno)) {
                    JOptionPane.showMessageDialog(null, "请输入完整的病房号和病床号！", "系统提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                con = linkdatabase.getConnection();
                String checkSql = "SELECT Wno, Bno FROM Bed WHERE Wno = ? AND Bno = ?";
                ps = con.prepareStatement(checkSql);
                ps.setObject(1, Wno);
                ps.setObject(2, Bno);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "该床位不存在，无法删除！", "系统提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null, "确认删除该床位信息？", "系统提示", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String deleteSql = "DELETE FROM Bed WHERE Wno = ? AND Bno = ?";
                    ps = con.prepareStatement(deleteSql);
                    ps.setObject(1, Wno);
                    ps.setObject(2, Bno);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "删除成功！", "系统提示", JOptionPane.PLAIN_MESSAGE);
                    frame.dispose();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "删除失败，请检查输入！", "系统提示", JOptionPane.ERROR_MESSAGE);
            } finally {
                linkdatabase.closeAll(rs, ps, con);
            }
        } else if (e.getSource() == button2) {
            frame.dispose();
        }
    }

    // 重置显示标签的文本
    private void resetShowLabels() {
        label_WnoShow.setText("病 房 号: ");
        label_BnoShow.setText("病 床 号: ");
        label_BstateShow.setText("目前状态: ");
    }
}