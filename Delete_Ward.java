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

public class Delete_Ward implements ActionListener {

	private JFrame frame;
	private JTextField text_Wno;
	private JButton button1 = new JButton("删除");
	private JButton button2 = new JButton("取消");
	private JButton buttonQuery = new JButton("查询");
	private JLabel label_WnoShow;
	private JLabel label_DeptnameShow;
	private JLabel label_WchargeShow;

	public Delete_Ward() {
		frame = new JFrame("删除病房信息");
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

		// 查询按钮
		buttonQuery.setBounds(390, 120, 120, 30);
		frame.getContentPane().add(buttonQuery);
		buttonQuery.addActionListener(this);

		// 显示病房信息的标签
		label_WnoShow = new JLabel("病房号: ");
		label_WnoShow.setFont(new Font("宋体", Font.PLAIN, 20));
		label_WnoShow.setBounds(120, 220, 300, 30);
		frame.getContentPane().add(label_WnoShow);

		label_DeptnameShow = new JLabel("所属科室: ");
		label_DeptnameShow.setFont(new Font("宋体", Font.PLAIN, 20));
		label_DeptnameShow.setBounds(120, 270, 300, 30);
		frame.getContentPane().add(label_DeptnameShow);

		label_WchargeShow = new JLabel("收费标准: ");
		label_WchargeShow.setFont(new Font("宋体", Font.PLAIN, 20));
		label_WchargeShow.setBounds(120, 320, 300, 30);
		frame.getContentPane().add(label_WchargeShow);

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
			String Wno = text_Wno.getText().trim();

			try {
				// 检查输入是否为空
				if (Wno.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入病房号！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				con = linkdatabase.getConnection();
				// 查询病房信息
				String querySql = "SELECT Wno, Deptname, Wcharge FROM Ward WHERE Wno = ?";
				ps = con.prepareStatement(querySql);
				ps.setString(1, Wno);
				rs = ps.executeQuery();

				if (rs.next()) {
					label_WnoShow.setText("病房号: " + rs.getString("Wno"));
					label_DeptnameShow.setText("所属科室: " + rs.getString("Deptname"));
					label_WchargeShow.setText("收费标准: " + rs.getString("Wcharge"));
				} else {
					JOptionPane.showMessageDialog(null, "该病房不存在！", "系统提示", JOptionPane.ERROR_MESSAGE);
					label_WnoShow.setText("病房号: ");
					label_DeptnameShow.setText("所属科室: ");
					label_WchargeShow.setText("收费标准: ");
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
			String Wno = text_Wno.getText().trim();

			try {
				// 检查输入是否为空
				if (Wno.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入病房号！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				con = linkdatabase.getConnection();
				// 先检查病房是否存在
				String checkSql = "SELECT Wno FROM Ward WHERE Wno = ?";
				ps = con.prepareStatement(checkSql);
				ps.setString(1, Wno);
				rs = ps.executeQuery();

				if (!rs.next()) {
					JOptionPane.showMessageDialog(null, "该病房不存在，无法删除！", "系统提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 确认删除
				int confirm = JOptionPane.showConfirmDialog(null, "确认删除该病房信息？", "系统提示", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					String deleteSql = "DELETE FROM Ward WHERE Wno = ?";
					ps = con.prepareStatement(deleteSql);
					ps.setString(1, Wno);
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