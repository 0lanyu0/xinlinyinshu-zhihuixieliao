package Hospitallogin;

import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Doctorfunction.Main;
import Hospitaladmin.*;
import Hospitalresponse.Alter;
import geng.handle.HandleLogin;
import geng.model.Login;
import linkdatabase.linkdatabase;

public class LoginFrame extends JFrame implements ActionListener {

	String sql;
	Login login = new Login();
	HandleLogin handleLogin = new HandleLogin();
	JLabel l1 = new JLabel("欢迎使用杏林云枢·智慧协同诊疗系统");
	JLabel l2 = new JLabel("账号:");
	JLabel l3 = new JLabel("密码:");
	JLabel l4 = new JLabel("账号不存在！");
	JLabel l6 = new JLabel("<HTML><U>" + "修改密码" + "</U></HTML>");

	JTextField t1 = new JTextField();
	JTextField t2 = new JTextField();

	Button b1 = new Button("登陆");
	Button b2 = new Button("取消");

	// 主界面的按钮
	Button appointmentButton = new Button("挂号预约");
	Button feeQueryButton = new Button("费用查询");
	Button departmentFloorButton = new Button("科室查询");
	Button instructionFeedbackButton = new Button("说明与反馈");

	public LoginFrame() {
		// 设置标题
		super("欢迎登陆杏林云枢·智慧协同诊疗系统");
		setBounds(400, 200, 850, 600);
		String path = "C:\\Users\\86175\\Desktop\\杏林云枢·智慧协同诊疗系统\\杏林云枢·智慧协同诊疗系统\\HospitalWardManagementSystem\\lib\\login.png";
		ImageIcon backgroundimage = new ImageIcon(path);
		JLabel jLabel = new JLabel(backgroundimage);
		jLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		JPanel jPanel1 = (JPanel) this.getContentPane();
		jPanel1.setOpaque(false);
		jPanel1.setLayout(null);
		this.getLayeredPane().add(jLabel, new Integer(Integer.MIN_VALUE));
		setVisible(true);

		// 登陆界面配置
		l1.setBounds(200, 80, 3000, 30);
		l1.setFont(new Font("宋体", Font.BOLD, 30));
		l2.setBounds(300, 160, 70, 25);
		l2.setFont(new Font("宋体", Font.BOLD, 23));
		l3.setBounds(300, 220, 70, 25);
		l3.setFont(new Font("宋体", Font.BOLD, 23));
		l4.setBounds(200, 220, 150, 20);
		l4.setFont(new Font("宋体", Font.BOLD, 23));

		l6.setBounds(550, 220, 70, 25);
		l6.setFont(new Font("微软雅黑", Font.BOLD, 15));
		l6.setForeground(Color.blue);
		l6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		l6.addMouseListener(new Alter());

		t1.setBounds(370, 160, 160, 25);
		t1.setFont(new Font("宋体", 0, 18));
		t2.setBounds(370, 220, 160, 25);
		t2.setFont(new Font("宋体", 0, 18));

		b1.setBounds(370, 280, 70, 30);
		b1.setFont(new Font("宋体", 0, 15));
		b1.addActionListener(this);
		b2.setBounds(490, 280, 70, 30);
		b2.setFont(new Font("宋体", 0, 15));
		b2.addActionListener(this);

		// 配置主界面的按钮
		appointmentButton.setBounds(200, 350, 120, 30);
		appointmentButton.setFont(new Font("宋体", 0, 15));
		appointmentButton.addActionListener(this);

		feeQueryButton.setBounds(330, 350, 120, 30);
		feeQueryButton.setFont(new Font("宋体", 0, 15));
		feeQueryButton.addActionListener(this);

		departmentFloorButton.setBounds(460, 350, 120, 30);
		departmentFloorButton.setFont(new Font("宋体", 0, 15));
		departmentFloorButton.addActionListener(this);

		instructionFeedbackButton.setBounds(590, 350, 120, 30);
		instructionFeedbackButton.setFont(new Font("宋体", 0, 15));
		instructionFeedbackButton.addActionListener(this);

		super.add(l1);
		super.add(l2);
		super.add(l3);
		super.add(l6);
		super.add(t1);
		super.add(t2);
		super.add(b1);
		super.add(b2);
		super.add(appointmentButton);
		super.add(feeQueryButton);
		super.add(departmentFloorButton);
		super.add(instructionFeedbackButton);

		super.setLayout(null);
		super.setVisible(true);

		// 当单击窗口右上方的关闭图标时,监视器调用windowClosing方法,如果在该方法中使用System.exit(0)退出程序的运行
		super.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}

		});
		super.setResizable(false);
	}

	public static void main(String[] args) {
		new LoginFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (source == b1) {
			String name = t1.getText();
			String pass = t2.getText();
			Login login = new Login();
			login.setDno(name);
			try {
				con = linkdatabase.getConnection();
				if ("".equals(name.trim()) || "".equals(pass.trim())) {
					JOptionPane.showMessageDialog(null, "请输入完整的登陆信息！", "系统提示", JOptionPane.ERROR_MESSAGE);
				} else if (name.equals("admin") && pass.equals("admin")) {
					new admin_frame();
					super.dispose();
				} else {
					login.setDno(name);
					login.setDpassword(pass);
					login = handleLogin.queryVerify(login);
					if (login.getLoginSuccess() == true) {
						System.out.println("登录成功了！");
						new Main(login);
						super.dispose();
					} else {
						System.out.println("登录失败了！");
						JOptionPane.showMessageDialog(null, "登录失败，请重新登录", "系统提示", JOptionPane.WARNING_MESSAGE);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				linkdatabase.closeAll(rs, ps, con);
			}
		} else if (source == b2) {
			System.exit(0);
		} else if (source == appointmentButton) {
			// 点击挂号预约按钮，打开新窗口
			JFrame appointmentFrame = new JFrame("预约管理");
			appointmentFrame.setBounds(400, 200, 850, 600);
			appointmentFrame.setLayout(null);

			// 新窗口的四个按钮
			Button newAppointmentButton = new Button("挂号预约");
			newAppointmentButton.setBounds(350, 100, 150, 50);
			newAppointmentButton.setFont(new Font("宋体", 0, 20));
			newAppointmentButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new DiagnosisAppointment();
					appointmentFrame.dispose();
				}
			});

			Button newModifyButton = new Button("修改预约");
			newModifyButton.setBounds(350, 200, 150, 50);
			newModifyButton.setFont(new Font("宋体", 0, 20));
			newModifyButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String input = JOptionPane.showInputDialog("请输入要修改的预约 ID：");
					if (input != null && !input.isEmpty()) {
						try {
							int appointmentId = Integer.parseInt(input);
							new ModifyAppointment(appointmentId);
							appointmentFrame.dispose();
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "输入的预约 ID 不是有效的数字，请重新输入！", "错误提示", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});

			Button newQueryButton = new Button("查询预约");
			newQueryButton.setBounds(350, 300, 150, 50);
			newQueryButton.setFont(new Font("宋体", 0, 20));
			newQueryButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new ViewAppointment();
					appointmentFrame.dispose();
				}
			});

			Button newDeleteButton = new Button("删除预约");
			newDeleteButton.setBounds(350, 400, 150, 50);
			newDeleteButton.setFont(new Font("宋体", 0, 20));
			newDeleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new DeleteAppointment();
					appointmentFrame.dispose();
				}
			});

			appointmentFrame.add(newAppointmentButton);
			appointmentFrame.add(newModifyButton);
			appointmentFrame.add(newQueryButton);
			appointmentFrame.add(newDeleteButton);

			appointmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			appointmentFrame.setVisible(true);
			appointmentFrame.setResizable(false);
		} else if (source == feeQueryButton) {
			// 点击费用查询按钮，调用 PrescribeInfoDisplay 的功能
			new PrescribeInfoDisplay();
		}else if (source == departmentFloorButton) {
			// 点击科室楼层管理按钮，打开新窗口
				new DepartmentFloorManagement();
		} else if (source == instructionFeedbackButton) {
				// 说明与反馈按钮的逻辑
				new AboutAndFeedback();
			}
		}
	}
