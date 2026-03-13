package Hospitaladmin;

import java.awt.Font;
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

public class Alter_Ward implements ActionListener {

	private JFrame frame;
	private JTextField text_Wno;
	private JTextField text_Dname;
	private JTextField text_Wcharge;
	private JButton button1 = new JButton("修改");
	private JButton button2 = new JButton("取消");
	private String Wno;

	public Alter_Ward(String Wno) {
		this.Wno = Wno;
		frame = new JFrame("修改病房信息");
		frame.setBounds(380, 100, 600, 600); // 与插入模块尺寸一致
		frame.getContentPane().setLayout(null);

		JLabel label_Wno = new JLabel("病 房 号");
		label_Wno.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Wno.setBounds(120, 120, 120, 30);
		frame.getContentPane().add(label_Wno);
		text_Wno = new JTextField();
		text_Wno.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Wno.setBounds(240, 120, 130, 30);
		text_Wno.setColumns(10);
		frame.getContentPane().add(text_Wno);

		JLabel label_Dname = new JLabel("所属科室");
		label_Dname.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Dname.setBounds(120, 220, 120, 30);
		frame.getContentPane().add(label_Dname);
		text_Dname = new JTextField();
		text_Dname.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Dname.setBounds(240, 220, 130, 30);
		text_Dname.setColumns(10);
		frame.getContentPane().add(text_Dname);

		JLabel label_Wcharge = new JLabel("收费标准");
		label_Wcharge.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Wcharge.setBounds(120, 320, 120, 30);
		frame.getContentPane().add(label_Wcharge);
		text_Wcharge = new JTextField();
		text_Wcharge.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Wcharge.setBounds(240, 320, 130, 30);
		text_Wcharge.setColumns(10);
		frame.getContentPane().add(text_Wcharge);

		button1.setBounds(90, 470, 120, 40);
		frame.getContentPane().add(button1);
		button1.addActionListener(this);

		button2.setBounds(280, 470, 120, 40);
		frame.getContentPane().add(button2);
		button2.addActionListener(this);

		// 填充原有信息
		fillOriginalInfo();

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void fillOriginalInfo() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = linkdatabase.getConnection();
			String sql = "SELECT Wno, Deptname, Wcharge FROM Ward WHERE Wno = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, Wno);
			rs = ps.executeQuery();
			if (rs.next()) {
				text_Wno.setText(rs.getString("Wno"));
				text_Dname.setText(rs.getString("Deptname"));
				text_Wcharge.setText(rs.getString("Wcharge"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "获取原有信息失败，请稍后重试！", "系统提示", JOptionPane.ERROR_MESSAGE);
		} finally {
			linkdatabase.closeAll(rs, ps, con);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == button1) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			String newWno = text_Wno.getText().trim();
			String newDeptname = text_Dname.getText().trim();
			String newWcharge = text_Wcharge.getText().trim();

			try {
				if ("".equals(newWno) || "".equals(newDeptname) || "".equals(newWcharge)) {
					JOptionPane.showMessageDialog(null, "请输入完整信息！", "系统提示", JOptionPane.ERROR_MESSAGE);
				} else {
					con = linkdatabase.getConnection();
					int a = JOptionPane.showConfirmDialog(null, "确认修改病房信息？", "系统提示", JOptionPane.YES_NO_OPTION);
					if (a == JOptionPane.YES_OPTION) {
						String sql = "UPDATE Ward SET Wno = ?, Deptname = ?, Wcharge = ? WHERE Wno = ?";
						ps = con.prepareStatement(sql);
						ps.setString(1, newWno);
						ps.setString(2, newDeptname);
						ps.setString(3, newWcharge);
						ps.setString(4, Wno);
						ps.executeUpdate();
						JOptionPane.showMessageDialog(null, "修改成功！", "系统提示", JOptionPane.PLAIN_MESSAGE);
						frame.dispose();
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "输入有误，请核实后重新输入！", "系统提示", JOptionPane.ERROR_MESSAGE);
			} finally {
				linkdatabase.closeAll(rs, ps, con);
			}
		}
		if (source == button2) {
			frame.dispose();
		}
	}
}