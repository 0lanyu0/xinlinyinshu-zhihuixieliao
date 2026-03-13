package Hospitaladmin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import linkdatabase.linkdatabase;

public class Alter_Bed implements ActionListener {

    private JFrame frame;
    private JTextField textField;
    private JComboBox comboBox;
    private JButton button1 = new JButton("修改");
    private JButton button2 = new JButton("取消");
    private String Wno, Bno;

    public Alter_Bed(String Wno, String Bno) {
        this.Wno = Wno;
        this.Bno = Bno;

        frame = new JFrame("修改病床信息");
        frame.setBounds(380, 100, 550, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel label1 = new JLabel("请选择需要修改的项");
        label1.setFont(new Font("宋体", Font.PLAIN, 25));
        label1.setBounds(50, 60, 250, 40);
        frame.getContentPane().add(label1);

        comboBox = new JComboBox();
        comboBox.setFont(new Font("宋体", Font.PLAIN, 25));
        comboBox.setModel(new DefaultComboBoxModel(new String[]{ "目前状态"}));
        comboBox.setBounds(300, 60, 160, 40);
        frame.getContentPane().add(comboBox);

        JLabel label2 = new JLabel("请输入修改后的内容");
        label2.setFont(new Font("宋体", Font.PLAIN, 25));
        label2.setBounds(50, 130, 250, 40);
        frame.getContentPane().add(label2);

        textField = new JTextField();
        textField.setFont(new Font("宋体", Font.PLAIN, 25));
        textField.setBounds(300, 130, 160, 40);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        button1.setBounds(100, 220, 110, 40);
        frame.getContentPane().add(button1);
        button1.addActionListener(this);

        button2.setBounds(330, 220, 110, 40);
        frame.getContentPane().add(button2);
        button2.addActionListener(this);

        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == button1) {
            Connection con = null;
            PreparedStatement ps = null;

            String select = (String) comboBox.getSelectedItem();
            String alter = textField.getText().trim();

            try {
                con = linkdatabase.getConnection();
                int a = JOptionPane.showConfirmDialog(null, "确认修改病床信息？", "系统提示", JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    if (select.equals("目前状态")) {
                        String sql = "UPDATE Bed SET Bstate = ? WHERE Wno = ? AND Bno = ?";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, alter);
                        ps.setString(2, Wno);
                        ps.setString(3, Bno);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "修改成功", "系统提示", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    }
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "输入有误，请核实后重新输入!", "系统提示", JOptionPane.ERROR_MESSAGE);
            } finally {
                linkdatabase.closeAll(null, ps, con);
            }
        }
        if (source == button2) {
            frame.dispose();
        }
    }
}
