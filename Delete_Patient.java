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

public class Delete_Patient implements ActionListener {

	private JFrame frame;
	private JTextField text_Pno;
	private JButton button1 = new JButton("删除");
	private JButton button2 = new JButton("取消");
	private JButton buttonQuery = new JButton("查询");

	// 用于显示病人信息的标签
	private JLabel label_PnoShow;
	private JLabel label_PnameShow;
	private JLabel label_PsexShow;
	private JLabel label_PdiagnoseShow;
	private JLabel label_WnoShow;
	private JLabel label_BnoShow;
	private JLabel label_DnoShow;
	private JLabel label_PtelShow;
	private JLabel label_PindateShow;
	private JLabel label_PoutdateShow;

	public Delete_Patient() {
		frame = new JFrame("删除病人信息");
		frame.setBounds(380, 50, 600, 670);  // 与插入模块尺寸一致
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);

		JLabel label_Pno = new JLabel("病 历 号");
		label_Pno.setFont(new Font("宋体", Font.PLAIN, 25));
		label_Pno.setBounds(120, 50, 120, 30);
		frame.getContentPane().add(label_Pno);

		text_Pno = new JTextField();
		text_Pno.setFont(new Font("宋体", Font.PLAIN, 20));
		text_Pno.setBounds(240, 50, 130, 30);
		text_Pno.setColumns(10);
		frame.getContentPane().add(text_Pno);

		// 添加查询按钮
		buttonQuery.setBounds(390, 50, 120, 30);
		frame.getContentPane().add(buttonQuery);
		buttonQuery.addActionListener(this);

		// 用于显示病人信息的标签初始化及布局（调整了位置和大小）
		label_PnoShow = new JLabel("病 历 号: ");
		label_PnoShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_PnoShow.setBounds(120, 100, 200, 25);
		frame.getContentPane().add(label_PnoShow);

		label_PnameShow = new JLabel("姓    名: ");
		label_PnameShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_PnameShow.setBounds(120, 130, 200, 25);
		frame.getContentPane().add(label_PnameShow);

		label_PsexShow = new JLabel("性    别: ");
		label_PsexShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_PsexShow.setBounds(120, 160, 200, 25);
		frame.getContentPane().add(label_PsexShow);

		label_PdiagnoseShow = new JLabel("诊断结果: ");
		label_PdiagnoseShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_PdiagnoseShow.setBounds(120, 190, 200, 25);
		frame.getContentPane().add(label_PdiagnoseShow);

		label_WnoShow = new JLabel("病 房 号: ");
		label_WnoShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_WnoShow.setBounds(120, 220, 200, 25);
		frame.getContentPane().add(label_WnoShow);

		label_BnoShow = new JLabel("病 床 号: ");
		label_BnoShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_BnoShow.setBounds(120, 250, 200, 25);
		frame.getContentPane().add(label_BnoShow);

		label_DnoShow = new JLabel("主医证号: ");
		label_DnoShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_DnoShow.setBounds(120, 280, 200, 25);
		frame.getContentPane().add(label_DnoShow);

		label_PtelShow = new JLabel("联系电话: ");
		label_PtelShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_PtelShow.setBounds(120, 310, 200, 25);
		frame.getContentPane().add(label_PtelShow);

		label_PindateShow = new JLabel("入院日期: ");
		label_PindateShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_PindateShow.setBounds(120, 340, 200, 25);
		frame.getContentPane().add(label_PindateShow);

		label_PoutdateShow = new JLabel("出院日期: ");
		label_PoutdateShow.setFont(new Font("宋体", Font.PLAIN, 18));
		label_PoutdateShow.setBounds(120, 370, 200, 25);
		frame.getContentPane().add(label_PoutdateShow);

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
			String Pno = text_Pno.getText().trim();

			try {
				if ("".equals(Pno)) {
					JOptionPane.showMessageDialog(null, "请输入病历号！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				con = linkdatabase.getConnection();
				String querySql = "SELECT Pno, Pname, Psex, Pdiagnose, Wno, Bno, Dno, Ptel, Pindate, Poutdate " +
						"FROM Patient WHERE Pno = ?";
				ps = con.prepareStatement(querySql);
				ps.setString(1, Pno);
				rs = ps.executeQuery();

				if (rs.next()) {
					label_PnoShow.setText("病 历 号: " + rs.getString("Pno"));
					label_PnameShow.setText("姓    名: " + rs.getString("Pname"));
					label_PsexShow.setText("性    别: " + rs.getString("Psex"));
					label_PdiagnoseShow.setText("诊断结果: " + rs.getString("Pdiagnose"));
					label_WnoShow.setText("病 房 号: " + rs.getString("Wno"));
					label_BnoShow.setText("病 床 号: " + rs.getString("Bno"));
					label_DnoShow.setText("主医证号: " + rs.getString("Dno"));
					label_PtelShow.setText("联系电话: " + rs.getString("Ptel"));
					label_PindateShow.setText("入院日期: " + rs.getString("Pindate"));
					label_PoutdateShow.setText("出院日期: " + rs.getString("Poutdate"));
				} else {
					JOptionPane.showMessageDialog(null, "该病人不存在！", "系统提示", JOptionPane.ERROR_MESSAGE);
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
			String Pno = text_Pno.getText().trim();

			try {
				if ("".equals(Pno)) {
					JOptionPane.showMessageDialog(null, "请输入病历号！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				con = linkdatabase.getConnection();
				String checkSql = "SELECT Pno FROM Patient WHERE Pno = ?";
				ps = con.prepareStatement(checkSql);
				ps.setObject(1, Pno);
				rs = ps.executeQuery();

				if (!rs.next()) {
					JOptionPane.showMessageDialog(null, "该病人不存在，无法删除！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				int confirm = JOptionPane.showConfirmDialog(null, "确认删除该病人信息？", "系统提示", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					String deleteSql = "DELETE FROM Patient WHERE Pno = ?";
					ps = con.prepareStatement(deleteSql);
					ps.setObject(1, Pno);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "删除成功！", "系统提示", JOptionPane.PLAIN_MESSAGE);
					frame.dispose();

					// 获取该病人的病房号和病床号，将病床状态设为“空闲”
					String bedSql = "SELECT Wno, Bno FROM Patient WHERE Pno = ?";
					ps = con.prepareStatement(bedSql);
					ps.setObject(1, Pno);
					rs = ps.executeQuery();
					if (rs.next()) {
						String Wno = rs.getString("Wno");
						String Bno = rs.getString("Bno");
						String updateSql = "UPDATE Bed SET Bstate = ? WHERE Wno = ? AND Bno = ?";
						ps = con.prepareStatement(updateSql);
						ps.setObject(1, "空闲");
						ps.setObject(2, Wno);
						ps.setObject(3, Bno);
						ps.executeUpdate();
					}
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
		label_PnoShow.setText("病 历 号: ");
		label_PnameShow.setText("姓    名: ");
		label_PsexShow.setText("性    别: ");
		label_PdiagnoseShow.setText("诊断结果: ");
		label_WnoShow.setText("病 房 号: ");
		label_BnoShow.setText("病 床 号: ");
		label_DnoShow.setText("主医证号: ");
		label_PtelShow.setText("联系电话: ");
		label_PindateShow.setText("入院日期: ");
		label_PoutdateShow.setText("出院日期: ");
	}
}