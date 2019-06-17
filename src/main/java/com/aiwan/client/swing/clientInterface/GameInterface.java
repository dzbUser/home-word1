package com.aiwan.client.swing.clientInterface;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.service.infoSend.MessageSendModule;
import com.aiwan.client.swing.NavigationBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 游戏界面
 * @author dengzebiao
 * @since 2019.6.17
 * */
public class GameInterface extends JFrame{
    /** 导航栏 */
    private NavigationBar navigationBar = new NavigationBar();

    /** 提示信息 */
    private JTextArea promptMessage;

    /** 地图信息 */
    private JTextArea mapMessage;

    /** 其他信息 */
    private JTextArea otherMessage;

    /** 初始化 */
    public GameInterface(){
        //接入界面管理
        InterfaceManager.putFrame("game",this);
        //设置长宽
        setSize(600, 600);
        //添加导航栏
        this.add(navigationBar);
        //消除格式
        this.setLayout(null);

        //添加信息输入 功能选择框+输入框+按钮
        final JTextField message = new JTextField();
        final JTextField func = new JTextField();
        message.setBounds(150,50,300,50);
        func.setBounds(50,50,100,50);
        JButton submit = new JButton("提交");
        //添加监听器
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //角色是否有
                if (LoginUser.getUsername().equals("")||LoginUser.getRoles() ==null || LoginUser.getRoles().size() == 0){
                   printOtherMessage("您还未创建角色，请创建角色！");
                    return;
                }
                //获取功能选择
                int num  = Integer.parseInt(func.getText());
                MessageSendModule.getMessageModule(navigationBar.getNum()).sendMessage(message.getText(),num);
            }
        });

        submit.setBounds(450,50,80,50);
        this.add(message);
        this.add(submit);
        this.add(func);
        //添加提示信息
        promptMessage = newJTextArea(0,100,600,120,5,60);
        promptMessage.append(NavigationBar.ROLEMESSAGE+"\n");
        //添加其他信息文本
        otherMessage = newJTextArea(0,220,300,300,15,30);
        //添加地图文本
        mapMessage = newJTextArea(300,220,300,300,15,25);
        //可见
        this.setVisible(false);

        //添加关闭框
        addWindowListener (new WindowAdapter()
        {
            @Override
            public void windowClosing ( WindowEvent e )
            {
                int exi = JOptionPane.showConfirmDialog (null, "要退出该程序吗？", "友情提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (exi == JOptionPane.YES_OPTION)
                {
                    System.exit (0);
                }
                else
                {
                    return;
                }
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
    public void printPromptMessage(String message){
        //清空信息
        promptMessage.setText("");
        promptMessage.append(message+"\n");
    }

    /** 输出地图提示信息 */
    public void printMapMessage(String message){
        mapMessage.append(message+"\n");
    }

    /** 输出其他提示信息 */
    public void printOtherMessage(String message){
        otherMessage.append(message+"\n");
    }
}
