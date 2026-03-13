package Doctorfunction;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Hospitaladmin.Insert_Patient;
import geng.model.Login;

public class Main implements ActionListener {

	Login login;
	JFrame frame = new JFrame();
	JButton b1 = new JButton("查看预约信息");
	JButton b2 = new JButton("登记病人信息");
	JButton b3 = new JButton("查询病人信息");
	JButton b4 = new JButton("查询床位信息");
	JButton b5 = new JButton("出院结算");
	// 新增“病人开药”按钮
	JButton b6 = new JButton("病人开药");

	public Main(Login login) {
		this.login = login;
		// TODO Auto-generated constructor stub
		frame.setBounds(330, 150, 600, 500);
		b1.setBounds(200, 60, 150, 40);
		b1.setFont(new Font("宋体", 0, 16));
		b1.addActionListener(this);

		b2.setBounds(200, 120, 150, 40);
		b2.setFont(new Font("宋体", 0, 16));
		b2.addActionListener(this);

		b3.setBounds(200, 180, 150, 40);
		b3.setFont(new Font("宋体", 0, 16));
		b3.addActionListener(this);

		b4.setBounds(200, 240, 150, 40);
		b4.setFont(new Font("宋体", 0, 16));
		b4.addActionListener(this);

		b5.setBounds(200, 300, 150, 40);
		b5.setFont(new Font("宋体", 0, 16));
		b5.addActionListener(this);

		// 设置“病人开药”按钮的属性
		b6.setBounds(200, 360, 150, 40);
		b6.setFont(new Font("宋体", 0, 16));
		b6.addActionListener(this);

		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.add(b4);
		frame.add(b5);
		// 将“病人开药”按钮添加到主界面
		frame.add(b6);

		frame.setLayout(null);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == b1) {
			new ViewAppointmentForDoctor(login);
		}

		if (e.getSource() == b2) {
			new Insert_Patient();
		}

		if (e.getSource() == b3) {
			new Select_Patient();
		}

		if (e.getSource() == b4) {
			new Select_Bed();
		}

		if (e.getSource() == b5) {
			new leave_cost();
		}

		// 处理“病人开药”按钮的点击事件
		if (e.getSource() == b6) {
			new PrescribeMedicine(); // 假设 PrescribeMedicine 类是实现“病人开药”功能的类
		}
	}
}