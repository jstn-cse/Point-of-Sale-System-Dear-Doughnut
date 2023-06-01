/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pos_system;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class SalesUI extends javax.swing.JFrame {



    public SalesUI() {
        initComponents();
        getTableName();
        orderlistTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        orderlistTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        orderlistTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        orderlistTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        OrderListScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
    }

    public void getTableName() {
      //Get the data from database and store it into JComboBox. This also works as a refresh function
      try {
          //Step 1: Create a connection to the database
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/receiptlist", "root", "");

          //Step 2: Execute a query to retrieve all tables in the database
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery("SHOW TABLES");

          //Step 3: Clear the JComboBox
          purchaseList.removeAllItems();

          //Step 4: Iterate through the ResultSet and add each table name to the JComboBox
          while(rs.next()) {
              String tableName = rs.getString(1);
              purchaseList.addItem(tableName);
          }

          //Step 5: Close the connection to the database
          conn.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        Exit = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        titlebar = new javax.swing.JLabel();
        titlebarpanel = new javax.swing.JPanel();
        Minimize = new javax.swing.JButton();
        OrderListScrollPane = new javax.swing.JScrollPane();
        orderlistTable = new javax.swing.JTable();
        purchaseList = new pos_system.ComboBoxSuggestion();
        refreshButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        totalQtyTF = new javax.swing.JTextField();
        totalAmountTF = new javax.swing.JTextField();
        taxAmountTF = new javax.swing.JTextField();
        cashAmountTF = new javax.swing.JTextField();
        changeTF = new javax.swing.JTextField();
        selectedPurchaseID = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainPanel.setBackground(new java.awt.Color(108, 68, 56));
        MainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 214), 2));
        MainPanel.setPreferredSize(new java.awt.Dimension(910, 557));
        MainPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                MainPanelMouseDragged(evt);
            }
        });
        MainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MainPanelMousePressed(evt);
            }
        });
        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        MainPanel.add(Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 0, 30, 30));

        title.setBackground(new java.awt.Color(232, 240, 214));
        title.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        title.setForeground(new java.awt.Color(232, 240, 214));
        title.setText("SALES");
        MainPanel.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 0, 60, 30));

        titlebar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Title.png"))); // NOI18N
        titlebar.setPreferredSize(new java.awt.Dimension(911, 25));
        MainPanel.add(titlebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 30, 30));

        titlebarpanel.setBackground(new java.awt.Color(89, 56, 46));
        titlebarpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 214)));
        titlebarpanel.setPreferredSize(new java.awt.Dimension(911, 30));
        titlebarpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        titlebarpanel.add(Minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 0, 30, 30));

        MainPanel.add(titlebarpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, -1));

        OrderListScrollPane.setBackground(new java.awt.Color(108, 68, 56));
        OrderListScrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(89, 56, 46), 2));

        orderlistTable.setBackground(new java.awt.Color(108, 68, 56));
        orderlistTable.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        orderlistTable.setForeground(new java.awt.Color(232, 240, 214));
        orderlistTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Product Name", "Quantity", "Item Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderlistTable.getTableHeader().setReorderingAllowed(false);
        OrderListScrollPane.setViewportView(orderlistTable);
        if (orderlistTable.getColumnModel().getColumnCount() > 0) {
            orderlistTable.getColumnModel().getColumn(0).setResizable(false);
            orderlistTable.getColumnModel().getColumn(1).setResizable(false);
            orderlistTable.getColumnModel().getColumn(2).setResizable(false);
            orderlistTable.getColumnModel().getColumn(3).setResizable(false);
        }

        MainPanel.add(OrderListScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 71, 790, 430));

        purchaseList.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        purchaseList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchaseListActionPerformed(evt);
            }
        });
        MainPanel.add(purchaseList, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 38, 370, 25));

        refreshButton.setBackground(new java.awt.Color(108, 68, 56));
        refreshButton.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(232, 240, 214));
        refreshButton.setText("Refresh");
        refreshButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 214)));
        refreshButton.setPreferredSize(new java.awt.Dimension(70, 25));
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        MainPanel.add(refreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 38, -1, -1));

        deleteButton.setBackground(new java.awt.Color(108, 68, 56));
        deleteButton.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(232, 240, 214));
        deleteButton.setText("Delete");
        deleteButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 214)));
        deleteButton.setPreferredSize(new java.awt.Dimension(70, 25));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        MainPanel.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 38, -1, -1));
        deleteButton.getAccessibleContext().setAccessibleName("");

        backButton.setBackground(new java.awt.Color(108, 68, 56));
        backButton.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        backButton.setForeground(new java.awt.Color(232, 240, 214));
        backButton.setText("Back");
        backButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 214)));
        backButton.setPreferredSize(new java.awt.Dimension(70, 25));
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        MainPanel.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 38, -1, -1));
        backButton.getAccessibleContext().setAccessibleName("");

        totalQtyTF.setEditable(false);
        totalQtyTF.setBackground(new java.awt.Color(89, 56, 46));
        totalQtyTF.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        totalQtyTF.setForeground(new java.awt.Color(232, 240, 214));
        totalQtyTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalQtyTF.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(84, 56, 29), new java.awt.Color(84, 56, 29)));
        MainPanel.add(totalQtyTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 130, 100, 30));

        totalAmountTF.setEditable(false);
        totalAmountTF.setBackground(new java.awt.Color(89, 56, 46));
        totalAmountTF.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        totalAmountTF.setForeground(new java.awt.Color(232, 240, 214));
        totalAmountTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalAmountTF.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(84, 56, 29), new java.awt.Color(84, 56, 29)));
        totalAmountTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalAmountTFActionPerformed(evt);
            }
        });
        MainPanel.add(totalAmountTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 290, 100, 30));

        taxAmountTF.setEditable(false);
        taxAmountTF.setBackground(new java.awt.Color(89, 56, 46));
        taxAmountTF.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        taxAmountTF.setForeground(new java.awt.Color(232, 240, 214));
        taxAmountTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        taxAmountTF.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(84, 56, 29), new java.awt.Color(84, 56, 29)));
        taxAmountTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taxAmountTFActionPerformed(evt);
            }
        });
        MainPanel.add(taxAmountTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 210, 100, 30));

        cashAmountTF.setEditable(false);
        cashAmountTF.setBackground(new java.awt.Color(89, 56, 46));
        cashAmountTF.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cashAmountTF.setForeground(new java.awt.Color(232, 240, 214));
        cashAmountTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cashAmountTF.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(84, 56, 29), new java.awt.Color(84, 56, 29)));
        cashAmountTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashAmountTFActionPerformed(evt);
            }
        });
        MainPanel.add(cashAmountTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 370, 100, 30));

        changeTF.setEditable(false);
        changeTF.setBackground(new java.awt.Color(89, 56, 46));
        changeTF.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        changeTF.setForeground(new java.awt.Color(232, 240, 214));
        changeTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        changeTF.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(84, 56, 29), new java.awt.Color(84, 56, 29)));
        changeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeTFActionPerformed(evt);
            }
        });
        MainPanel.add(changeTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 450, 100, 30));

        selectedPurchaseID.setBackground(new java.awt.Color(89, 56, 46));
        selectedPurchaseID.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        selectedPurchaseID.setForeground(new java.awt.Color(232, 240, 214));
        selectedPurchaseID.setText(" #");
        selectedPurchaseID.setOpaque(true);
        MainPanel.add(selectedPurchaseID, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 515, 160, 30));

        timeLabel.setBackground(new java.awt.Color(89, 56, 46));
        timeLabel.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        timeLabel.setForeground(new java.awt.Color(232, 240, 214));
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setText("Time");
        timeLabel.setOpaque(true);
        MainPanel.add(timeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 515, 110, 30));

        dateLabel.setBackground(new java.awt.Color(89, 56, 46));
        dateLabel.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(232, 240, 214));
        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLabel.setText("Date");
        dateLabel.setOpaque(true);
        MainPanel.add(dateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 515, 160, 30));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(232, 240, 214));
        jLabel1.setText("Purchase ID List :");
        MainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 120, 20));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(232, 240, 214));
        jLabel2.setText("Selected ID :");
        MainPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 515, 120, 30));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(232, 240, 214));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TAX AMOUNT");
        MainPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 180, 120, 30));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(232, 240, 214));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("TOTAL QUANTITY");
        MainPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 120, 30));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(232, 240, 214));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("TOTAL AMOUNT");
        MainPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 260, 120, 30));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(232, 240, 214));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("CASH AMOUNT");
        MainPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 340, 120, 30));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(232, 240, 214));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("CHANGE");
        MainPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 420, 80, 30));

        clearButton.setBackground(new java.awt.Color(108, 68, 56));
        clearButton.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        clearButton.setForeground(new java.awt.Color(232, 240, 214));
        clearButton.setText("Clear");
        clearButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 214)));
        clearButton.setPreferredSize(new java.awt.Dimension(70, 25));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        MainPanel.add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 38, -1, -1));
        clearButton.getAccessibleContext().setAccessibleName("");

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 560));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_ExitActionPerformed
int pX, pY;
    private void MainPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainPanelMousePressed
        pX = evt.getX();
        pY = evt.getY();
    }//GEN-LAST:event_MainPanelMousePressed

    private void MainPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainPanelMouseDragged
       this.setLocation(this.getLocation().x + evt.getX() - pX,
                         this.getLocation().y + evt.getY() - pY);
    }//GEN-LAST:event_MainPanelMouseDragged

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
    this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void changeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeTFActionPerformed

    }//GEN-LAST:event_changeTFActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
    getTableName();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void cashAmountTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashAmountTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cashAmountTFActionPerformed

    private void taxAmountTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taxAmountTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_taxAmountTFActionPerformed

    private void MinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeActionPerformed
    setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_MinimizeActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
    totalQtyTF.setText("");
    totalAmountTF.setText("");
    taxAmountTF.setText("");
    cashAmountTF.setText("");
    changeTF.setText("");
    dateLabel.setText("Date");
    timeLabel.setText("Time");
    selectedPurchaseID.setText(" #");
    DefaultTableModel dm = (DefaultTableModel)orderlistTable.getModel();
    while(dm.getRowCount() > 0)
    {
        dm.removeRow(0);
    }
    }//GEN-LAST:event_clearButtonActionPerformed

    private void totalAmountTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalAmountTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalAmountTFActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
    String pass = "omcm";
    JPasswordField inputKeyField = new JPasswordField();
    int result = JOptionPane.showConfirmDialog(null, inputKeyField, "Enter the confirmation key:", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
        String inputKey = new String(inputKeyField.getPassword());
        if (pass.equals(inputKey)) {
            //code here
            try {
                // Get the selected table name
                String tableName = (String) purchaseList.getSelectedItem();

                //Step 1: Create a connection to the database
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/receiptlist", "root", "");

                //Step 2: Drop the selected table
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/receiptlist", "root", "");
                Statement statement = connection.createStatement();
                statement.executeUpdate("DROP TABLE " + tableName);

                statement.close();
                connection.close();

                // Refresh the list of tables
                purchaseList.removeAllItems();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SHOW TABLES");
                while(rs.next()) {
                    tableName = rs.getString(1);
                    purchaseList.addItem(tableName);
                }
                totalQtyTF.setText("");
                totalAmountTF.setText("");
                taxAmountTF.setText("");
                cashAmountTF.setText("");
                changeTF.setText("");
                dateLabel.setText("Date");
                timeLabel.setText("Time");
                selectedPurchaseID.setText("#");
                DefaultTableModel dm = (DefaultTableModel)orderlistTable.getModel();
                while(dm.getRowCount() > 0)
                {
                    dm.removeRow(0);
                }
                //Step 5: Close the connection to the database
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            if (inputKey.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Key");
            }
            else {
                JOptionPane.showMessageDialog(null, "Incorrect key, please try again.");
            }
        }
    }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void purchaseListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchaseListActionPerformed
            try {
              // Establish database connection
              Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/receiptlist", "root", "");
              String tableName = (String) purchaseList.getSelectedItem();
              Statement stmt = conn.createStatement();
              if(tableName != null && !tableName.isEmpty()){
              ResultSet rs = stmt.executeQuery("SELECT Item_ID,Product_Name,Quantity,Item_Price,Total_Tax,Total_Price,Cash,Cash_Change,Total_Quantity,Time,Date FROM " + tableName);
              ResultSetMetaData rsmd = rs.getMetaData();
              int columnCount = rsmd.getColumnCount();

              // Clear the table and text fields
              DefaultTableModel model = (DefaultTableModel) orderlistTable.getModel();
              model.setRowCount(0);
              taxAmountTF.setText("");
              totalAmountTF.setText("");
              cashAmountTF.setText("");
              changeTF.setText("");
              totalQtyTF.setText("");
              timeLabel.setText("");
              dateLabel.setText("");
              selectedPurchaseID.setText("");

          //insert the data into the components
          while(rs.next()) {
                Object[] data = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    data[i-1] = rs.getObject(i);
                }
                model.insertRow(0, data);

                taxAmountTF.setText(rs.getString("Total_Tax"));
                totalAmountTF.setText(rs.getString("Total_Price"));
                cashAmountTF.setText(rs.getString("Cash"));
                changeTF.setText(rs.getString("Cash_Change"));
                totalQtyTF.setText(rs.getString("Total_Quantity"));
                timeLabel.setText(rs.getString("Time"));
                dateLabel.setText(rs.getString("Date"));
                selectedPurchaseID.setText(tableName);
            }
                

              }
          conn.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
            
    }//GEN-LAST:event_purchaseListActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SalesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalesUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Exit;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton Minimize;
    private javax.swing.JScrollPane OrderListScrollPane;
    private javax.swing.JButton backButton;
    private javax.swing.JTextField cashAmountTF;
    private javax.swing.JTextField changeTF;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTable orderlistTable;
    private pos_system.ComboBoxSuggestion purchaseList;
    private javax.swing.JButton refreshButton;
    private javax.swing.JLabel selectedPurchaseID;
    private javax.swing.JTextField taxAmountTF;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel title;
    private javax.swing.JLabel titlebar;
    private javax.swing.JPanel titlebarpanel;
    private javax.swing.JTextField totalAmountTF;
    private javax.swing.JTextField totalQtyTF;
    // End of variables declaration//GEN-END:variables
}
