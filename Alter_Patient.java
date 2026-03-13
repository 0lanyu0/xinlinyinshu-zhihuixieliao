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

public class Alter_Patient implements ActionListener {

	private JFrame frame;
	private JTextField text_Pno;          // 病历号（不可修改，显示用）
	private JTextField text_Pname;       // 姓名
	private JComboBox comboBox_Psex;      // 性别
	private JTextField text_Pdiagnose;    // 诊断结果
	private JTextField text_Wno;          // 病房号
	private JTextField text_Bno;          // 病床号
	private JTextField text_Dno;          // 主医证号
	private JTextField text_Ptel;         // 联系电话
	private JTextField text_Pindate;      // 入院日期
	private JTextField text_Poutdate;     // 出院日期
	private JButton button1 = new JButton("修改");
	private JButton button2 = new JButton("取消");
	private String sql;
	private String Pno;                   // 待修改病人的病历号

	public Alter_Patient(String Pno) {
		this.Pno = Pno;
		frame = new JFrame("修改病人信息");
		frame.setBounds(380, 50, 600, 670);  // 与插入模块尺寸一致
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);

		// 病历号（标签+文本框，不可编辑）
		JLabel label_Pno = new JLabel("病 历 号");
		label_Pno.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Pno.setBounds(120, 50, 120, 30);
		frame.getContentPane().add(label_Pno);
		text_Pno = new JTextField(Pno);
		text_Pno.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Pno.setBounds(240, 50, 130, 30);
		text_Pno.setColumns(10);
		text_Pno.setEditable(false);  // 病历号不可修改
		frame.getContentPane().add(text_Pno);

		// 姓名
		JLabel label_Pname = new JLabel("姓    名");
		label_Pname.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Pname.setBounds(120, 100, 120, 30);
		frame.getContentPane().add(label_Pname);
		text_Pname = new JTextField();
		text_Pname.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Pname.setBounds(240, 100, 130, 30);
		frame.getContentPane().add(text_Pname);

		// 性别
		JLabel label_Psex = new JLabel("性    别");
		label_Psex.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Psex.setBounds(120, 150, 120, 30);
		frame.getContentPane().add(label_Psex);
		comboBox_Psex = new JComboBox();
		comboBox_Psex.setFont(new Font("宋体", Font.PLAIN, 20));
		comboBox_Psex.setModel(new DefaultComboBoxModel(new String[]{"男", "女"}));
		comboBox_Psex.setBounds(240, 150, 130, 30);
		frame.getContentPane().add(comboBox_Psex);

		// 诊断结果
		JLabel label_Pdiagnose = new JLabel("诊断结果");
		label_Pdiagnose.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Pdiagnose.setBounds(120, 200, 120, 30);
		frame.getContentPane().add(label_Pdiagnose);
		text_Pdiagnose = new JTextField();
		text_Pdiagnose.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Pdiagnose.setBounds(240, 200, 130, 30);
		frame.getContentPane().add(text_Pdiagnose);

		// 病房号
		JLabel label_Wno = new JLabel("病 房 号");
		label_Wno.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Wno.setBounds(120, 250, 120, 30);
		frame.getContentPane().add(label_Wno);
		text_Wno = new JTextField();
		text_Wno.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Wno.setBounds(240, 250, 130, 30);
		frame.getContentPane().add(text_Wno);

		// 病床号
		JLabel label_Bno = new JLabel("病 床 号");
		label_Bno.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Bno.setBounds(120, 300, 120, 30);
		frame.getContentPane().add(label_Bno);
		text_Bno = new JTextField();
		text_Bno.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Bno.setBounds(240, 300, 130, 30);
		frame.getContentPane().add(text_Bno);

		// 主医证号
		JLabel label_Dno = new JLabel("主医证号");
		label_Dno.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Dno.setBounds(120, 350, 120, 30);
		frame.getContentPane().add(label_Dno);
		text_Dno = new JTextField();
		text_Dno.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Dno.setBounds(240, 350, 130, 30);
		frame.getContentPane().add(text_Dno);

		// 联系电话
		JLabel label_Ptel = new JLabel("联系电话");
		label_Ptel.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Ptel.setBounds(120, 400, 120, 30);
		frame.getContentPane().add(label_Ptel);
		text_Ptel = new JTextField();
		text_Ptel.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Ptel.setBounds(240, 400, 130, 30);
		frame.getContentPane().add(text_Ptel);

		// 入院日期
		JLabel label_Pindate = new JLabel("入院日期");
		label_Pindate.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Pindate.setBounds(120, 450, 120, 30);
		frame.getContentPane().add(label_Pindate);
		text_Pindate = new JTextField();
		text_Pindate.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Pindate.setBounds(240, 450, 130, 30);
		frame.getContentPane().add(text_Pindate);

