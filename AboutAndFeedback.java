package Hospitaladmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import linkdatabase.linkdatabase;

public class AboutAndFeedback extends JFrame {
    public AboutAndFeedback() {
        setTitle("说明与反馈");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 系统介绍面板
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BorderLayout());
        JLabel aboutLabel = new JLabel("杏林云枢·智慧协同诊疗系统", SwingConstants.CENTER);
        aboutLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));

        JTextArea aboutText = new JTextArea(
                "1.本系统由软件三班刘同学，黄同学，王同学，姚同学，李同学共同完成。\n" +
                        "2.系统还有待优化地方还请在下方反馈中提交，我们会及时处理与改进。\n" +
                        "3.你的反馈是我们不断前进的动力，在此感谢您给予我们的帮助\n"
        );
        aboutText.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        aboutText.setEditable(false);
        JScrollPane aboutScrollPane = new JScrollPane(aboutText);
        // 调整说明部分滚动面板的首选大小，减少空白
        aboutScrollPane.setPreferredSize(new Dimension(700, 100));
        aboutPanel.add(aboutLabel, BorderLayout.NORTH);
        aboutPanel.add(aboutScrollPane, BorderLayout.CENTER);

        // 用户反馈面板
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel feedbackLabel = new JLabel("用户反馈：");
        feedbackLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        feedbackPanel.add(feedbackLabel, gbc);

        JTextArea feedbackText = new JTextArea(5, 30);
        feedbackText.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackText);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        feedbackPanel.add(feedbackScrollPane, gbc);

        JButton submitButton = new JButton("提交反馈");
        submitButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        submitButton.addActionListener(e -> {
            String content = feedbackText.getText().trim();
            if (content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "反馈内容不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String currentDno = "D001";
            saveFeedback(currentDno, content);
            feedbackText.setText("");
            JOptionPane.showMessageDialog(this, "反馈提交成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        feedbackPanel.add(submitButton, gbc);

        add(aboutPanel, BorderLayout.NORTH);
        add(feedbackPanel, BorderLayout.CENTER);
        setResizable(false);
        setVisible(true);
    }

    private void saveFeedback(String dno, String content) {
        String sql = "INSERT INTO Feedback (Dno, FeedbackContent, FeedbackTime) VALUES (?,?, NOW())";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = linkdatabase.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, dno);
            ps.setString(2, content);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "反馈提交失败，请联系管理员", "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            linkdatabase.closeAll(null, ps, con);
        }
    }
    }
