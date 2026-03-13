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

public class Alter_Doctor implements ActionListener {

	private JFrame frame;
	private JTextField text_Dno;          // 工作证号（不可修改，显示用）
	private JTextField text_Dpassword;   // 密码
	private JTextField text_Dname;       // 姓名
	private JComboBox<String> comboBox_Dsex;  // 性别
	private JComboBox<String> comboBox_Dtitle; // 职称
	private JComboBox<String> comboBox_Deptname; // 所属科室
	private JComboBox<String> comboBox_DeptCategory; // 科室类别
	private JTextField text_Dage;        // 年龄
	private JTextField text_Dtel;        // 联系电话
	private JButton button1 = new JButton("修改");
	private JButton button2 = new JButton("取消");
	private String sql;
	private String Dno;                   // 待修改医生的工作证号

	public Alter_Doctor(String Dno) {
		this.Dno = Dno;
		frame = new JFrame("修改医生信息");
		frame.setBounds(380, 50, 600, 670);  // 与病人信息修改界面尺寸一致
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);

		// 工作证号（标签+文本框，不可编辑）
		JLabel label_Dno = new JLabel("工作证号");
		label_Dno.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Dno.setBounds(120, 50, 120, 30);
		frame.getContentPane().add(label_Dno);
		text_Dno = new JTextField(Dno);
		text_Dno.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Dno.setBounds(240, 50, 130, 30);
		text_Dno.setColumns(10);
		text_Dno.setEditable(false);  // 工作证号不可修改
		frame.getContentPane().add(text_Dno);

		// 密码
		JLabel label_Dpassword = new JLabel("密    码");
		label_Dpassword.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Dpassword.setBounds(120, 100, 120, 30);
		frame.getContentPane().add(label_Dpassword);
		text_Dpassword = new JTextField();
		text_Dpassword.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Dpassword.setBounds(240, 100, 130, 30);
		frame.getContentPane().add(text_Dpassword);

		// 姓名
		JLabel label_Dname = new JLabel("姓    名");
		label_Dname.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Dname.setBounds(120, 150, 120, 30);
		frame.getContentPane().add(label_Dname);
		text_Dname = new JTextField();
		text_Dname.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Dname.setBounds(240, 150, 130, 30);
		frame.getContentPane().add(text_Dname);

		// 性别
		JLabel label_Dsex = new JLabel("性    别");
		label_Dsex.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Dsex.setBounds(120, 200, 120, 30);
		frame.getContentPane().add(label_Dsex);
		comboBox_Dsex = new JComboBox<>();
		comboBox_Dsex.setFont(new Font("宋体", Font.PLAIN, 20));
		comboBox_Dsex.setModel(new DefaultComboBoxModel<>(new String[]{"男", "女"}));
		comboBox_Dsex.setBounds(240, 200, 130, 30);
		frame.getContentPane().add(comboBox_Dsex);

		// 职称
		JLabel label_Dtitle = new JLabel("职    称");
		label_Dtitle.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Dtitle.setBounds(120, 250, 120, 30);
		frame.getContentPane().add(label_Dtitle);
		comboBox_Dtitle = new JComboBox<>();
		comboBox_Dtitle.setFont(new Font("宋体", Font.PLAIN, 20));
		// 更新职称选项
		comboBox_Dtitle.setModel(new DefaultComboBoxModel<>(new String[]{
				"见习医师", "医士", "住院医师讲师", "副主任医师", "主任医师",
				"助教", "副教授", "教授", "护士", "护士长"
		}));
		comboBox_Dtitle.setBounds(240, 250, 130, 30);
		frame.getContentPane().add(comboBox_Dtitle);

		// 科室类别
		JLabel label_DeptCategory = new JLabel("科室类别");
		label_DeptCategory.setFont(new Font("宋体", Font.PLAIN, 25));
		label_DeptCategory.setBounds(120, 300, 120, 30);
		frame.getContentPane().add(label_DeptCategory);
		comboBox_DeptCategory = new JComboBox<>();
		comboBox_DeptCategory.setFont(new Font("宋体", Font.PLAIN, 20));
		comboBox_DeptCategory.setModel(new DefaultComboBoxModel<>(new String[]{"临床科室", "医技科室", "职能部门"}));
		comboBox_DeptCategory.setBounds(240, 300, 130, 30);
		comboBox_DeptCategory.addActionListener(this);
		frame.getContentPane().add(comboBox_DeptCategory);

		// 所属科室
		JLabel label_Deptname = new JLabel("所属科室");
		label_Deptname.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Deptname.setBounds(120, 350, 120, 30);
		frame.getContentPane().add(label_Deptname);
		comboBox_Deptname = new JComboBox<>();
		comboBox_Deptname.setFont(new Font("宋体", Font.PLAIN, 20));
		comboBox_Deptname.setBounds(240, 350, 130, 30);
		frame.getContentPane().add(comboBox_Deptname);

		// 年龄
		JLabel label_Dage = new JLabel("年    龄");
		label_Dage.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Dage.setBounds(120, 400, 120, 30);
		frame.getContentPane().add(label_Dage);
		text_Dage = new JTextField();
		text_Dage.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Dage.setBounds(240, 400, 130, 30);
		frame.getContentPane().add(text_Dage);

