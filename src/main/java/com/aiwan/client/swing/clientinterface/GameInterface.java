package com.aiwan.client.swing.clientinterface;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.service.infoSend.MessageSendModule;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.NavigationBar;
import com.aiwan.client.util.Verification;
import com.aiwan.server.user.account.protocol.CM_Logout;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 游戏界面
 * @author dengzebiao
 * @since 2019.6.17
 * */
public class GameInterface extends JFrame{
    private static Logger logger = LoggerFactory.getLogger(GameInterface.class);
    /** 导航栏 */
    private NavigationBar navigationBar = new NavigationBar();

    /** 提示信息 */
    private JTextArea promptMessage;

    /** 地图信息 */
    private JTextArea mapMessage;

    /** 其他信息 */
    private JTextArea otherMessage;

    /**
     * 时间字段
     */
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** 初始化 */
    public GameInterface(){
        //接入界面管理
        InterfaceManager.putFrame("game",this);
        //设置长宽
        setSize(1000, 650);
        //添加导航栏
        this.add(navigationBar);
        //消除格式
        this.setLayout(null);

        //添加信息输入 功能选择框+输入框+按钮
        JLabel funcPrompt = new JLabel("功能框：");
        funcPrompt.setFont(new Font("楷体",Font.BOLD,16));
        funcPrompt.setBounds(150,50,80,50);
        JLabel messagePrompt = new JLabel("指令框：");
        messagePrompt.setBounds(360,50,80,50);
        messagePrompt.setFont(new Font("楷体",Font.BOLD,16));

        //添加指示
        this.add(funcPrompt);
        this.add(messagePrompt);

        //添加输入输入框
        final JTextField message = new JTextField();
        final JTextField func = new JTextField();
        message.setFont(new Font("楷体",Font.BOLD,16));
        func.setFont(new Font("楷体",Font.BOLD,16));

        message.setBounds(430,50,100,50);
        func.setBounds(220,50,100,50);
        JButton submit = new JButton("提交");
        //添加监听器
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Verification.canParseInt(func.getText())){
                    //功能验证
                    printOtherMessage("功能框输入不规范");
                    return;
                }
                int num  = Integer.parseInt(func.getText());
                //角色是否有
                if (LoginUser.getAccountId().equals("") || LoginUser.getRoles() == null || LoginUser.getRoles().size() == 0) {
                    //验证是否为创建角色
                    if (navigationBar.getNum() != 1 || num != 1){
                        printOtherMessage("您还未创建角色，请创建角色！");
                        return;
                    }
                }
                //获取功能选择
                MessageSendModule.getMessageModule(navigationBar.getNum()).sendMessage(message.getText(),num);
            }
        });

        submit.setBounds(550,50,80,50);
        this.add(message);
        this.add(submit);
        this.add(func);

        //添加提示信息文本框
        promptMessage = newJTextArea(0,100,800,120,5,80);
        promptMessage.append(NavigationBar.getDefaultMessage() + "\n");
        //添加其他信息文本
        otherMessage = newJTextArea(0,220,500,300,15,50);
        //添加地图文本
        mapMessage = newJTextArea(500,220,500,300,15,50);
        //可见
        this.setVisible(false);
        //加入清空按钮
        JButton clear = new JButton("清空");
        //添加监听器
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                otherMessage.setText("");
            }
        });
        clear.setBounds(40, 530, 80, 40);
        this.add(clear);
        //添加关闭框
        addWindowListener (new WindowAdapter()
        {
            @Override
            public void windowClosing ( WindowEvent e )
            {
                logout();
            }
        });
    }

    /** 创建对应文本域 */
    private JTextArea newJTextArea(int x,int y,int width,int height,int row,int columns){
        //创建一个JPanel对象
        JPanel jp=new JPanel();
        jp.setBounds(x,y,width,height);
        JTextArea jTextArea=new JTextArea(row,columns);
        //设置文本域中的文本为自动换行
        jTextArea.setLineWrap(true);
        //设置组件的背景色
        jTextArea.setForeground(Color.BLACK);
        //修改字体样式
        jTextArea.setFont(new Font("楷体",Font.BOLD,16));
        jTextArea.setEditable(false);
        //将文本域放入滚动窗口
        JScrollPane jsp=new JScrollPane(jTextArea);
        jsp.setBounds(x,y,width,height);
        //将JScrollPane添加到JPanel容器中
        jp.add(jsp);
        //将JPanel容器添加到JFrame容器中
        this.add(jp);
        return jTextArea;
    }

    /** 输出提示信息 */
    public void printPromptMessage(String message) {
        promptMessage.setText(message);
    }

    /** 输出地图提示信息 */
    public void printMapMessage(String message){
        mapMessage.setText(message+"\n");
    }

    /** 输出其他提示信息 */
    public void printOtherMessage(String message) {

        otherMessage.append("\n~~~~~~~~~[" + df.format(new Date()) + "]~~~~~~~~~~\n\n");
        otherMessage.append(message);
    }

    /** 用户注销 */
    public static void logout(){
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        //清空文本域
        gameInterface.getMapMessage().setText("");
        gameInterface.getOtherMessage().setText("");
        //发送注销协议
        CM_Logout cm_logout = new CM_Logout();
        cm_logout.setUsername(LoginUser.getAccountId());
        ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.LOGOUT,cm_logout));
        InterfaceManager.getFrame("game").setVisible(false);
        InterfaceManager.getFrame("login").setVisible(true);
    }

    public JTextArea getPromptMessage() {
        return promptMessage;
    }

    public GameInterface setPromptMessage(JTextArea promptMessage) {
        this.promptMessage = promptMessage;
        return this;
    }

    public JTextArea getMapMessage() {
        return mapMessage;
    }

    public GameInterface setMapMessage(JTextArea mapMessage) {
        this.mapMessage = mapMessage;
        return this;
    }

    public JTextArea getOtherMessage() {
        return otherMessage;
    }

    public GameInterface setOtherMessage(JTextArea otherMessage) {
        this.otherMessage = otherMessage;
        return this;
    }
}
