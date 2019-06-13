package com.aiwan.client.swing.user;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.InterfaceManager;
import com.aiwan.server.user.account.protocol.CM_Login;
import com.aiwan.server.user.account.protocol.CM_Registered;
import com.aiwan.server.user.account.protocol.SM_Register;
import com.aiwan.server.user.account.protocol.SM_UserMessage;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author dengzebiao
 * @since 2019.6.13
 * 注册页面
 * */
//@InfoReceiveObject
public class RegisterInterface extends JFrame {

    /** 用户账号输入框 */
    private JTextField userText ;

    /** 用户密码输入框 */
    private JPasswordField passwordText;

    /** 高级密码框 */
    private JPasswordField hightPasswordText;

    public RegisterInterface(){
        // Setting the width and height of frame
        setTitle("注册");
        setSize(400, 500);
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

        //添加到集合中
        InterfaceManager.putFrame("register",this);
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
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(50,150,100,40);
        panel.add(passwordLabel);

        //创建密码窗口
        this.passwordText = new JPasswordField(20);
        passwordText.setBounds(50,200,300,40);
        panel.add(passwordText);

        // 输入高级密码的文本域
        JLabel hPasswordLabel = new JLabel("高级密码:");
        hPasswordLabel.setBounds(50,250,50,40);
        panel.add(hPasswordLabel);

        //创建高级密码框
        this.hightPasswordText = new JPasswordField(20);
        hightPasswordText.setBounds(50,300,300,40);
        panel.add(hightPasswordText);

        // 创建登录按钮
        JButton loginButton = new JButton("登录");
        //添加登录事件事件监听
        loginButton.addActionListener(new LoginListener());

        //创建注册按钮
        JButton registerButton = new JButton("提交");
        //添加注册事件事件监听
        registerButton.addActionListener(new RegisterListener());

        registerButton.setBounds(90, 350, 100, 40);
        loginButton.setBounds(210, 350, 100, 40);
        panel.add(loginButton);
        panel.add(registerButton);

    }

    /** 跳转登录事件的匿名内部类 */
    class  LoginListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            InterfaceManager.getFrame("register").setVisible(false);
            InterfaceManager.getFrame("login").setVisible(true);
        }
    }

    /** 注册提交事件的匿名内部类 */
    class  RegisterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //注册
            CM_Registered cm_registered = new CM_Registered();
            cm_registered.setUsername(userText.getText());
            cm_registered.setPassword(String.valueOf(passwordText.getPassword()));
            cm_registered.setHpassword(String.valueOf(hightPasswordText.getPassword()));
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.REGIST,cm_registered));
        }
    }


    public JTextField getUserText() {
        return userText;
    }

    public JPasswordField getPasswordText() {
        return passwordText;
    }

    public JPasswordField getHightPasswordText() {
        return hightPasswordText;
    }

    public RegisterInterface setHightPasswordText(JPasswordField hightPasswordText) {
        this.hightPasswordText = hightPasswordText;
        return this;
    }

    public RegisterInterface setUserText(JTextField userText) {
        this.userText = userText;
        return this;
    }

    public RegisterInterface setPasswordText(JPasswordField passwordText) {
        this.passwordText = passwordText;
        return this;
    }

    /** 登录返回接收 */
    @InfoReceiveMethod(status = StatusCode.REGISTER)
    public void register(SM_Register sm_register){
        if (sm_register.getStatus() == 1){
            //注册成功
            JOptionPane.showMessageDialog(new JPanel(), sm_register.getAccountId()+"注册成功", "标题",JOptionPane.WARNING_MESSAGE);
            InterfaceManager.getFrame("register").setVisible(false);
            InterfaceManager.getFrame("login").setVisible(true);
        }else {
            JOptionPane.showMessageDialog(new JPanel(), sm_register.getAccountId()+"账号已存在", "标题",JOptionPane.WARNING_MESSAGE);
        }
    }
}
