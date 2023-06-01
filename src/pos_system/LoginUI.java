/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pos_system;


import javax.swing.JFrame;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;







/**
 *
 * @author spfi-lipa-otc
 */
public class LoginUI extends javax.swing.JFrame {

    /**
     * Creates new form LoginUI
     */
    
    private Animator animatorLogin;
    
    
    public LoginUI() {
        initComponents();
        connection();
        TimingTarget targetLogin = new TimingTargetAdapter(){
         @Override
         public void timingEvent(float fraction){
             flowanimation.setAnimate(fraction);
         }
        };
        animatorLogin = new Animator(2000, targetLogin);
        animatorLogin.setResolution(0);
    }
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    
    public void connection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try{
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","");
            } catch (SQLException ex) {
                
            }
        } catch (ClassNotFoundException ex) {
            
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoginPanel = new javax.swing.JPanel();
        usernamelogo = new javax.swing.JLabel();
        passwordlogo = new javax.swing.JLabel();
        titlebarpanel = new javax.swing.JPanel();
        Exit = new javax.swing.JButton();
        Minimize = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        titlebar = new javax.swing.JLabel();
        password = new pos_system.PasswordTF();
        username = new pos_system.UsernameTF();
        Login = new pos_system.LoginButton();
        Title = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        background = new javax.swing.JLabel();
        flowanimation = new pos_system.FlowAnimation();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LoginPanel.setBackground(new java.awt.Color(140, 87, 71));
        LoginPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 214)));
        LoginPanel.setPreferredSize(new java.awt.Dimension(1205, 600));
        LoginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernamelogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Username Logo.png"))); // NOI18N
        LoginPanel.add(usernamelogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 40, 30));

        passwordlogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Password Logo.png"))); // NOI18N
        LoginPanel.add(passwordlogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 390, 40, 30));

        titlebarpanel.setBackground(new java.awt.Color(89, 56, 46));
        titlebarpanel.setPreferredSize(new java.awt.Dimension(1205, 30));
        titlebarpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Exit.setBackground(new java.awt.Color(51, 25, 0));
        Exit.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        Exit.setForeground(new java.awt.Color(232, 240, 214));
        Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Close icon.png"))); // NOI18N
        Exit.setBorder(null);
        Exit.setContentAreaFilled(false);
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        titlebarpanel.add(Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 0, 30, 30));

        Minimize.setBackground(new java.awt.Color(51, 25, 0));
        Minimize.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        Minimize.setForeground(new java.awt.Color(232, 240, 214));
        Minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimize icon.png"))); // NOI18N
        Minimize.setBorder(null);
        Minimize.setContentAreaFilled(false);
        Minimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinimizeActionPerformed(evt);
            }
        });
        titlebarpanel.add(Minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 0, 30, 30));

        title.setBackground(new java.awt.Color(232, 240, 214));
        title.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        title.setForeground(new java.awt.Color(232, 240, 214));
        title.setText("Point of Sale System - Login UI");
        titlebarpanel.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 210, 30));

        titlebar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Title.png"))); // NOI18N
        titlebarpanel.add(titlebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 30));

        LoginPanel.add(titlebarpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 30));

        password.setBackground(new java.awt.Color(140, 87, 71));
        password.setForeground(new java.awt.Color(232, 240, 214));
        password.setCaretColor(new java.awt.Color(232, 240, 214));
        password.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        password.setLabelText("Password");
        password.setShowAndHide(true);
        password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordActionPerformed(evt);
            }
        });
        LoginPanel.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 370, 190, 60));

        username.setBackground(new java.awt.Color(140, 87, 71));
        username.setForeground(new java.awt.Color(232, 240, 214));
        username.setCaretColor(new java.awt.Color(232, 240, 217));
        username.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        username.setLabelText("Username");
        LoginPanel.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 290, 190, 60));

        Login.setBackground(new java.awt.Color(128, 71, 54));
        Login.setBorder(null);
        Login.setForeground(new java.awt.Color(232, 240, 214));
        Login.setText("Login");
        Login.setFont(new java.awt.Font("Century Gothic", 2, 18)); // NOI18N
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });
        LoginPanel.add(Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 440, 110, 30));

        Title.setBackground(new java.awt.Color(232, 240, 214));
        Title.setFont(new java.awt.Font("Goudy Old Style", 3, 24)); // NOI18N
        Title.setForeground(new java.awt.Color(232, 240, 214));
        Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Title.setText("SIGN IN");
        Title.setNextFocusableComponent(Logo);
        LoginPanel.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 240, 170, 40));

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Login UI Design.png"))); // NOI18N
        LoginPanel.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 330, 470));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.png"))); // NOI18N
        LoginPanel.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 600));

        getContentPane().add(LoginPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, -1));

        flowanimation.setBackground(new java.awt.Color(140, 87, 71));
        flowanimation.setOpaque(true);
        flowanimation.setLayout(new java.awt.CardLayout());
        getContentPane().add(flowanimation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
    System.exit(0); 
           }//GEN-LAST:event_ExitActionPerformed
    int pX, pY;
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        pX = evt.getX();
        pY = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - pX,
                         this.getLocation().y + evt.getY() - pY);
    }//GEN-LAST:event_formMouseDragged

    private void MinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeActionPerformed
    setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_MinimizeActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
    this.requestFocus();
    }//GEN-LAST:event_formMouseClicked

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
    String user = username.getText();
    String pass = password.getText();

    if (user.equals("")) {
    username.setHelperText("Please input username");
    username.grabFocus();
    } else if (pass.equals("")) {
    password.setHelperText("Please input password");
    password.grabFocus();
    } else {
    try {
        pst = con.prepareStatement("Select * From accounts");
        rs = pst.executeQuery();

        boolean found = false;
        while (rs.next()) {
            String un = rs.getString("user");
            String pn = rs.getString("pass");
            if (user.equals(un) && pass.equals(pn)) {
                found = true;
                break;
            }
        }
        if (found) {
            LoginPanel.setVisible(false);
            animatorLogin.start();
            TimingTarget targetLogin = new TimingTargetAdapter() {
                @Override
                public void timingEvent(float fraction) {
                    flowanimation.setAnimate(fraction);
                }
                
                @Override
                public void end() {
                    new DearDoughnut().setVisible(true);
                    setVisible(false);
                }
            };
            animatorLogin.addTarget(targetLogin);
        } else {
            username.setHelperText("Incorrect username");
            password.setHelperText("Incorrect password");
            username.grabFocus();
        }
    } catch (SQLException ex) {
        // handle the SQL exception
    }
}

    }//GEN-LAST:event_LoginActionPerformed

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("FlatLaf Dark".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Exit;
    private pos_system.LoginButton Login;
    private javax.swing.JPanel LoginPanel;
    private javax.swing.JLabel Logo;
    private javax.swing.JButton Minimize;
    private javax.swing.JLabel Title;
    private javax.swing.JLabel background;
    private pos_system.FlowAnimation flowanimation;
    private pos_system.PasswordTF password;
    private javax.swing.JLabel passwordlogo;
    private javax.swing.JLabel title;
    private javax.swing.JLabel titlebar;
    private javax.swing.JPanel titlebarpanel;
    private pos_system.UsernameTF username;
    private javax.swing.JLabel usernamelogo;
    // End of variables declaration//GEN-END:variables
}