		// 联系电话
		JLabel label_Dtel = new JLabel("联系电话");
		label_Dtel.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Dtel.setBounds(120, 450, 120, 30);
		frame.getContentPane().add(label_Dtel);
		text_Dtel = new JTextField();
		text_Dtel.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Dtel.setBounds(240, 450, 130, 30);
		frame.getContentPane().add(text_Dtel);

		// 修改按钮
		button1.setBounds(90, 570, 120, 40);
		frame.getContentPane().add(button1);
		button1.addActionListener(this);

		// 取消按钮
		button2.setBounds(280, 570, 120, 40);
		frame.getContentPane().add(button2);
		button2.addActionListener(this);

		// 初始化时加载医生现有信息（需补充查询逻辑，假设从数据库获取）
		loadDoctorInfo();

		// 初始化所属科室的内容
		updateDeptComboBox((String) comboBox_DeptCategory.getSelectedItem());

		frame.setVisible(true);
	}

	// 加载医生现有信息（示例：需根据Dno从数据库查询）
	private void loadDoctorInfo() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = linkdatabase.getConnection();
			String sql = "SELECT * FROM Doctor WHERE Dno = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, Dno);
			rs = ps.executeQuery();
			if (rs.next()) {
				text_Dpassword.setText(rs.getString("Dpassword"));
				text_Dname.setText(rs.getString("Dname"));
				comboBox_Dsex.setSelectedItem(rs.getString("Dsex"));
				comboBox_Dtitle.setSelectedItem(rs.getString("Dtitle"));
				// 根据科室名称查询科室类别并设置
				String deptName = rs.getString("Deptname");
				String deptCategory = getDeptCategory(deptName);
				if (deptCategory != null) {
					comboBox_DeptCategory.setSelectedItem(deptCategory);
				}
				text_Dage.setText(rs.getString("Dage"));
				text_Dtel.setText(rs.getString("Dtel"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "获取医生信息失败", "系统提示", JOptionPane.ERROR_MESSAGE);
		} finally {
			linkdatabase.closeAll(rs, ps, con);
		}
	}

	// 根据科室名称查询科室类别
	private String getDeptCategory(String deptName) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = linkdatabase.getConnection();
			String sql = "SELECT Deptcategory FROM Department WHERE Deptname = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, deptName);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("Deptcategory");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			linkdatabase.closeAll(rs, ps, con);
		}
		return null;
	}

	// 更新所属科室的下拉框内容
	private void updateDeptComboBox(String category) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		try {
			con = linkdatabase.getConnection();
			String querySql = "SELECT Deptname FROM Department WHERE Deptcategory = ?";
			ps = con.prepareStatement(querySql);
			ps.setString(1, category);
			rs = ps.executeQuery();
			while (rs.next()) {
				String deptName = rs.getString("Deptname");
				model.addElement(deptName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "获取科室信息失败，请检查！", "系统提示", JOptionPane.ERROR_MESSAGE);
		} finally {
			linkdatabase.closeAll(rs, ps, con);
		}
		comboBox_Deptname.setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == comboBox_DeptCategory) {
			String category = (String) comboBox_DeptCategory.getSelectedItem();
			updateDeptComboBox(category);
		} else if (source == button1) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			String Dpassword = text_Dpassword.getText().trim();
			String Dname = text_Dname.getText().trim();
			String Dsex = (String) comboBox_Dsex.getSelectedItem();
			String Dtitle = (String) comboBox_Dtitle.getSelectedItem();
			String Dage = text_Dage.getText().trim();
			String Dtel = text_Dtel.getText().trim();
			String Deptname = (String) comboBox_Deptname.getSelectedItem();

			// 输入验证（必填字段检查）
			if (Dpassword.isEmpty() || Dname.isEmpty() || Dsex.isEmpty() || Dtitle.isEmpty() ||
					Dage.isEmpty() || Dtel.isEmpty() || Deptname.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入完整信息！", "系统提示", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				int a = JOptionPane.showConfirmDialog(null, "确认修改医生信息？", "系统提示", JOptionPane.YES_NO_OPTION);
				if (a == JOptionPane.YES_OPTION) {
					con = linkdatabase.getConnection();
					// 构建更新语句
					sql = "UPDATE Doctor SET " +
							"Dpassword=?, Dname=?, Dsex=?, Dtitle=?, Dage=?, " +
							"Dtel=?, Deptname=? WHERE Dno=?";
					ps = con.prepareStatement(sql);
					ps.setString(1, Dpassword);
					ps.setString(2, Dname);
					ps.setString(3, Dsex);
					ps.setString(4, Dtitle);
					ps.setString(5, Dage);
					ps.setString(6, Dtel);
					ps.setString(7, Deptname);
					ps.setString(8, Dno);  // 条件：工作证号

					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "修改成功！", "系统提示", JOptionPane.PLAIN_MESSAGE);
					frame.dispose();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "修改失败，请检查输入！", "系统提示", JOptionPane.ERROR_MESSAGE);
			} finally {
				linkdatabase.closeAll(rs, ps, con);
			}
		} else if (source == button2) {
			frame.dispose();
		}
	}
}