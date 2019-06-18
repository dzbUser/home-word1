package com.aiwan.client.swing.clientinterface;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.server.user.account.protocol.CM_HLogin;
import com.aiwan.server.user.account.protocol.CM_Login;
import com.aiwan.server.user.account.protocol.SM_UserMessage;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author dengzebiao
 * @since 2019.6.18
 * 高级登录页面
 * */
public class HeightLoginInterface extends JFrame {

    /** 用户账号输入框 */
    private JTextField userText ;

    /** 用户密码输入框 */
    private JPasswordField passwordText;

    public HeightLoginInterface(){
        // Setting the width and height of frame
        setTitle("高级登录");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        setVisible(false);

        //添加到界面集合中
        InterfaceManager.putFrame("heightLogin",this);
    }

    private void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("账号:");
        userLabel.setBounds(50,50,50,40);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        this.userText = new JTextField(20);
        userText.setBounds(50,100,300,40);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("高级密码:");
        passwordLabel.setBounds(50,150,100,40);
        panel.add(passwordLabel);

        //创建密码窗口
        this.passwordText = new JPasswordField(20);
        passwordText.setBounds(50,200,300,40);
        panel.add(passwordText);

        // 创建登录按钮
        JButton loginButton = new JButton("普通登录");
        //添加登录事件事件监听
        loginButton.addActionListener(new LoginListener());

        // 创建提交按钮
        JButton submit = new JButton("提交");
        //添加登录事件事件监听
        submit.addActionListener(new SubmitListener());
        //创建注册按钮
        JButton registerButton = new JButton("注册");
        //添加注册事件事件监听
        registerButton.addActionListener(new RegisterListener());

        loginButton.setBounds(50, 250, 100, 40);
        submit.setBounds(160, 250, 100, 40);
        registerButton.setBounds(270,250,100,40);

        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(submit);
    }

    /** 处理高级登录事件的匿名内部类 */
    class LoginListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            InterfaceManager.getFrame("login").setVisible(true);
            HeightLoginInterface.super.setVisible(false);
        }
    }

    /** 处理注册事件的匿名内部类 */
    class RegisterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            HeightLoginInterface.super.setVisible(false);
            InterfaceManager.getFrame("register").setVisible(true);
        }
    }

    /** 处理注册事件的匿名内部类 */
    class SubmitListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            CM_HLogin cm_login = new CM_HLogin();
            cm_login.setHpassword(String.valueOf(passwordText.getPassword()));
            cm_login.setUsername(userText.getText());
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.HLOGIN,cm_login));
        }
    }

    public JTextField getUserText() {
        return userText;
    }

    public JPasswordField getPasswordText() {
        return passwordText;
    }
}
