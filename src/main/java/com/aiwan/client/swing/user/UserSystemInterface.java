package com.aiwan.client.swing.user;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.NavigationBar;
import com.aiwan.server.scenes.protocol.CM_Move;
import com.aiwan.server.scenes.protocol.SM_Move;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 用户系统界面
 * @author dengzebiao
 * @since 2019.6.17
 * */
//@InfoReceiveObject
public class UserSystemInterface extends JFrame {

    private static Logger logger = LoggerFactory.getLogger(UserSystemInterface.class);
    /** 导航栏 */
    private NavigationBar navigationBar = new NavigationBar();

    /** 文本域 */
    private JTextArea jTextArea;


    public UserSystemInterface(){
        this.setLayout(null);
        setTitle("用户系统");
        setSize(600, 600);
        setVisible(false);
        this.add(navigationBar);
        InterfaceManager.putFrame("userSystem",this);

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

        //添加文本域
        addTextArea();
        //添加移动功能
        addMoveFunction();
    }

    /** 输出信息 */
    public void printMessage(String message){
        jTextArea.append(message+"\n");
    }

    /** 添加文本域 */
    private void addTextArea(){
        //创建一个JPanel对象
        JPanel jp=new JPanel();
        jp.setBounds(0,100,600,400);
        jTextArea=new JTextArea(20,50);
        //设置文本域中的文本为自动换行
        jTextArea.setLineWrap(true);
        //设置组件的背景色
        jTextArea.setForeground(Color.BLACK);
        //修改字体样式
        jTextArea.setFont(new Font("楷体",Font.BOLD,16));
        jTextArea.setEditable(false);
        //将文本域放入滚动窗口
        JScrollPane jsp=new JScrollPane(jTextArea);
        jsp.setBounds(110,90,600,400);
        //将JScrollPane添加到JPanel容器中
        jp.add(jsp);
        //将JPanel容器添加到JFrame容器中
        this.add(jp);
    }

    /** 添加移动功能 */
    public void addMoveFunction(){
        //添加坐标输入框
        final JTextField position = new JTextField();
        JLabel userLabel = new JLabel("坐标:");
        userLabel.setFont(new Font("楷体",Font.BOLD,18));
        userLabel.setBounds(50,50,50,40);
        position.setBounds(100,50,100,40);
        //添加按钮
        JButton moveButton = new JButton("移动");
        moveButton.setBounds(200,50,80,40);
        //添加监听器
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取文本框中的内容
                String[] nums = position.getText().split(" ");
                int targetX = Integer.parseInt(nums[0]);
                int targetY = Integer.parseInt(nums[1]);
                CM_Move move = CM_Move.valueOf(LoginUser.getCurrentX(),LoginUser.getCurrentY(),targetX,targetY,LoginUser.getUsername());
                ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.MOVE,move));
            }
        });
        this.add(userLabel);
        this.add(position);
        this.add(moveButton);
    }

    /** 地图条状功能添加 */
    public void shift(){
        final JTextField shiftField = new JTextField();
        JLabel shiftText = new JLabel("坐标:");
        shiftText.setFont(new Font("楷体",Font.BOLD,18));
        shiftText.setBounds(300,50,50,40);
        shiftField.setBounds(350,50,50,40);

        //添加跳转按钮
        JButton shiftButton = new JButton("移动");
        shiftButton.setBounds(200,50,80,40);
    }

    @InfoReceiveMethod(status = StatusCode.MOVESUCCESS)
    public void move(SM_Move sm_move){
        LoginUser.setCurrentY(sm_move.getTargetY());
        LoginUser.setCurrentX(sm_move.getTargetX());
    }


}
