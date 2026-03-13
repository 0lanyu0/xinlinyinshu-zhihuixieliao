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

public class Alter_Department implements ActionListener {

	private JFrame frame;
	private JTextField text_Deptname; // 科室名称输入框
	private JButton buttonQuery = new JButton("查询"); // 查询按钮
	private JTextField text_Deptaddress; // 科室地址输入框
	private JTextField text_Depttel; // 科室电话输入框
	private JTextField text_Deptcategory; // 新增的科室类别输入框
	private JButton button1 = new JButton("修改");
	private JButton button2 = new JButton("取消");
	private String originalDeptname; // 原始科室名称（用于条件查询）

	public Alter_Department(String originalDeptname) {
		this.originalDeptname = originalDeptname;

		frame = new JFrame("修改科室信息");
		frame.setBounds(380, 100, 600, 600); // 调整尺寸与插入模块一致
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// 科室名称标签和输入框
		JLabel label_Deptname = new JLabel("科室名称");
		label_Deptname.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Deptname.setBounds(120, 50, 120, 30);
		frame.getContentPane().add(label_Deptname);
		text_Deptname = new JTextField(originalDeptname);
		text_Deptname.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Deptname.setBounds(240, 50, 130, 30);
		text_Deptname.setColumns(10);
		frame.getContentPane().add(text_Deptname);

		// 查询按钮
		buttonQuery.setBounds(390, 50, 120, 30);
		frame.getContentPane().add(buttonQuery);
		buttonQuery.addActionListener(this);

		// 科室地址标签和输入框
		JLabel label_Deptaddress = new JLabel("科室地址");
		label_Deptaddress.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Deptaddress.setBounds(120, 150, 120, 30);
		frame.getContentPane().add(label_Deptaddress);
		text_Deptaddress = new JTextField();
		text_Deptaddress.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Deptaddress.setBounds(240, 150, 130, 30);
		text_Deptaddress.setColumns(10);
		frame.getContentPane().add(text_Deptaddress);

		// 科室电话标签和输入框
		JLabel label_Depttel = new JLabel("科室电话");
		label_Depttel.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Depttel.setBounds(120, 250, 120, 30);
		frame.getContentPane().add(label_Depttel);
		text_Depttel = new JTextField();
		text_Depttel.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Depttel.setBounds(240, 250, 130, 30);
		text_Depttel.setColumns(10);
		frame.getContentPane().add(text_Depttel);

		// 新增科室类别标签和输入框
		JLabel label_Deptcategory = new JLabel("科室类别");
		label_Deptcategory.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Deptcategory.setBounds(120, 350, 120, 30);
		frame.getContentPane().add(label_Deptcategory);
		text_Deptcategory = new JTextField();
		text_Deptcategory.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Deptcategory.setBounds(240, 350, 130, 30);
		text_Deptcategory.setColumns(10);
		frame.getContentPane().add(text_Deptcategory);

		// 修改按钮
		button1.setBounds(90, 470, 120, 40);
		frame.getContentPane().add(button1);
		button1.addActionListener(this);

		// 取消按钮
		button2.setBounds(280, 470, 120, 40);
		frame.getContentPane().add(button2);
		button2.addActionListener(this);

		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonQuery) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String deptname = text_Deptname.getText().trim();

			try {
				if ("".equals(deptname)) {
					JOptionPane.showMessageDialog(null, "请输入科室名称！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				con = linkdatabase.getConnection();
				// 查询语句包含科室类别
				String querySql = "SELECT Deptaddress, Depttel, Deptcategory FROM Department WHERE Deptname = ?";
				ps = con.prepareStatement(querySql);
				ps.setString(1, deptname);
				rs = ps.executeQuery();

				if (rs.next()) {
					text_Deptaddress.setText(rs.getString("Deptaddress"));
					text_Depttel.setText(rs.getString("Depttel"));
					text_Deptcategory.setText(rs.getString("Deptcategory"));
				} else {
					JOptionPane.showMessageDialog(null, "未找到该科室信息！", "系统提示", JOptionPane.ERROR_MESSAGE);
					text_Deptaddress.setText("");
					text_Depttel.setText("");
					text_Deptcategory.setText("");
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "查询失败，请检查输入！", "系统提示", JOptionPane.ERROR_MESSAGE);
			} finally {
				linkdatabase.closeAll(rs, ps, con);
			}
		} else if (e.getSource() == button1) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String deptname = text_Deptname.getText().trim();
			String deptaddress = text_Deptaddress.getText().trim();
			String depttel = text_Depttel.getText().trim();
			String deptcategory = text_Deptcategory.getText().trim();

			try {
				if ("".equals(deptname) || "".equals(deptaddress) || "".equals(depttel) || "".equals(deptcategory)) {
					JOptionPane.showMessageDialog(null, "请输入完整信息！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 检查新名称是否已存在（除了当前修改的科室）
				if (!deptname.equals(originalDeptname)) {
					String checkSql = "SELECT Deptname FROM Department WHERE Deptname = ?";
					ps = con.prepareStatement(checkSql);
					ps.setString(1, deptname);
					rs = ps.executeQuery();
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "该科室名称已存在，无法修改！", "系统提示", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				int confirm = JOptionPane.showConfirmDialog(null, "确认修改科室信息？", "系统提示", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					con = linkdatabase.getConnection();
					// 更新语句包含科室类别
					String updateSql = "UPDATE Department SET Deptname = ?, Deptaddress = ?, Depttel = ?, Deptcategory = ? WHERE Deptname = ?";
					ps = con.prepareStatement(updateSql);
					ps.setString(1, deptname);
					ps.setString(2, deptaddress);
					ps.setString(3, depttel);
					ps.setString(4, deptcategory);
					ps.setString(5, originalDeptname);

					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "修改成功！", "系统提示", JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "输入有误，请核实后重新输入！", "系统提示", JOptionPane.ERROR_MESSAGE);
			} finally {
				linkdatabase.closeAll(rs, ps, con);
			}
		} else if (e.getSource() == button2) {
			frame.dispose();
		}
	}
}