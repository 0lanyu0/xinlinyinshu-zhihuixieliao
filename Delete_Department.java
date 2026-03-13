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

public class Delete_Department implements ActionListener {

	private JFrame frame;
	private JTextField text_Deptname;
	private JButton button1 = new JButton("删除");
	private JButton button2 = new JButton("取消");
	private JButton buttonQuery = new JButton("查询");
	private JLabel label_DeptnameShow;
	private JLabel label_DeptaddressShow;
	private JLabel label_DepttelShow;
	private JLabel label_DeptcategoryShow; // 新增用于显示科室类别的 JLabel

	public Delete_Department() {
		frame = new JFrame("删除科室信息");
		frame.setBounds(380, 100, 600, 600); // 与插入模块尺寸一致
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);

		// 科室名称标签和输入框（与插入模块布局一致）
		JLabel label_Deptname = new JLabel("科室名称");
		label_Deptname.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Deptname.setBounds(120, 120, 120, 30);
		frame.getContentPane().add(label_Deptname);

		text_Deptname = new JTextField();
		text_Deptname.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Deptname.setBounds(240, 120, 130, 30);
		frame.getContentPane().add(text_Deptname);

		// 查询按钮
		buttonQuery.setBounds(390, 120, 120, 30);
		frame.getContentPane().add(buttonQuery);
		buttonQuery.addActionListener(this);

		// 显示科室信息的标签
		label_DeptnameShow = new JLabel("科室名称: ");
		label_DeptnameShow.setFont(new Font("宋体", Font.PLAIN, 20));
		label_DeptnameShow.setBounds(120, 220, 300, 30);
		frame.getContentPane().add(label_DeptnameShow);

		label_DeptaddressShow = new JLabel("科室地址: ");
		label_DeptaddressShow.setFont(new Font("宋体", Font.PLAIN, 20));
		label_DeptaddressShow.setBounds(120, 270, 300, 30);
		frame.getContentPane().add(label_DeptaddressShow);

		label_DepttelShow = new JLabel("科室电话: ");
		label_DepttelShow.setFont(new Font("宋体", Font.PLAIN, 20));
		label_DepttelShow.setBounds(120, 320, 300, 30);
		frame.getContentPane().add(label_DepttelShow);

		// 新增显示科室类别的标签
		label_DeptcategoryShow = new JLabel("科室类别: ");
		label_DeptcategoryShow.setFont(new Font("宋体", Font.PLAIN, 20));
		label_DeptcategoryShow.setBounds(120, 370, 300, 30);
		frame.getContentPane().add(label_DeptcategoryShow);

		// 按钮布局（与插入模块一致）
		button1.setBounds(90, 470, 120, 40);
		frame.getContentPane().add(button1);
		button1.addActionListener(this);

		button2.setBounds(280, 470, 120, 40);
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
			String deptname = text_Deptname.getText().trim();

			try {
				// 检查输入是否为空
				if (deptname.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入科室名称！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				con = linkdatabase.getConnection();
				// 查询科室信息，包含科室类别
				String querySql = "SELECT Deptname, Deptaddress, Depttel, Deptcategory FROM Department WHERE Deptname = ?";
				ps = con.prepareStatement(querySql);
				ps.setString(1, deptname);
				rs = ps.executeQuery();

				if (rs.next()) {
					label_DeptnameShow.setText("科室名称: " + rs.getString("Deptname"));
					label_DeptaddressShow.setText("科室地址: " + rs.getString("Deptaddress"));
					label_DepttelShow.setText("科室电话: " + rs.getString("Depttel"));
					label_DeptcategoryShow.setText("科室类别: " + rs.getString("Deptcategory")); // 设置科室类别信息
				} else {
					JOptionPane.showMessageDialog(null, "该科室不存在！", "系统提示", JOptionPane.ERROR_MESSAGE);
					label_DeptnameShow.setText("科室名称: ");
					label_DeptaddressShow.setText("科室地址: ");
					label_DepttelShow.setText("科室电话: ");
					label_DeptcategoryShow.setText("科室类别: "); // 清空科室类别信息
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

			try {
				// 检查输入是否为空
				if (deptname.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入科室名称！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				con = linkdatabase.getConnection();
				// 先检查科室是否存在
				String checkSql = "SELECT Deptname FROM Department WHERE Deptname = ?";
				ps = con.prepareStatement(checkSql);
				ps.setString(1, deptname);
				rs = ps.executeQuery();

				if (!rs.next()) {
					JOptionPane.showMessageDialog(null, "该科室不存在，无法删除！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 确认删除
				int confirm = JOptionPane.showConfirmDialog(null, "确认删除该科室？", "系统提示", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					String deleteSql = "DELETE FROM Department WHERE Deptname = ?";
					ps = con.prepareStatement(deleteSql);
					ps.setString(1, deptname);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "删除成功！", "系统提示", JOptionPane.INFORMATION_MESSAGE);
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
}