		// 出院日期
		JLabel label_Poutdate = new JLabel("出院日期");
		label_Poutdate.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Poutdate.setBounds(120, 500, 120, 30);
		frame.getContentPane().add(label_Poutdate);
		text_Poutdate = new JTextField();
		text_Poutdate.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Poutdate.setBounds(240, 500, 130, 30);
		frame.getContentPane().add(text_Poutdate);

		// 修改按钮
		button1.setBounds(90, 570, 120, 40);
		frame.getContentPane().add(button1);
		button1.addActionListener(this);

		// 取消按钮
		button2.setBounds(280, 570, 120, 40);
		frame.getContentPane().add(button2);
		button2.addActionListener(this);

		// 初始化时加载病人现有信息（需补充查询逻辑，假设从数据库获取）
		loadPatientInfo();

		frame.setVisible(true);
	}

	// 加载病人现有信息（示例：需根据Pno从数据库查询）
	private void loadPatientInfo() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = linkdatabase.getConnection();
			String sql = "SELECT * FROM Patient WHERE Pno = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, Pno);
			rs = ps.executeQuery();
			if (rs.next()) {
				text_Pname.setText(rs.getString("Pname"));
				comboBox_Psex.setSelectedItem(rs.getString("Psex"));
				text_Pdiagnose.setText(rs.getString("Pdiagnose"));
				text_Wno.setText(rs.getString("Wno"));
				text_Bno.setText(rs.getString("Bno"));
				text_Dno.setText(rs.getString("Dno"));
				text_Ptel.setText(rs.getString("Ptel"));
				text_Pindate.setText(rs.getString("Pindate"));
				text_Poutdate.setText(rs.getString("Poutdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "获取病人信息失败", "系统提示", JOptionPane.ERROR_MESSAGE);
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

			String Pname = text_Pname.getText().trim();
			String Psex = (String) comboBox_Psex.getSelectedItem();
			String Pdiagnose = text_Pdiagnose.getText().trim();
			String Wno = text_Wno.getText().trim();
			String Bno = text_Bno.getText().trim();
			String Dno = text_Dno.getText().trim();
			String Ptel = text_Ptel.getText().trim();
			String Pindate = text_Pindate.getText().trim();
			String Poutdate = text_Poutdate.getText().trim();

			// 输入验证（必填字段检查）
			if (Pname.isEmpty() || Psex.isEmpty() || Pdiagnose.isEmpty() || Wno.isEmpty() ||
					Bno.isEmpty() || Dno.isEmpty() || Ptel.isEmpty() || Pindate.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入完整信息！", "系统提示", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				int a = JOptionPane.showConfirmDialog(null, "确认修改病人信息？", "系统提示", JOptionPane.YES_NO_OPTION);
				if (a == JOptionPane.YES_OPTION) {
					con = linkdatabase.getConnection();
					// 构建更新语句
					sql = "UPDATE Patient SET " +
							"Pname=?, Psex=?, Pdiagnose=?, Wno=?, Bno=?, Dno=?, " +
							"Ptel=?, Pindate=?, Poutdate=? WHERE Pno=?";
					ps = con.prepareStatement(sql);
					ps.setString(1, Pname);
					ps.setString(2, Psex);
					ps.setString(3, Pdiagnose);
					ps.setString(4, Wno);
					ps.setString(5, Bno);
					ps.setString(6, Dno);
					ps.setString(7, Ptel);
					ps.setString(8, Pindate);
					ps.setString(9, Poutdate);
					ps.setString(10, Pno);  // 条件：病历号

					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "修改成功！", "系统提示", JOptionPane.PLAIN_MESSAGE);
					frame.dispose();

					// 如果病床号修改，更新Bed表状态（可选，根据业务需求）
					if (!Bno.equals(getOriginalBno())) {  // 假设需要记录原病床号
						updateBedState(Wno, getOriginalBno(), "空闲");  // 原病床释放
						updateBedState(Wno, Bno, "使用中");  // 新病床占用
					}
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

	// 辅助方法：更新病床状态（可选，根据实际表结构调整）
	private void updateBedState(String Wno, String Bno, String state) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = linkdatabase.getConnection();
			String sql = "UPDATE Bed SET Bstate = ? WHERE Wno = ? AND Bno = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, state);
			ps.setString(2, Wno);
			ps.setString(3, Bno);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			linkdatabase.closeAll(null, ps, con);
		}
	}

	// 辅助方法：获取原病床号（需在loadPatientInfo中记录原始值）
	private String originalBno;
	private String getOriginalBno() {
		return originalBno;
	}
}
