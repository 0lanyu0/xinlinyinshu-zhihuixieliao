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

public class Delete_Doctor implements ActionListener {

	private JFrame frame;
	private JTextField text_No;
	private JButton button1 = new JButton("删除");
	private JButton button2 = new JButton("取消");
	private JButton buttonQuery = new JButton("查询");

	// 用于显示医生信息的标签
	private JLabel label_NoShow;
	private JLabel label_PasswordShow;
	private JLabel label_NameShow;
	private JLabel label_SexShow;
	private JLabel label_TitleShow;
	private JLabel label_AgeShow;
	private JLabel label_TelShow;
	private JLabel label_DeptShow;
	// 新增显示科室类别的标签
	private JLabel label_DeptCategoryShow;

	public Delete_Doctor() {
		frame = new JFrame("删除医生信息");
		frame.setBounds(380, 100, 600, 600); // 与插入模块尺寸一致
		frame.getContentPane().setLayout(null);

		JLabel label_No = new JLabel("工作证号：");
		label_No.setFont(new Font("宋体", Font.PLAIN, 20));
		label_No.setBounds(120, 50, 100, 30);
		frame.getContentPane().add(label_No);

		text_No = new JTextField();
		text_No.setFont(new Font("宋体", Font.PLAIN, 20));
		text_No.setBounds(240, 50, 130, 30);
		frame.getContentPane().add(text_No);
		text_No.setColumns(10);

		// 添加查询按钮
		buttonQuery.setBounds(390, 50, 120, 30);
		frame.getContentPane().add(buttonQuery);
		buttonQuery.addActionListener(this);

		// 用于显示医生信息的标签初始化及布局（调整了位置和大小）
		label_NoShow = new JLabel("工作证号: ");
		label_NoShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_NoShow.setBounds(120, 100, 200, 25);
		frame.getContentPane().add(label_NoShow);

		label_PasswordShow = new JLabel("密    码: ");
		label_PasswordShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_PasswordShow.setBounds(120, 130, 200, 25);
		frame.getContentPane().add(label_PasswordShow);

		label_NameShow = new JLabel("姓    名: ");
		label_NameShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_NameShow.setBounds(120, 160, 200, 25);
		frame.getContentPane().add(label_NameShow);

		label_SexShow = new JLabel("性    别: ");
		label_SexShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_SexShow.setBounds(120, 190, 200, 25);
		frame.getContentPane().add(label_SexShow);

		label_TitleShow = new JLabel("职    称: ");
		label_TitleShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_TitleShow.setBounds(120, 220, 200, 25);
		frame.getContentPane().add(label_TitleShow);

		label_AgeShow = new JLabel("年    龄: ");
		label_AgeShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_AgeShow.setBounds(120, 250, 200, 25);
		frame.getContentPane().add(label_AgeShow);

		label_TelShow = new JLabel("联系电话: ");
		label_TelShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_TelShow.setBounds(120, 280, 200, 25);
		frame.getContentPane().add(label_TelShow);

		label_DeptShow = new JLabel("所属科室: ");
		label_DeptShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_DeptShow.setBounds(120, 310, 200, 25);
		frame.getContentPane().add(label_DeptShow);

		// 新增显示科室类别的标签及布局
		label_DeptCategoryShow = new JLabel("科室类别: ");
		label_DeptCategoryShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_DeptCategoryShow.setBounds(120, 340, 200, 25);
		frame.getContentPane().add(label_DeptCategoryShow);

		button1.setBounds(90, 470, 120, 40);
		frame.getContentPane().add(button1);
		button1.addActionListener(this);

		button2.setBounds(280, 470, 120, 40);
		frame.getContentPane().add(button2);
		button2.addActionListener(this);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonQuery) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String no = text_No.getText().trim();

			try {
				if ("".equals(no)) {
					JOptionPane.showMessageDialog(null, "请输入工作证号！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				con = linkdatabase.getConnection();
				// 修改查询语句，增加科室类别字段
				String querySql = "SELECT Dno, Dpassword, Dname, Dsex, Dtitle, Dage, Dtel, Deptname, " +
						"(SELECT Deptcategory FROM Department WHERE Department.Deptname = Doctor.Deptname) AS Deptcategory " +
						"FROM Doctor WHERE Dno = ?";
				ps = con.prepareStatement(querySql);
				ps.setString(1, no);
				rs = ps.executeQuery();

				if (rs.next()) {
					label_NoShow.setText("工作证号: " + rs.getString("Dno"));
					label_PasswordShow.setText("密    码: " + rs.getString("Dpassword"));
					label_NameShow.setText("姓    名: " + rs.getString("Dname"));
					label_SexShow.setText("性    别: " + rs.getString("Dsex"));
					label_TitleShow.setText("职    称: " + rs.getString("Dtitle"));
					label_AgeShow.setText("年    龄: " + rs.getString("Dage"));
					label_TelShow.setText("联系电话: " + rs.getString("Dtel"));
					label_DeptShow.setText("所属科室: " + rs.getString("Deptname"));
					// 显示科室类别信息
					label_DeptCategoryShow.setText("科室类别: " + rs.getString("Deptcategory"));
				} else {
					JOptionPane.showMessageDialog(null, "该医生不存在！", "系统提示", JOptionPane.ERROR_MESSAGE);
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
			String no = text_No.getText().trim();

			try {
				if ("".equals(no)) {
					JOptionPane.showMessageDialog(null, "请输入工作证号", "系统提示", JOptionPane.ERROR_MESSAGE);
				} else {
					con = linkdatabase.getConnection();
					String checkSql = "SELECT Dno FROM Doctor WHERE Dno = ?";
					ps = con.prepareStatement(checkSql);
					ps.setObject(1, no);
					rs = ps.executeQuery();
					if (rs.next()) {
						int a = JOptionPane.showConfirmDialog(null, "确认删除该医生信息？", "系统提示", JOptionPane.YES_NO_OPTION);
						if (a == JOptionPane.YES_OPTION) {
							String deleteSql = "DELETE FROM Doctor WHERE Dno = ?";
							ps = con.prepareStatement(deleteSql);
							ps.setObject(1, no);
							ps.executeUpdate();
							JOptionPane.showMessageDialog(null, "删除成功！", "系统提示", JOptionPane.PLAIN_MESSAGE);
							frame.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "该医生不存在，无法删除！", "系统提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
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
		label_NoShow.setText("工作证号: ");
		label_PasswordShow.setText("密    码: ");
		label_NameShow.setText("姓    名: ");
		label_SexShow.setText("性    别: ");
		label_TitleShow.setText("职    称: ");
		label_AgeShow.setText("年    龄: ");
		label_TelShow.setText("联系电话: ");
		label_DeptShow.setText("所属科室: ");
		label_DeptCategoryShow.setText("科室类别: ");
	}
}