/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pos_system;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JPasswordField;





/**
 *
 * @author spfi-lipa-otc
 */
public class DearDoughnut extends javax.swing.JFrame {
    

    
    
    
    /**
     * Creates new form AdminPOS
     */
    public DearDoughnut() {
        initComponents();
        setTime();
        
        
        Table1.getColumnModel().getColumn(0).setPreferredWidth(30);
        Table1.getColumnModel().getColumn(1).setPreferredWidth(200);
        PerPieceScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        HalfDozenScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        DozenScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        ScrollPane3.getVerticalScrollBar().setUnitIncrement(16);
        Scrollpane2.getVerticalScrollBar().setUnitIncrement(16);
        Scrollpane1.getVerticalScrollBar().setUnitIncrement(16);
        
        this.Donuts.setVisible(false);
        this.Shakes.setVisible(false);
        this.Hotdrinks.setVisible(false);
        this.Iceddrinks.setVisible(false);
        this.foods.setVisible(false);
        this.shakes.setVisible(false);
        this.hotdrinks.setVisible(false);
        this.colddrinks.setVisible(false);
        this.totalQuantity.setVisible(false);
        this.Receipt.setVisible(false);
    }  

        public void createTable() {
        // Replace these with your own MySQL login details
        String url = "jdbc:mysql://localhost:3306/receiptlist";
        String username = "root";
        String password = "";

        // Initialize the variables
        Connection connection = null;
        PreparedStatement statement = null;
        int tableName = Integer.parseInt(purchaseNumber.getText());

        try {
          // Load the MySQL driver
          Class.forName("com.mysql.cj.jdbc.Driver");

          // Connect to the database
          connection = DriverManager.getConnection(url,username,password);

          // Create the table
          String createTableSQL = "CREATE TABLE IF NOT EXISTS " + "purchase_id_" + tableName + " (Item_ID VARCHAR(100), Product_Name VARCHAR(100), Quantity VARCHAR(100), Item_Price VARCHAR(100),Total_Tax VARCHAR(100),Total_Price VARCHAR(100), Cash VARCHAR(100), Cash_Change VARCHAR(100), Total_Quantity VARCHAR(100),Time VARCHAR(100), Date VARCHAR(100))";
          statement = connection.prepareStatement(createTableSQL);
          statement.executeUpdate();

          JOptionPane.showMessageDialog(null, "Save Successfuly!", "Save", JOptionPane.INFORMATION_MESSAGE);

              DefaultTableModel df = (DefaultTableModel) Table1.getModel();
            for (int i = 0; i < Table1.getRowCount(); i++) {

                    String ID = df.getValueAt(i, 0).toString();
                    String ProductName = df.getValueAt(i, 1).toString();
                    String Quantity = df.getValueAt(i, 2).toString();
                    String ItemPrice = df.getValueAt(i, 3).toString();
                    String TaxAmount = tax.getText();
                    String TotalAmount = total.getText();
                    String CashAmount = cash.getText();
                    String ChangeAmount = change.getText();
                    String time = Time.getText();
                    String date = Date.getText();
                    String totalqty = totalQuantity.getText();
            try {
                Statement s = InsertData.mycon().createStatement();
                s.executeUpdate("INSERT INTO " + "purchase_id_" + tableName + " (Item_ID,Product_Name,Quantity,Item_Price,Total_Tax,Total_Price,Cash,Cash_Change,Total_Quantity,Time,Date) VALUES ('"+ID+"','"+ProductName+"','"+Quantity+"','"+ItemPrice+"','"+TaxAmount+"','"+TotalAmount+"','"+CashAmount+"','"+ChangeAmount+"','"+totalqty+"','"+time+"','"+date+"')");
        }catch (Exception e){
                System.out.println(e);
        }
            }
                //Clear order list and total
                cash.setText("");
                change.setText("");
                total.setText("");
                tax.setText("");
                purchaseNumber.setText("");
                DefaultTableModel dm = (DefaultTableModel)Table1.getModel();
                while(dm.getRowCount() > 0)
                {
                    dm.removeRow(0);
                }
        } catch (ClassNotFoundException e) {
          JOptionPane.showMessageDialog(null, "MYSQL Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
          JOptionPane.showMessageDialog(null, "Error occurred while creating the table", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
          // Close the connection and statement
          try {
            if (statement != null) {
              statement.close();
            }
            if (connection != null) {
              connection.close();
            }
          } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while closing the connection and statement", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }




        


    
    public void addtable(int id, String Name, int Qty, int Price) {
    
        DefaultTableModel dt = (DefaultTableModel) Table1.getModel();
        
        DecimalFormat df = new DecimalFormat("00.00");
        double totPrice = Price * Double.valueOf(Qty);
        String TotalPrice = df.format(totPrice);
        
        for (int row = 0; row < Table1.getRowCount(); row++) {
            
            if (Name == Table1.getValueAt(row, 1)) {
                
                dt.removeRow(Table1.convertRowIndexToModel(row));
            }
        }
        
        Vector v = new Vector();
        
        v.add(id);
        v.add(Name); 
        v.add(Qty);
        v.add(TotalPrice);
        
        dt.addRow(v);
    }
  
    
    
    public void cal(){
    int numOfRow = Table1.getRowCount();
    double tot = 0.0;
    for (int i = 0; i < numOfRow; i++) {
        double value = Double.valueOf(Table1.getValueAt(i, 3).toString());
        tot += value;
    }
    double taxRate = getTax(tot);
    double taxAmount = tot * taxRate;
    DecimalFormat df = new DecimalFormat("#,###.##");
    total.setText(df.format(tot + taxAmount));
    tax.setText(df.format(taxRate * 100) + "%");
    }

    public double getTax(double income) {
    if (income < 10000) {
        return 0.05;
    } else if (income < 25000) {
        return 0.10;
    } else if (income < 50000) {
        return 0.12;
    } else if (income < 75000) {
        return 0.12;
    } else if (income < 100000) {
        return 0.12;
    } else {
        return 0.12;
    }
    }

    public void setTime(){
        new Thread (new Runnable() {
            @Override
            public void run() {
               while (true){
                try {
                    Thread.sleep(0);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DearDoughnut.class.getName()).log(Level.SEVERE, null, ex);
                }
                Date date = new Date();
                SimpleDateFormat tf = new SimpleDateFormat("h:mm:ss aa");
                SimpleDateFormat df = new SimpleDateFormat("EEEE, dd-MM-yyyy");
                String time = tf.format(date);
                Time.setText(time.split(" ")[0]+" "+time.split(" ")[1]);
                Date.setText(df.format(date));
            }
            }
        
    }).start();
                }
    
    public void DearDoughtnut(){
    receiptarea.setText("	\n" +
"    	     Dear Doughnut\n" +
"            Asian Institute of Computer Studies\n" +
"  X538+355, Sabang, Gen. Luna St, Lipa, Batangas,\n" +
"	         Philippines\n" +
"\n"+
"                         Purchase ID: " + purchaseNumber.getText() + "\n"+
" \n"+
"                                " + Time.getText() + "\n" +
"                         " + Date.getText() + "\n" +
"---------------------------------------------------------------\n"+
"  Item \t        Qty \t               Price"+"\n");
    
}
    

    

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        title = new javax.swing.JLabel();
        Minimize = new javax.swing.JButton();
        Exit = new javax.swing.JButton();
        Tab = new javax.swing.JLabel();
        MainPanel = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        Order = new javax.swing.JPanel();
        orderlist = new javax.swing.JLabel();
        administrator1 = new javax.swing.JLabel();
        Date = new javax.swing.JLabel();
        Time = new javax.swing.JLabel();
        Label1 = new javax.swing.JLabel();
        Scrollpane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        clearlist = new javax.swing.JButton();
        clearall = new javax.swing.JButton();
        purchaseNumber = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        totalQuantity = new javax.swing.JLabel();
        Receipt = new javax.swing.JPanel();
        print = new javax.swing.JButton();
        back = new javax.swing.JButton();
        Scrollpane2 = new javax.swing.JScrollPane();
        receiptarea = new javax.swing.JTextArea();
        administrator2 = new javax.swing.JLabel();
        Label2 = new javax.swing.JLabel();
        Time2 = new javax.swing.JLabel();
        Date2 = new javax.swing.JLabel();
        calculator = new javax.swing.JPanel();
        receipt = new javax.swing.JButton();
        cash = new javax.swing.JTextField();
        total = new javax.swing.JLabel();
        change = new javax.swing.JLabel();
        tax = new javax.swing.JLabel();
        TaxJLabel = new javax.swing.JLabel();
        ChangeJLabel = new javax.swing.JLabel();
        CashJLabel = new javax.swing.JLabel();
        TotalJLabel = new javax.swing.JLabel();
        pay = new javax.swing.JButton();
        clearcash = new javax.swing.JButton();
        colddrinks = new javax.swing.JButton();
        hotdrinks = new javax.swing.JButton();
        shakes = new javax.swing.JButton();
        foods = new javax.swing.JButton();
        home = new javax.swing.JButton();
        Logout = new javax.swing.JButton();
        sales = new javax.swing.JButton();
        menu = new javax.swing.JButton();
        Tab3 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        Home = new javax.swing.JPanel();
        Donuts = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        AAAAAAAa1 = new javax.swing.JLabel();
        perpiece = new javax.swing.JButton();
        halfdozen = new javax.swing.JButton();
        dozen = new javax.swing.JButton();
        PerPieceScrollPane = new javax.swing.JScrollPane();
        PerPiece = new javax.swing.JPanel();
        classicdonuts = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        shadow6 = new javax.swing.JPanel();
        price5 = new javax.swing.JLabel();
        donut6 = new javax.swing.JButton();
        d6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        shadow2 = new javax.swing.JPanel();
        price1 = new javax.swing.JLabel();
        donut2 = new javax.swing.JButton();
        d2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        shadow1 = new javax.swing.JPanel();
        price = new javax.swing.JLabel();
        donut1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        d1 = new javax.swing.JLabel();
        shadow3 = new javax.swing.JPanel();
        price2 = new javax.swing.JLabel();
        donut3 = new javax.swing.JButton();
        d3 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        shadow8 = new javax.swing.JPanel();
        price7 = new javax.swing.JLabel();
        donut8 = new javax.swing.JButton();
        d8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        shadow7 = new javax.swing.JPanel();
        price6 = new javax.swing.JLabel();
        donut7 = new javax.swing.JButton();
        d7 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        shadow10 = new javax.swing.JPanel();
        price3 = new javax.swing.JLabel();
        donut10 = new javax.swing.JButton();
        d10 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        shadow12 = new javax.swing.JPanel();
        price4 = new javax.swing.JLabel();
        donut12 = new javax.swing.JButton();
        d12 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        premiumdonuts = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        shadow9 = new javax.swing.JPanel();
        price8 = new javax.swing.JLabel();
        donut9 = new javax.swing.JButton();
        d9 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        shadow11 = new javax.swing.JPanel();
        price11 = new javax.swing.JLabel();
        donut11 = new javax.swing.JButton();
        d11 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        shadow16 = new javax.swing.JPanel();
        price15 = new javax.swing.JLabel();
        donut16 = new javax.swing.JButton();
        d16 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        shadow15 = new javax.swing.JPanel();
        price14 = new javax.swing.JLabel();
        donut15 = new javax.swing.JButton();
        d15 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        shadow14 = new javax.swing.JPanel();
        price13 = new javax.swing.JLabel();
        donut14 = new javax.swing.JButton();
        d14 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        shadow13 = new javax.swing.JPanel();
        price12 = new javax.swing.JLabel();
        donut13 = new javax.swing.JButton();
        d13 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        shadow4 = new javax.swing.JPanel();
        price9 = new javax.swing.JLabel();
        donut4 = new javax.swing.JButton();
        d4 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        shadow5 = new javax.swing.JPanel();
        price10 = new javax.swing.JLabel();
        donut5 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        d5 = new javax.swing.JLabel();
        HalfDozenScrollPane = new javax.swing.JScrollPane();
        HalfDozen = new javax.swing.JPanel();
        halfdozenpanel1 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        price16 = new javax.swing.JLabel();
        halfdozenbutton1 = new javax.swing.JButton();
        halfdozen1 = new javax.swing.JLabel();
        halfdozenpanel2 = new javax.swing.JPanel();
        price17 = new javax.swing.JLabel();
        halfdozenbutton2 = new javax.swing.JButton();
        jLabel66 = new javax.swing.JLabel();
        halfdozen2 = new javax.swing.JLabel();
        DozenScrollPane = new javax.swing.JScrollPane();
        Dozen = new javax.swing.JPanel();
        dozenpanel1 = new javax.swing.JPanel();
        price18 = new javax.swing.JLabel();
        dozenbutton1 = new javax.swing.JButton();
        jLabel67 = new javax.swing.JLabel();
        dozen1 = new javax.swing.JLabel();
        dozenpanel2 = new javax.swing.JPanel();
        price19 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        dozenbutton2 = new javax.swing.JButton();
        dozen2 = new javax.swing.JLabel();
        Shakes = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        AAAAAAAa2 = new javax.swing.JLabel();
        ScrollPane3 = new javax.swing.JScrollPane();
        ShakesScrollPane = new javax.swing.JPanel();
        shadow17 = new javax.swing.JPanel();
        price20 = new javax.swing.JLabel();
        shake1 = new javax.swing.JButton();
        s1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        shadow18 = new javax.swing.JPanel();
        price21 = new javax.swing.JLabel();
        shake2 = new javax.swing.JButton();
        s2 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        shadow19 = new javax.swing.JPanel();
        price30 = new javax.swing.JLabel();
        shake11 = new javax.swing.JButton();
        s11 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        shadow20 = new javax.swing.JPanel();
        price23 = new javax.swing.JLabel();
        shake4 = new javax.swing.JButton();
        s4 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        shadow21 = new javax.swing.JPanel();
        price24 = new javax.swing.JLabel();
        shake5 = new javax.swing.JButton();
        s5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        shadow22 = new javax.swing.JPanel();
        price25 = new javax.swing.JLabel();
        shake6 = new javax.swing.JButton();
        s6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        shadow23 = new javax.swing.JPanel();
        price34 = new javax.swing.JLabel();
        shake15 = new javax.swing.JButton();
        s15 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        shadow24 = new javax.swing.JPanel();
        price27 = new javax.swing.JLabel();
        shake8 = new javax.swing.JButton();
        s8 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        shadow25 = new javax.swing.JPanel();
        price28 = new javax.swing.JLabel();
        shake9 = new javax.swing.JButton();
        s9 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        shadow26 = new javax.swing.JPanel();
        price29 = new javax.swing.JLabel();
        shake10 = new javax.swing.JButton();
        s10 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        shadow27 = new javax.swing.JPanel();
        price22 = new javax.swing.JLabel();
        shake3 = new javax.swing.JButton();
        s3 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        shadow31 = new javax.swing.JPanel();
        price26 = new javax.swing.JLabel();
        shake7 = new javax.swing.JButton();
        s7 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        shadow28 = new javax.swing.JPanel();
        price31 = new javax.swing.JLabel();
        s = new javax.swing.JButton();
        s12 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        shadow29 = new javax.swing.JPanel();
        price32 = new javax.swing.JLabel();
        shake13 = new javax.swing.JButton();
        s13 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        shadow30 = new javax.swing.JPanel();
        price33 = new javax.swing.JLabel();
        shake14 = new javax.swing.JButton();
        s14 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        shadow32 = new javax.swing.JPanel();
        price35 = new javax.swing.JLabel();
        shake16 = new javax.swing.JButton();
        s16 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        Hotdrinks = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        AAAAAAAa3 = new javax.swing.JLabel();
        shadow33 = new javax.swing.JPanel();
        price36 = new javax.swing.JLabel();
        hotdrink1 = new javax.swing.JButton();
        h1 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        shadow34 = new javax.swing.JPanel();
        price37 = new javax.swing.JLabel();
        hotdrink2 = new javax.swing.JButton();
        h2 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        shadow35 = new javax.swing.JPanel();
        price38 = new javax.swing.JLabel();
        hotdrink3 = new javax.swing.JButton();
        h3 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        shadow36 = new javax.swing.JPanel();
        price39 = new javax.swing.JLabel();
        hotdrink4 = new javax.swing.JButton();
        h4 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        shadow37 = new javax.swing.JPanel();
        price40 = new javax.swing.JLabel();
        hotdrink5 = new javax.swing.JButton();
        h5 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        shadow38 = new javax.swing.JPanel();
        price41 = new javax.swing.JLabel();
        hotdrink6 = new javax.swing.JButton();
        h6 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        shadow39 = new javax.swing.JPanel();
        price42 = new javax.swing.JLabel();
        hotdrink7 = new javax.swing.JButton();
        h7 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        shadow40 = new javax.swing.JPanel();
        price43 = new javax.swing.JLabel();
        hotdrink8 = new javax.swing.JButton();
        h8 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        Iceddrinks = new javax.swing.JPanel();
        shadow49 = new javax.swing.JPanel();
        price44 = new javax.swing.JLabel();
        icedrink1 = new javax.swing.JButton();
        i1 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        shadow50 = new javax.swing.JPanel();
        price45 = new javax.swing.JLabel();
        icedrink2 = new javax.swing.JButton();
        i2 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        shadow51 = new javax.swing.JPanel();
        price46 = new javax.swing.JLabel();
        icedrink3 = new javax.swing.JButton();
        i3 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        shadow52 = new javax.swing.JPanel();
        price47 = new javax.swing.JLabel();
        icedrink4 = new javax.swing.JButton();
        i4 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        shadow53 = new javax.swing.JPanel();
        price48 = new javax.swing.JLabel();
        icedrink5 = new javax.swing.JButton();
        i5 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        shadow54 = new javax.swing.JPanel();
        price49 = new javax.swing.JLabel();
        icedrink6 = new javax.swing.JButton();
        i6 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        shadow55 = new javax.swing.JPanel();
        price50 = new javax.swing.JLabel();
        icedrink7 = new javax.swing.JButton();
        i7 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        shadow56 = new javax.swing.JPanel();
        price51 = new javax.swing.JLabel();
        icedrink8 = new javax.swing.JButton();
        i8 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        shadow57 = new javax.swing.JPanel();
        price52 = new javax.swing.JLabel();
        icedrink9 = new javax.swing.JButton();
        i9 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        shadow58 = new javax.swing.JPanel();
        price53 = new javax.swing.JLabel();
        icedrink10 = new javax.swing.JButton();
        i10 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        shadow59 = new javax.swing.JPanel();
        price54 = new javax.swing.JLabel();
        icedrink11 = new javax.swing.JButton();
        i11 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        shadow60 = new javax.swing.JPanel();
        price55 = new javax.swing.JLabel();
        icedrink12 = new javax.swing.JButton();
        i12 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        AAAAAAAa4 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(140, 87, 71));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        title.setForeground(new java.awt.Color(232, 240, 217));
        title.setText("Point of Sale System");
        getContentPane().add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 180, 30));

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
        getContentPane().add(Minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 0, 30, 30));

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
        getContentPane().add(Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 0, 30, 30));

        Tab.setBackground(new java.awt.Color(89, 56, 46));
        Tab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        Tab.setOpaque(true);
        getContentPane().add(Tab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 30));

        MainPanel.setBackground(new java.awt.Color(108, 68, 56));
        MainPanel.setPreferredSize(new java.awt.Dimension(1099, 719));
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

        Order.setBackground(new java.awt.Color(108, 68, 56));
        Order.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        Order.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        orderlist.setFont(new java.awt.Font("Century Gothic", 2, 18)); // NOI18N
        orderlist.setForeground(new java.awt.Color(232, 240, 217));
        orderlist.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orderlist.setText("Order List");
        Order.add(orderlist, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 100, 30));

        administrator1.setFont(new java.awt.Font("Century Gothic", 2, 16)); // NOI18N
        administrator1.setForeground(new java.awt.Color(232, 240, 217));
        administrator1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        administrator1.setText("Administrator");
        Order.add(administrator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 30));

        Date.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        Date.setForeground(new java.awt.Color(232, 240, 217));
        Date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Date.setText("Sunday, 08-01-2023");
        Order.add(Date, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 20, 210, 30));

        Time.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        Time.setForeground(new java.awt.Color(232, 240, 217));
        Time.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Time.setText("12:00:00 AM");
        Order.add(Time, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 7, 110, 20));

        Label1.setBackground(new java.awt.Color(89, 56, 46));
        Label1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        Label1.setOpaque(true);
        Order.add(Label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 430, 80));

        Scrollpane1.setBackground(new java.awt.Color(108, 68, 56));
        Scrollpane1.setToolTipText("");
        Scrollpane1.setColumnHeaderView(jTable1);

        Table1.setBackground(new java.awt.Color(108, 68, 56));
        Table1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        Table1.setForeground(new java.awt.Color(232, 240, 217));
        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Name", "Qty", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table1.getTableHeader().setReorderingAllowed(false);
        Scrollpane1.setViewportView(Table1);
        if (Table1.getColumnModel().getColumnCount() > 0) {
            Table1.getColumnModel().getColumn(0).setResizable(false);
            Table1.getColumnModel().getColumn(1).setResizable(false);
            Table1.getColumnModel().getColumn(2).setResizable(false);
            Table1.getColumnModel().getColumn(3).setResizable(false);
        }

        Order.add(Scrollpane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 410, 410));

        clearlist.setBackground(new java.awt.Color(108, 68, 56));
        clearlist.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        clearlist.setForeground(new java.awt.Color(232, 240, 217));
        clearlist.setText("Select & Clear");
        clearlist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        clearlist.setRolloverEnabled(false);
        clearlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearlistActionPerformed(evt);
            }
        });
        Order.add(clearlist, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 490, 110, 30));

        clearall.setBackground(new java.awt.Color(108, 68, 56));
        clearall.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        clearall.setForeground(new java.awt.Color(232, 240, 217));
        clearall.setText("Clear All");
        clearall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        clearall.setRolloverEnabled(false);
        clearall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearallActionPerformed(evt);
            }
        });
        Order.add(clearall, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 490, 80, 30));

        purchaseNumber.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        purchaseNumber.setForeground(new java.awt.Color(232, 240, 217));
        purchaseNumber.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Order.add(purchaseNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 130, 30));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(232, 240, 217));
        jLabel4.setText("Purchase # :");
        Order.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 49, 110, 30));
        Order.add(totalQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 80, 30));

        jLayeredPane2.setLayer(Order, javax.swing.JLayeredPane.POPUP_LAYER);
        jLayeredPane2.add(Order);
        Order.setBounds(0, 0, 430, 530);

        Receipt.setBackground(new java.awt.Color(108, 68, 56));
        Receipt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        Receipt.setPreferredSize(new java.awt.Dimension(428, 690));
        Receipt.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        print.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        print.setForeground(new java.awt.Color(232, 240, 217));
        print.setText("Print");
        print.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        print.setContentAreaFilled(false);
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        Receipt.add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, 140, 40));

        back.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        back.setForeground(new java.awt.Color(232, 240, 217));
        back.setText("BACK");
        back.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        back.setContentAreaFilled(false);
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        Receipt.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 480, 140, 40));

        receiptarea.setColumns(20);
        receiptarea.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        receiptarea.setRows(5);
        Scrollpane2.setViewportView(receiptarea);

        Receipt.add(Scrollpane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 330, 410));

        administrator2.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        administrator2.setForeground(new java.awt.Color(232, 240, 217));
        administrator2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        administrator2.setText("Administrator");
        Receipt.add(administrator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 30));

        Label2.setBackground(new java.awt.Color(89, 56, 46));
        Label2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        Label2.setOpaque(true);
        Receipt.add(Label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 50));

        Time2.setFont(new java.awt.Font("Goudy Old Style", 1, 16)); // NOI18N
        Time2.setForeground(new java.awt.Color(232, 240, 217));
        Receipt.add(Time2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 90, 50));

        Date2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        Date2.setForeground(new java.awt.Color(232, 240, 217));
        Receipt.add(Date2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 130, 50));

        jLayeredPane2.setLayer(Receipt, javax.swing.JLayeredPane.POPUP_LAYER);
        jLayeredPane2.add(Receipt);
        Receipt.setBounds(0, 0, 430, 530);

        calculator.setBackground(new java.awt.Color(76, 48, 39));
        calculator.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        calculator.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        receipt.setBackground(new java.awt.Color(76, 48, 39));
        receipt.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        receipt.setForeground(new java.awt.Color(232, 240, 217));
        receipt.setText("Receipt");
        receipt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        receipt.setRolloverEnabled(false);
        receipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiptActionPerformed(evt);
            }
        });
        calculator.add(receipt, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 70));

        cash.setBackground(new java.awt.Color(76, 48, 39));
        cash.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        cash.setForeground(new java.awt.Color(232, 240, 217));
        cash.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cash.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 68, 56), 2));
        cash.setCaretColor(new java.awt.Color(232, 240, 217));
        cash.setDisabledTextColor(new java.awt.Color(232, 240, 217));
        calculator.add(cash, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 81, 147, 35));

        total.setBackground(new java.awt.Color(76, 48, 39));
        total.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        total.setForeground(new java.awt.Color(232, 240, 217));
        total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 68, 56), 2));
        total.setOpaque(true);
        calculator.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 43, 147, 35));

        change.setBackground(new java.awt.Color(76, 48, 39));
        change.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        change.setForeground(new java.awt.Color(232, 240, 217));
        change.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        change.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 68, 56), 2));
        change.setOpaque(true);
        calculator.add(change, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 119, 147, 35));

        tax.setBackground(new java.awt.Color(76, 48, 39));
        tax.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        tax.setForeground(new java.awt.Color(232, 240, 217));
        tax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tax.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 68, 56), 2));
        tax.setOpaque(true);
        calculator.add(tax, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 5, 147, 35));

        TaxJLabel.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        TaxJLabel.setForeground(new java.awt.Color(232, 240, 217));
        TaxJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TaxJLabel.setText("Tax:");
        calculator.add(TaxJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 50, 40));

        ChangeJLabel.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        ChangeJLabel.setForeground(new java.awt.Color(232, 240, 217));
        ChangeJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChangeJLabel.setText("Change:");
        calculator.add(ChangeJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 90, 30));

        CashJLabel.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        CashJLabel.setForeground(new java.awt.Color(232, 240, 217));
        CashJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CashJLabel.setText("Cash:");
        calculator.add(CashJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 60, 40));

        TotalJLabel.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        TotalJLabel.setForeground(new java.awt.Color(232, 240, 217));
        TotalJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TotalJLabel.setText("Total:");
        calculator.add(TotalJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 60, 40));

        pay.setBackground(new java.awt.Color(76, 48, 39));
        pay.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        pay.setForeground(new java.awt.Color(232, 240, 217));
        pay.setText("Pay");
        pay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        pay.setRolloverEnabled(false);
        pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payActionPerformed(evt);
            }
        });
        calculator.add(pay, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 68, 120, 90));

        clearcash.setBackground(new java.awt.Color(76, 48, 39));
        clearcash.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        clearcash.setForeground(new java.awt.Color(232, 240, 217));
        clearcash.setText("Clear");
        clearcash.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        clearcash.setContentAreaFilled(false);
        clearcash.setOpaque(true);
        clearcash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearcashActionPerformed(evt);
            }
        });
        calculator.add(clearcash, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 70, 160));

        jLayeredPane2.add(calculator);
        calculator.setBounds(0, 530, 430, 160);

        MainPanel.add(jLayeredPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 430, 690));

        colddrinks.setBackground(new java.awt.Color(89, 56, 46));
        colddrinks.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        colddrinks.setForeground(new java.awt.Color(232, 240, 217));
        colddrinks.setText("Iced Beverages");
        colddrinks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        colddrinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colddrinksActionPerformed(evt);
            }
        });
        MainPanel.add(colddrinks, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 120, 30));

        hotdrinks.setBackground(new java.awt.Color(89, 56, 46));
        hotdrinks.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        hotdrinks.setForeground(new java.awt.Color(232, 240, 217));
        hotdrinks.setText("Hot Beverages");
        hotdrinks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        hotdrinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotdrinksActionPerformed(evt);
            }
        });
        MainPanel.add(hotdrinks, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 40, 110, 30));

        shakes.setBackground(new java.awt.Color(89, 56, 46));
        shakes.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        shakes.setForeground(new java.awt.Color(232, 240, 217));
        shakes.setText("Shakes");
        shakes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shakes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shakesActionPerformed(evt);
            }
        });
        MainPanel.add(shakes, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 60, 30));

        foods.setBackground(new java.awt.Color(89, 56, 46));
        foods.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        foods.setForeground(new java.awt.Color(232, 240, 217));
        foods.setText("Donuts");
        foods.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        foods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodsActionPerformed(evt);
            }
        });
        MainPanel.add(foods, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 60, 30));

        home.setBackground(new java.awt.Color(89, 56, 46));
        home.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        home.setForeground(new java.awt.Color(232, 240, 217));
        home.setText("HOME");
        home.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        home.setRolloverEnabled(false);
        home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeActionPerformed(evt);
            }
        });
        MainPanel.add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 60, 30));

        Logout.setBackground(new java.awt.Color(89, 56, 46));
        Logout.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Logout.setForeground(new java.awt.Color(232, 240, 217));
        Logout.setText("Logout");
        Logout.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });
        MainPanel.add(Logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 40, 60, 30));

        sales.setBackground(new java.awt.Color(89, 56, 46));
        sales.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        sales.setForeground(new java.awt.Color(232, 240, 217));
        sales.setText("SALES");
        sales.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        sales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesActionPerformed(evt);
            }
        });
        MainPanel.add(sales, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 60, 30));

        menu.setBackground(new java.awt.Color(89, 56, 46));
        menu.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        menu.setForeground(new java.awt.Color(232, 240, 217));
        menu.setText("FOOD MENU");
        menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuActionPerformed(evt);
            }
        });
        MainPanel.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 100, 30));

        Tab3.setBackground(new java.awt.Color(89, 56, 46));
        Tab3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        Tab3.setOpaque(true);
        MainPanel.add(Tab3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 0, 680, 80));

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Home.setBackground(new java.awt.Color(108, 68, 56));
        Home.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 2));
        Home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jLayeredPane1.setLayer(Home, javax.swing.JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 680, 650));

        Donuts.setBackground(new java.awt.Color(108, 68, 56));
        Donuts.setFocusable(false);
        Donuts.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(232, 240, 217));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DONUTS");
        Donuts.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 40));

        AAAAAAAa1.setBackground(new java.awt.Color(89, 56, 46));
        AAAAAAAa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        AAAAAAAa1.setOpaque(true);
        Donuts.add(AAAAAAAa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 690, 50));

        perpiece.setBackground(new java.awt.Color(108, 68, 56));
        perpiece.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        perpiece.setForeground(new java.awt.Color(232, 240, 217));
        perpiece.setText("Per Piece");
        perpiece.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        perpiece.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                perpieceActionPerformed(evt);
            }
        });
        Donuts.add(perpiece, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 90, 30));

        halfdozen.setBackground(new java.awt.Color(108, 68, 56));
        halfdozen.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        halfdozen.setForeground(new java.awt.Color(232, 240, 217));
        halfdozen.setText("Half Dozen");
        halfdozen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        halfdozen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                halfdozenActionPerformed(evt);
            }
        });
        Donuts.add(halfdozen, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 90, 30));

        dozen.setBackground(new java.awt.Color(108, 68, 56));
        dozen.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        dozen.setForeground(new java.awt.Color(232, 240, 217));
        dozen.setText("Dozen");
        dozen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        dozen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dozenActionPerformed(evt);
            }
        });
        Donuts.add(dozen, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, 90, 30));

        PerPieceScrollPane.setBackground(new java.awt.Color(108, 68, 56));
        PerPieceScrollPane.setBorder(null);
        PerPieceScrollPane.setPreferredSize(new java.awt.Dimension(650, 560));

        PerPiece.setBackground(new java.awt.Color(108, 68, 56));
        PerPiece.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        classicdonuts.setBackground(new java.awt.Color(89, 56, 46));
        classicdonuts.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel63.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(232, 240, 217));
        jLabel63.setText("PER-PIECE CLASSIC");
        classicdonuts.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 200, 40));

        shadow6.setBackground(new java.awt.Color(76, 48, 40));
        shadow6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price5.setForeground(new java.awt.Color(232, 240, 217));
        price5.setText("25PHP");
        shadow6.add(price5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Chocolate Coconut.png"))); // NOI18N
        donut6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut6ActionPerformed(evt);
            }
        });
        shadow6.add(donut6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d6.setText("0");
        d6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow6.add(d6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(232, 240, 217));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Chocolate Coconut");
        shadow6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        classicdonuts.add(shadow6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, -1, 160));

        shadow2.setBackground(new java.awt.Color(76, 48, 40));
        shadow2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price1.setForeground(new java.awt.Color(232, 240, 217));
        price1.setText("25PHP");
        shadow2.add(price1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Apple n' Spice.png"))); // NOI18N
        donut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut2ActionPerformed(evt);
            }
        });
        shadow2.add(donut2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d2.setText("0");
        d2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow2.add(d2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 70, 20));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(232, 240, 217));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Apple n' Spice");
        shadow2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        classicdonuts.add(shadow2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 160));

        shadow1.setBackground(new java.awt.Color(76, 48, 40));
        shadow1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price.setForeground(new java.awt.Color(232, 240, 217));
        price.setText("25PHP");
        shadow1.add(price, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Glaze.png"))); // NOI18N
        donut1.setBorder(null);
        donut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut1ActionPerformed(evt);
            }
        });
        shadow1.add(donut1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(232, 240, 217));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Glaze");
        shadow1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        d1.setText("0");
        d1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow1.add(d1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

        classicdonuts.add(shadow1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 130, 160));

        shadow3.setBackground(new java.awt.Color(76, 48, 40));
        shadow3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price2.setForeground(new java.awt.Color(232, 240, 217));
        price2.setText("25PHP");
        shadow3.add(price2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Bavarian Kreme.png"))); // NOI18N
        donut3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut3ActionPerformed(evt);
            }
        });
        shadow3.add(donut3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d3.setText("0");
        d3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow3.add(d3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 70, 20));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(232, 240, 217));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Bavarian Kreme");
        shadow3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        classicdonuts.add(shadow3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 130, 160));

        shadow8.setBackground(new java.awt.Color(76, 48, 40));
        shadow8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price7.setForeground(new java.awt.Color(232, 240, 217));
        price7.setText("25PHP");
        shadow8.add(price7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Chocolate Glazed.png"))); // NOI18N
        donut8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut8ActionPerformed(evt);
            }
        });
        shadow8.add(donut8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d8.setText("0");
        d8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow8.add(d8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 70, 20));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(232, 240, 217));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Chocolate Glazed");
        shadow8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 130, 150, 30));

        classicdonuts.add(shadow8, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, 130, 160));

        shadow7.setBackground(new java.awt.Color(76, 48, 40));
        shadow7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price6.setForeground(new java.awt.Color(232, 240, 217));
        price6.setText("25PHP");
        shadow7.add(price6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Chocolate Frosted.png"))); // NOI18N
        donut7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut7ActionPerformed(evt);
            }
        });
        shadow7.add(donut7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d7.setText("0");
        d7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow7.add(d7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(232, 240, 217));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Chocolate Frosted");
        shadow7.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, 30));

        classicdonuts.add(shadow7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, 130, 160));

        shadow10.setBackground(new java.awt.Color(76, 48, 40));
        shadow10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price3.setForeground(new java.awt.Color(232, 240, 217));
        price3.setText("25PHP");
        shadow10.add(price3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Double Chocolate.png"))); // NOI18N
        donut10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut10ActionPerformed(evt);
            }
        });
        shadow10.add(donut10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d10.setText("0");
        d10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow10.add(d10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 70, 20));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(232, 240, 217));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Double Chocolate");
        shadow10.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        classicdonuts.add(shadow10, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, -1, 160));

        shadow12.setBackground(new java.awt.Color(76, 48, 40));
        shadow12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price4.setForeground(new java.awt.Color(232, 240, 217));
        price4.setText("25PHP");
        shadow12.add(price4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 60, 30));

        donut12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Jelly.png"))); // NOI18N
        donut12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut12ActionPerformed(evt);
            }
        });
        shadow12.add(donut12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d12.setText("0");
        d12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow12.add(d12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(232, 240, 217));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Jelly");
        shadow12.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        classicdonuts.add(shadow12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 130, 160));

        PerPiece.add(classicdonuts, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 640, 390));

        premiumdonuts.setBackground(new java.awt.Color(89, 56, 46));
        premiumdonuts.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel64.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(232, 240, 217));
        jLabel64.setText("PER-PIECE PREMIUM");
        premiumdonuts.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 200, 40));

        shadow9.setBackground(new java.awt.Color(76, 48, 40));
        shadow9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price8.setForeground(new java.awt.Color(232, 240, 217));
        price8.setText("40PHP");
        shadow9.add(price8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Chocolate Kreme.png"))); // NOI18N
        donut9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut9ActionPerformed(evt);
            }
        });
        shadow9.add(donut9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d9.setText("0");
        d9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow9.add(d9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 70, 20));

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(232, 240, 217));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Chocolate Kreme");
        shadow9.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        premiumdonuts.add(shadow9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 160));

        shadow11.setBackground(new java.awt.Color(76, 48, 40));
        shadow11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price11.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price11.setForeground(new java.awt.Color(232, 240, 217));
        price11.setText("40PHP");
        shadow11.add(price11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/French Cruller.png"))); // NOI18N
        donut11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut11ActionPerformed(evt);
            }
        });
        shadow11.add(donut11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d11.setText("0");
        d11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow11.add(d11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(232, 240, 217));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("French Cruller");
        shadow11.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        premiumdonuts.add(shadow11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 130, 160));

        shadow16.setBackground(new java.awt.Color(76, 48, 40));
        shadow16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price15.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price15.setForeground(new java.awt.Color(232, 240, 217));
        price15.setText("40PHP");
        shadow16.add(price15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Vanilla Kreme.png"))); // NOI18N
        donut16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut16ActionPerformed(evt);
            }
        });
        shadow16.add(donut16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d16.setText("0");
        d16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow16.add(d16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(232, 240, 217));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Vanilla Kreme");
        shadow16.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        premiumdonuts.add(shadow16, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 130, 160));

        shadow15.setBackground(new java.awt.Color(76, 48, 40));
        shadow15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price14.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price14.setForeground(new java.awt.Color(232, 240, 217));
        price14.setText("40PHP");
        shadow15.add(price14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Vanilla Frosted.png"))); // NOI18N
        donut15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut15ActionPerformed(evt);
            }
        });
        shadow15.add(donut15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d15.setText("0");
        d15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow15.add(d15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(232, 240, 217));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Vanilla Frosted");
        shadow15.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        premiumdonuts.add(shadow15, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 210, 130, 160));

        shadow14.setBackground(new java.awt.Color(76, 48, 40));
        shadow14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price13.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price13.setForeground(new java.awt.Color(232, 240, 217));
        price13.setText("40PHP");
        shadow14.add(price13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 60, 30));

        donut14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Strawberry Frosted.png"))); // NOI18N
        donut14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut14ActionPerformed(evt);
            }
        });
        shadow14.add(donut14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d14.setText("0");
        d14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow14.add(d14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel26.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(232, 240, 217));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Strawberry Frosted");
        shadow14.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        premiumdonuts.add(shadow14, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 130, 160));

        shadow13.setBackground(new java.awt.Color(76, 48, 40));
        shadow13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price12.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price12.setForeground(new java.awt.Color(232, 240, 217));
        price12.setText("40PHP");
        shadow13.add(price12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Mapple Frosted.png"))); // NOI18N
        donut13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut13ActionPerformed(evt);
            }
        });
        shadow13.add(donut13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d13.setText("0");
        d13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow13.add(d13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 70, 20));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(232, 240, 217));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Mapple Frosted");
        shadow13.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        premiumdonuts.add(shadow13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, 160));

        shadow4.setBackground(new java.awt.Color(76, 48, 40));
        shadow4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price9.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price9.setForeground(new java.awt.Color(232, 240, 217));
        price9.setText("40PHP");
        shadow4.add(price9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Boston Kreme.png"))); // NOI18N
        donut4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut4ActionPerformed(evt);
            }
        });
        shadow4.add(donut4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        d4.setText("0");
        d4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow4.add(d4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(232, 240, 217));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Boston Kreme");
        shadow4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        premiumdonuts.add(shadow4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 160));

        shadow5.setBackground(new java.awt.Color(76, 48, 40));
        shadow5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price10.setForeground(new java.awt.Color(232, 240, 217));
        price10.setText("40PHP");
        shadow5.add(price10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 30));

        donut5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Butternut.png"))); // NOI18N
        donut5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donut5ActionPerformed(evt);
            }
        });
        shadow5.add(donut5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(232, 240, 217));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Butternut");
        shadow5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        d5.setText("0");
        d5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow5.add(d5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        premiumdonuts.add(shadow5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 130, 160));

        PerPiece.add(premiumdonuts, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 640, 380));

        PerPieceScrollPane.setViewportView(PerPiece);

        Donuts.add(PerPieceScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 670, 550));

        HalfDozenScrollPane.setBorder(null);

        HalfDozen.setBackground(new java.awt.Color(108, 68, 56));
        HalfDozen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        halfdozenpanel1.setBackground(new java.awt.Color(89, 56, 46));
        halfdozenpanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel65.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(232, 240, 217));
        jLabel65.setText("HALF DOZEN CLASSIC");
        halfdozenpanel1.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, -10, 220, 50));

        price16.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        price16.setForeground(new java.awt.Color(232, 240, 217));
        price16.setText("150PHP");
        halfdozenpanel1.add(price16, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 90, 30));

        halfdozenbutton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Half Dozen Classic.png"))); // NOI18N
        halfdozenbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                halfdozenbutton1ActionPerformed(evt);
            }
        });
        halfdozenpanel1.add(halfdozenbutton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 450, 210));

        halfdozen1.setText("0");
        halfdozenpanel1.add(halfdozen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 30, 20));

        HalfDozen.add(halfdozenpanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 570, 280));

        halfdozenpanel2.setBackground(new java.awt.Color(89, 56, 46));
        halfdozenpanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price17.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        price17.setForeground(new java.awt.Color(232, 240, 217));
        price17.setText("240PHP");
        halfdozenpanel2.add(price17, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 90, -1));

        halfdozenbutton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Half Dozen Premium.png"))); // NOI18N
        halfdozenbutton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                halfdozenbutton2ActionPerformed(evt);
            }
        });
        halfdozenpanel2.add(halfdozenbutton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 450, 210));

        jLabel66.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(232, 240, 217));
        jLabel66.setText("HALF DOZEN PREMIUM");
        halfdozenpanel2.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 220, 40));

        halfdozen2.setText("0");
        halfdozenpanel2.add(halfdozen2, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 156, 40, 20));

        HalfDozen.add(halfdozenpanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 570, 280));

        HalfDozenScrollPane.setViewportView(HalfDozen);

        Donuts.add(HalfDozenScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 670, 540));

        DozenScrollPane.setBorder(null);

        Dozen.setBackground(new java.awt.Color(108, 68, 56));
        Dozen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dozenpanel1.setBackground(new java.awt.Color(89, 56, 46));
        dozenpanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price18.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        price18.setForeground(new java.awt.Color(232, 240, 217));
        price18.setText("300PHP");
        dozenpanel1.add(price18, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 90, 30));

        dozenbutton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Dozen.png"))); // NOI18N
        dozenbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dozenbutton1ActionPerformed(evt);
            }
        });
        dozenpanel1.add(dozenbutton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 450, 210));

        jLabel67.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(232, 240, 217));
        jLabel67.setText("DOZEN CLASSIC PACK");
        dozenpanel1.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, -10, 210, 50));

        dozen1.setText("0");
        dozenpanel1.add(dozen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));

        Dozen.add(dozenpanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 570, 280));

        dozenpanel2.setBackground(new java.awt.Color(89, 56, 46));
        dozenpanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price19.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        price19.setForeground(new java.awt.Color(232, 240, 217));
        price19.setText("480PHP");
        dozenpanel2.add(price19, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 90, 30));

        jLabel68.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(232, 240, 217));
        jLabel68.setText("DOZEN PREMIUM PACK");
        dozenpanel2.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, -10, 210, 50));

        dozenbutton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Dozen.png"))); // NOI18N
        dozenbutton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dozenbutton2ActionPerformed(evt);
            }
        });
        dozenpanel2.add(dozenbutton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 450, 210));

        dozen2.setText("0");
        dozenpanel2.add(dozen2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, -1, -1));

        Dozen.add(dozenpanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 570, 280));

        DozenScrollPane.setViewportView(Dozen);

        Donuts.add(DozenScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 670, 540));

        jLayeredPane1.setLayer(Donuts, javax.swing.JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(Donuts, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 640));

        Shakes.setBackground(new java.awt.Color(108, 68, 56));
        Shakes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217), 3));
        Shakes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(232, 240, 217));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SHAKES");
        Shakes.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 40));

        AAAAAAAa2.setBackground(new java.awt.Color(89, 56, 46));
        AAAAAAAa2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        AAAAAAAa2.setOpaque(true);
        Shakes.add(AAAAAAAa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 690, 50));

        ShakesScrollPane.setBackground(new java.awt.Color(108, 68, 56));
        ShakesScrollPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        shadow17.setBackground(new java.awt.Color(89, 56, 46));
        shadow17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price20.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price20.setForeground(new java.awt.Color(232, 240, 217));
        price20.setText("110PHP");
        shadow17.add(price20, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Jamocha.png"))); // NOI18N
        shake1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake1ActionPerformed(evt);
            }
        });
        shadow17.add(shake1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s1.setText("0");
        shadow17.add(s1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 60, 30));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(232, 240, 217));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Jamocha [M]");
        shadow17.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        ShakesScrollPane.add(shadow17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 130, 160));

        shadow18.setBackground(new java.awt.Color(89, 56, 46));
        shadow18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price21.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price21.setForeground(new java.awt.Color(232, 240, 217));
        price21.setText("110PHP");
        shadow18.add(price21, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Oreo Chocolate.png"))); // NOI18N
        shake2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake2ActionPerformed(evt);
            }
        });
        shadow18.add(shake2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s2.setText("0");
        shadow18.add(s2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 60, 30));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(232, 240, 217));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Oreo Choco [M]");
        shadow18.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow18, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 130, 160));

        shadow19.setBackground(new java.awt.Color(89, 56, 46));
        shadow19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price30.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price30.setForeground(new java.awt.Color(232, 240, 217));
        price30.setText("90PHP");
        shadow19.add(price30, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        shake11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Peach Milkshake.png"))); // NOI18N
        shake11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake11ActionPerformed(evt);
            }
        });
        shadow19.add(shake11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s11.setText("0");
        shadow19.add(s11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 60, 30));

        jLabel31.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(232, 240, 217));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Peach MilkShake [M]");
        shadow19.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow19, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 130, 160));

        shadow20.setBackground(new java.awt.Color(89, 56, 46));
        shadow20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price23.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price23.setForeground(new java.awt.Color(232, 240, 217));
        price23.setText("110PHP");
        shadow20.add(price23, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Vanilla Ice Cream.png"))); // NOI18N
        shake4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake4ActionPerformed(evt);
            }
        });
        shadow20.add(shake4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s4.setText("0");
        shadow20.add(s4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 60, 30));

        jLabel29.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(232, 240, 217));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Vanilla Ice Cream [M]");
        shadow20.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow20, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 130, 160));

        shadow21.setBackground(new java.awt.Color(89, 56, 46));
        shadow21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price24.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price24.setForeground(new java.awt.Color(232, 240, 217));
        price24.setText("125PHP");
        shadow21.add(price24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 70, 30));

        shake5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Jamocha.png"))); // NOI18N
        shake5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake5ActionPerformed(evt);
            }
        });
        shadow21.add(shake5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s5.setText("0");
        shadow21.add(s5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 60, 30));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(232, 240, 217));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Jamocha [L]");
        shadow21.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        ShakesScrollPane.add(shadow21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 130, 160));

        shadow22.setBackground(new java.awt.Color(89, 56, 46));
        shadow22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price25.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price25.setForeground(new java.awt.Color(232, 240, 217));
        price25.setText("125PHP");
        shadow22.add(price25, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Oreo Chocolate.png"))); // NOI18N
        shake6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake6ActionPerformed(evt);
            }
        });
        shadow22.add(shake6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s6.setText("0");
        shadow22.add(s6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 60, 30));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(232, 240, 217));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Oreo Choco [L]");
        shadow22.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow22, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 130, 160));

        shadow23.setBackground(new java.awt.Color(89, 56, 46));
        shadow23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price34.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price34.setForeground(new java.awt.Color(232, 240, 217));
        price34.setText("105PHP");
        shadow23.add(price34, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Peach Milkshake.png"))); // NOI18N
        shake15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake15ActionPerformed(evt);
            }
        });
        shadow23.add(shake15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s15.setText("0");
        shadow23.add(s15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 60, 30));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(232, 240, 217));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Peach MilkShake [L]");
        shadow23.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow23, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 520, 130, 160));

        shadow24.setBackground(new java.awt.Color(89, 56, 46));
        shadow24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price27.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price27.setForeground(new java.awt.Color(232, 240, 217));
        price27.setText("125PHP");
        shadow24.add(price27, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Vanilla Ice Cream.png"))); // NOI18N
        shake8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake8ActionPerformed(evt);
            }
        });
        shadow24.add(shake8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s8.setText("0");
        shadow24.add(s8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 60, 30));

        jLabel32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(232, 240, 217));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Vanilla Ice Cream [L]");
        shadow24.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow24, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 130, 160));

        shadow25.setBackground(new java.awt.Color(89, 56, 46));
        shadow25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price28.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price28.setForeground(new java.awt.Color(232, 240, 217));
        price28.setText("110PHP");
        shadow25.add(price28, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Vanilla Shake.png"))); // NOI18N
        shake9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake9ActionPerformed(evt);
            }
        });
        shadow25.add(shake9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s9.setText("0");
        shadow25.add(s9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 60, 30));

        jLabel33.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(232, 240, 217));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Vanilla Shake [M]");
        shadow25.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 130, 160));

        shadow26.setBackground(new java.awt.Color(89, 56, 46));
        shadow26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price29.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price29.setForeground(new java.awt.Color(232, 240, 217));
        price29.setText("110PHP");
        shadow26.add(price29, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Caramel Cinnamon.png"))); // NOI18N
        shake10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake10ActionPerformed(evt);
            }
        });
        shadow26.add(shake10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s10.setText("0");
        shadow26.add(s10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 60, 30));

        jLabel35.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(232, 240, 217));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Caramel Cinnamon [M]");
        shadow26.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow26, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 130, 160));

        shadow27.setBackground(new java.awt.Color(89, 56, 46));
        shadow27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price22.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price22.setForeground(new java.awt.Color(232, 240, 217));
        price22.setText("90PHP");
        shadow27.add(price22, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        shake3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Mango MilkShake.jpg"))); // NOI18N
        shake3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake3ActionPerformed(evt);
            }
        });
        shadow27.add(shake3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s3.setText("0");
        shadow27.add(s3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 60, 30));

        jLabel37.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(232, 240, 217));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Sweet  Mango [M]");
        shadow27.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow27, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 130, 160));

        shadow31.setBackground(new java.awt.Color(89, 56, 46));
        shadow31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price26.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price26.setForeground(new java.awt.Color(232, 240, 217));
        price26.setText("105PHP");
        shadow31.add(price26, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Mango MilkShake.jpg"))); // NOI18N
        shake7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake7ActionPerformed(evt);
            }
        });
        shadow31.add(shake7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s7.setText("0");
        shadow31.add(s7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 60, 30));

        jLabel38.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(232, 240, 217));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Sweet Mango [L]");
        shadow31.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow31, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, 130, 160));

        shadow28.setBackground(new java.awt.Color(89, 56, 46));
        shadow28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price31.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price31.setForeground(new java.awt.Color(232, 240, 217));
        price31.setText("90PHP");
        shadow28.add(price31, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        s.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Chocolate Frape Shake.png"))); // NOI18N
        s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sActionPerformed(evt);
            }
        });
        shadow28.add(s, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s12.setText("0");
        shadow28.add(s12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 60, 30));

        jLabel39.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(232, 240, 217));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Chocolate Frappe [M]");
        shadow28.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow28, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 350, 130, 160));

        shadow29.setBackground(new java.awt.Color(89, 56, 46));
        shadow29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price32.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price32.setForeground(new java.awt.Color(232, 240, 217));
        price32.setText("125PHP");
        shadow29.add(price32, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Vanilla Shake.png"))); // NOI18N
        shake13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake13ActionPerformed(evt);
            }
        });
        shadow29.add(shake13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s13.setText("0");
        shadow29.add(s13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 60, 30));

        jLabel34.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(232, 240, 217));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Vanilla Shake [L]");
        shadow29.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, 130, 160));

        shadow30.setBackground(new java.awt.Color(89, 56, 46));
        shadow30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price33.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price33.setForeground(new java.awt.Color(232, 240, 217));
        price33.setText("125PHP");
        shadow30.add(price33, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Caramel Cinnamon.png"))); // NOI18N
        shake14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake14ActionPerformed(evt);
            }
        });
        shadow30.add(shake14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s14.setText("0");
        shadow30.add(s14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 60, 30));

        jLabel36.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(232, 240, 217));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Caramel Cinnamon [L]");
        shadow30.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow30, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 520, 130, 160));

        shadow32.setBackground(new java.awt.Color(89, 56, 46));
        shadow32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price35.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price35.setForeground(new java.awt.Color(232, 240, 217));
        price35.setText("105PHP");
        shadow32.add(price35, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        shake16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Chocolate Frape Shake.png"))); // NOI18N
        shake16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shake16ActionPerformed(evt);
            }
        });
        shadow32.add(shake16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        s16.setText("0");
        shadow32.add(s16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 60, 30));

        jLabel40.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(232, 240, 217));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Chocolate Frappe [L]");
        shadow32.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        ShakesScrollPane.add(shadow32, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 520, 130, 160));

        ScrollPane3.setViewportView(ShakesScrollPane);

        Shakes.add(ScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 670, 600));

        jLayeredPane1.setLayer(Shakes, javax.swing.JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(Shakes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 640));

        Hotdrinks.setBackground(new java.awt.Color(108, 68, 56));
        Hotdrinks.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Goudy Old Style", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(232, 240, 217));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("HOT BEVERAGES");
        Hotdrinks.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 670, 40));

        AAAAAAAa3.setBackground(new java.awt.Color(89, 56, 46));
        AAAAAAAa3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        AAAAAAAa3.setOpaque(true);
        Hotdrinks.add(AAAAAAAa3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 690, 50));

        shadow33.setBackground(new java.awt.Color(89, 56, 46));
        shadow33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price36.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price36.setForeground(new java.awt.Color(232, 240, 217));
        price36.setText("90PHP");
        shadow33.add(price36, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        hotdrink1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Latte.png"))); // NOI18N
        hotdrink1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotdrink1ActionPerformed(evt);
            }
        });
        shadow33.add(hotdrink1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        h1.setText("0");
        h1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow33.add(h1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

        jLabel41.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(232, 240, 217));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Latte");
        shadow33.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        Hotdrinks.add(shadow33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 130, 160));

        shadow34.setBackground(new java.awt.Color(89, 56, 46));
        shadow34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price37.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price37.setForeground(new java.awt.Color(232, 240, 217));
        price37.setText("90PHP");
        shadow34.add(price37, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        hotdrink2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cappuccino.png"))); // NOI18N
        hotdrink2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotdrink2ActionPerformed(evt);
            }
        });
        shadow34.add(hotdrink2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        h2.setText("0");
        h2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow34.add(h2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 70, 20));

        jLabel42.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(232, 240, 217));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Cappuccino");
        shadow34.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        Hotdrinks.add(shadow34, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 130, 160));

        shadow35.setBackground(new java.awt.Color(89, 56, 46));
        shadow35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price38.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price38.setForeground(new java.awt.Color(232, 240, 217));
        price38.setText("60PHP");
        shadow35.add(price38, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        hotdrink3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Espresso.png"))); // NOI18N
        hotdrink3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotdrink3ActionPerformed(evt);
            }
        });
        shadow35.add(hotdrink3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        h3.setText("0");
        h3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow35.add(h3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 70, 20));

        jLabel43.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(232, 240, 217));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Espresso [M]");
        shadow35.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        Hotdrinks.add(shadow35, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 130, 160));

        shadow36.setBackground(new java.awt.Color(89, 56, 46));
        shadow36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price39.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price39.setForeground(new java.awt.Color(232, 240, 217));
        price39.setText("75PHP");
        shadow36.add(price39, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        hotdrink4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Espresso.png"))); // NOI18N
        hotdrink4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotdrink4ActionPerformed(evt);
            }
        });
        shadow36.add(hotdrink4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        h4.setText("0");
        h4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow36.add(h4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

        jLabel44.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(232, 240, 217));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Espresso [L]");
        shadow36.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        Hotdrinks.add(shadow36, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, 130, 160));

        shadow37.setBackground(new java.awt.Color(89, 56, 46));
        shadow37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price40.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price40.setForeground(new java.awt.Color(232, 240, 217));
        price40.setText("65PHP");
        shadow37.add(price40, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        hotdrink5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Hot Chocolate.png"))); // NOI18N
        hotdrink5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotdrink5ActionPerformed(evt);
            }
        });
        shadow37.add(hotdrink5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        h5.setText("0");
        h5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow37.add(h5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel45.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(232, 240, 217));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Hot Chocolate [M]");
        shadow37.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Hotdrinks.add(shadow37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 130, 160));

        shadow38.setBackground(new java.awt.Color(89, 56, 46));
        shadow38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price41.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price41.setForeground(new java.awt.Color(232, 240, 217));
        price41.setText("75PHP");
        shadow38.add(price41, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        hotdrink6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Hot Chocolate.png"))); // NOI18N
        hotdrink6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotdrink6ActionPerformed(evt);
            }
        });
        shadow38.add(hotdrink6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        h6.setText("0");
        h6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow38.add(h6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel46.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(232, 240, 217));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Hot Chocolate [L]");
        shadow38.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Hotdrinks.add(shadow38, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 130, 160));

        shadow39.setBackground(new java.awt.Color(89, 56, 46));
        shadow39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price42.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price42.setForeground(new java.awt.Color(232, 240, 217));
        price42.setText("85PHP");
        shadow39.add(price42, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        hotdrink7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cafe Americano.png"))); // NOI18N
        hotdrink7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotdrink7ActionPerformed(evt);
            }
        });
        shadow39.add(hotdrink7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        h7.setText("0");
        h7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow39.add(h7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel47.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(232, 240, 217));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("Cafe Americano [M]");
        shadow39.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Hotdrinks.add(shadow39, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 270, 130, 160));

        shadow40.setBackground(new java.awt.Color(89, 56, 46));
        shadow40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price43.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price43.setForeground(new java.awt.Color(232, 240, 217));
        price43.setText("95PHP");
        shadow40.add(price43, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 30));

        hotdrink8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cafe Americano.png"))); // NOI18N
        hotdrink8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotdrink8ActionPerformed(evt);
            }
        });
        shadow40.add(hotdrink8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        h8.setText("0");
        h8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow40.add(h8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 70, 20));

        jLabel48.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(232, 240, 217));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Cafe Americano [L]");
        shadow40.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Hotdrinks.add(shadow40, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 130, 160));

        jLayeredPane1.setLayer(Hotdrinks, javax.swing.JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(Hotdrinks, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 640));

        Iceddrinks.setBackground(new java.awt.Color(108, 68, 56));
        Iceddrinks.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        shadow49.setBackground(new java.awt.Color(89, 56, 46));
        shadow49.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price44.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price44.setForeground(new java.awt.Color(232, 240, 217));
        price44.setText("125PHP");
        shadow49.add(price44, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Coffe.png"))); // NOI18N
        icedrink1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink1ActionPerformed(evt);
            }
        });
        shadow49.add(icedrink1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i1.setText("0");
        i1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow49.add(i1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

        jLabel49.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(232, 240, 217));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Iced Coffee [M]");
        shadow49.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Iceddrinks.add(shadow49, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 130, 160));

        shadow50.setBackground(new java.awt.Color(89, 56, 46));
        shadow50.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price45.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price45.setForeground(new java.awt.Color(232, 240, 217));
        price45.setText("130PHP");
        shadow50.add(price45, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Coffe.png"))); // NOI18N
        icedrink2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink2ActionPerformed(evt);
            }
        });
        shadow50.add(icedrink2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i2.setText("0");
        i2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow50.add(i2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 70, 20));

        jLabel50.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(232, 240, 217));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Iced Coffee [L]");
        shadow50.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Iceddrinks.add(shadow50, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 130, 160));

        shadow51.setBackground(new java.awt.Color(89, 56, 46));
        shadow51.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price46.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price46.setForeground(new java.awt.Color(232, 240, 217));
        price46.setText("145PHP");
        shadow51.add(price46, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Latte.png"))); // NOI18N
        icedrink3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink3ActionPerformed(evt);
            }
        });
        shadow51.add(icedrink3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i3.setText("0");
        i3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow51.add(i3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 70, 20));

        jLabel51.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(232, 240, 217));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Iced Latte [M]");
        shadow51.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Iceddrinks.add(shadow51, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 130, 160));

        shadow52.setBackground(new java.awt.Color(89, 56, 46));
        shadow52.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price47.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price47.setForeground(new java.awt.Color(232, 240, 217));
        price47.setText("160PHP");
        shadow52.add(price47, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Latte.png"))); // NOI18N
        icedrink4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink4ActionPerformed(evt);
            }
        });
        shadow52.add(icedrink4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i4.setText("0");
        i4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow52.add(i4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

        jLabel52.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(232, 240, 217));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Iced Latte [L]");
        shadow52.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Iceddrinks.add(shadow52, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 130, 160));

        shadow53.setBackground(new java.awt.Color(89, 56, 46));
        shadow53.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price48.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price48.setForeground(new java.awt.Color(232, 240, 217));
        price48.setText("170PHP");
        shadow53.add(price48, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Caffè Mocha.png"))); // NOI18N
        icedrink5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink5ActionPerformed(evt);
            }
        });
        shadow53.add(icedrink5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i5.setText("0");
        i5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow53.add(i5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel53.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(232, 240, 217));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Iced Caffè Mocha [M]");
        shadow53.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Iceddrinks.add(shadow53, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 130, 160));

        shadow54.setBackground(new java.awt.Color(89, 56, 46));
        shadow54.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price49.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price49.setForeground(new java.awt.Color(232, 240, 217));
        price49.setText("185PHP");
        shadow54.add(price49, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Caffè Mocha.png"))); // NOI18N
        icedrink6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink6ActionPerformed(evt);
            }
        });
        shadow54.add(icedrink6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i6.setText("0");
        i6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow54.add(i6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel54.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(232, 240, 217));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Iced Caffè Mocha [L]");
        shadow54.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Iceddrinks.add(shadow54, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 130, 160));

        shadow55.setBackground(new java.awt.Color(89, 56, 46));
        shadow55.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price50.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price50.setForeground(new java.awt.Color(232, 240, 217));
        price50.setText("135PHP");
        shadow55.add(price50, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Americano.png"))); // NOI18N
        icedrink7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink7ActionPerformed(evt);
            }
        });
        shadow55.add(icedrink7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i7.setText("0");
        i7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow55.add(i7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel55.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(232, 240, 217));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Iced Americano [M]");
        shadow55.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Iceddrinks.add(shadow55, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, 130, 160));

        shadow56.setBackground(new java.awt.Color(89, 56, 46));
        shadow56.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price51.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price51.setForeground(new java.awt.Color(232, 240, 217));
        price51.setText("150PHP");
        shadow56.add(price51, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Americano.png"))); // NOI18N
        icedrink8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink8ActionPerformed(evt);
            }
        });
        shadow56.add(icedrink8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i8.setText("0");
        i8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow56.add(i8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 70, 20));

        jLabel56.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(232, 240, 217));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("Iced Americano [L]");
        shadow56.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Iceddrinks.add(shadow56, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 260, 130, 160));

        shadow57.setBackground(new java.awt.Color(89, 56, 46));
        shadow57.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price52.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price52.setForeground(new java.awt.Color(232, 240, 217));
        price52.setText("170PHP");
        shadow57.add(price52, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Caramel Macchiato.png"))); // NOI18N
        icedrink9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink9ActionPerformed(evt);
            }
        });
        shadow57.add(icedrink9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i9.setText("0");
        i9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow57.add(i9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 70, 20));

        jLabel57.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(232, 240, 217));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("Macchiato [M]");
        shadow57.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 130, 20));

        jLabel58.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(232, 240, 217));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("Iced Caramel");
        shadow57.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, -1));

        Iceddrinks.add(shadow57, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 130, 160));

        shadow58.setBackground(new java.awt.Color(89, 56, 46));
        shadow58.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price53.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price53.setForeground(new java.awt.Color(232, 240, 217));
        price53.setText("185PHP");
        shadow58.add(price53, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Caramel Macchiato.png"))); // NOI18N
        icedrink10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink10ActionPerformed(evt);
            }
        });
        shadow58.add(icedrink10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i10.setText("0");
        i10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow58.add(i10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 70, 20));

        jLabel59.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(232, 240, 217));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Macchiato [L]");
        shadow58.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 130, 20));

        jLabel60.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(232, 240, 217));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("Iced Caramel");
        shadow58.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, -1));

        Iceddrinks.add(shadow58, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, 130, 160));

        shadow59.setBackground(new java.awt.Color(89, 56, 46));
        shadow59.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price54.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price54.setForeground(new java.awt.Color(232, 240, 217));
        price54.setText("145PHP");
        shadow59.add(price54, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Cappuccino.png"))); // NOI18N
        icedrink11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink11ActionPerformed(evt);
            }
        });
        shadow59.add(icedrink11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i11.setText("0");
        i11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow59.add(i11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel61.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(232, 240, 217));
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setText("Iced Cappuccino [M]");
        shadow59.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Iceddrinks.add(shadow59, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 440, 130, 160));

        shadow60.setBackground(new java.awt.Color(89, 56, 46));
        shadow60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        price55.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        price55.setForeground(new java.awt.Color(232, 240, 217));
        price55.setText("160PHP");
        shadow60.add(price55, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 0, 70, 30));

        icedrink12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Iced Cappuccino.png"))); // NOI18N
        icedrink12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icedrink12ActionPerformed(evt);
            }
        });
        shadow60.add(icedrink12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 100));

        i12.setText("0");
        i12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        shadow60.add(i12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 20));

        jLabel62.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(232, 240, 217));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("Iced Cappuccino [L]");
        shadow60.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 130, 30));

        Iceddrinks.add(shadow60, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 440, 130, 160));

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(232, 240, 217));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ICED BEVERAGES");
        Iceddrinks.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 40));

        AAAAAAAa4.setBackground(new java.awt.Color(89, 56, 46));
        AAAAAAAa4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 240, 217)));
        AAAAAAAa4.setOpaque(true);
        Iceddrinks.add(AAAAAAAa4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 690, 50));

        jLayeredPane1.setLayer(Iceddrinks, javax.swing.JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(Iceddrinks, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 640));

        MainPanel.add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, 670, 640));

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        MainPanel.getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
    String pass = "omcm";
    JPasswordField inputKeyField = new JPasswordField();
    int result = JOptionPane.showConfirmDialog(null, inputKeyField, "Enter the confirmation key:", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
        String inputKey = new String(inputKeyField.getPassword());
        if (pass.equals(inputKey)) {
            //Code Here

            this.setVisible(false);
            new LoginUI().setVisible(true);
            
            
        } else {
            if (inputKey.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Key");
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect key, please try again.");
            }
        }
    }

    }//GEN-LAST:event_LogoutActionPerformed

    private void clearlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearlistActionPerformed
int result = JOptionPane.showConfirmDialog(null, "Please Confirm Removal","Confirmation", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
DefaultTableModel dt = (DefaultTableModel) Table1.getModel();
if (Table1.getSelectedRow() >= 0) {
    String r = dt.getValueAt(Table1.getSelectedRow(), 0).toString();
    int rw = Table1.getSelectedRow();
    double itemPrice = Double.valueOf(Table1.getValueAt(rw, 3).toString());
    double taxRate = getTax(itemPrice);
    DecimalFormat df = new DecimalFormat("#,###.##");
    try {
        double currentTotal = df.parse(total.getText()).doubleValue();
        double currentTax = df.parse(tax.getText()).doubleValue();
        double updatedTotal = currentTotal - itemPrice - taxRate * itemPrice;
        double updatedTax = currentTax - taxRate * itemPrice;
        total.setText(df.format(updatedTotal));
        tax.setText(df.format(updatedTax));
        String cashValue = cash.getText();
        double cash = df.parse(cashValue).doubleValue();
        double Change = cash - updatedTotal;
        change.setText(df.format(Change));
    } catch (ParseException e) {
        // Handle the parse exception
    }
    Object quantityValue = Table1.getValueAt(rw, 2);
    try {
        int itemQuantity = Integer.valueOf(quantityValue.toString());
        int currentTotalQuantity = Integer.valueOf(totalQuantity.getText());
        int updatedTotalQuantity = currentTotalQuantity - itemQuantity;
        totalQuantity.setText(String.valueOf(updatedTotalQuantity));
    } catch (NumberFormatException e) {
        // Handle the exception
    }
    dt.removeRow(rw);
    if (Table1.getRowCount() == 0) {
        tax.setText("");
        total.setText("");
        change.setText("");
        menu.setEnabled(true);
        cash.setEnabled(true);
    } else {
        double newTot = 0.0;
        int numOfRow = Table1.getRowCount();
        for (int i = 0; i < numOfRow; i++) {
            double value = Double.valueOf(Table1.getValueAt(i, 3).toString());
            newTot += value;
        }
        double newTaxRate = getTax(newTot);
        double newTaxAmount = newTot * newTaxRate;
        total.setText(df.format(newTot + newTaxAmount));
        tax.setText(df.format(newTaxRate * 100) + "%");
        
    }




    switch(r) {
        
        case "1":
            d1.setText("0");
            break;
        case "2":
            d2.setText("0");
            break;
        case "3":
            d3.setText("0");
            break;
        case "4":
            d4.setText("0");
            break;
        case "5":
            d5.setText("0");
            break;
        case "6":
            d6.setText("0");
            break;
        case "7":
            d7.setText("0");
            break;
        case "8":
            d8.setText("0");
            break;
        case "9":
            d9.setText("0");
            break;
        case "10":
            d10.setText("0");
            break;
        case "11":
            d11.setText("0");
            break;
        case "12":
            d12.setText("0");
            break;
        case "13":
            d13.setText("0");
            break;
        case "14":
            d14.setText("0");
            break;
        case "15":
            d15.setText("0");
            break;
        case "16":
            d16.setText("0");
            break;
        case "17":
            s1.setText("0");
            break;
        case "18":
            s2.setText("0");
            break;
        case "19":
            s3.setText("0");
            break;
        case "20":
            s4.setText("0");
            break;
        case "21":
            s5.setText("0");
            break;
        case "22":
            s6.setText("0");
            break;
        case "23":
            s7.setText("0");
            break;
        case "24":
            s8.setText("0");
            break;
        case "25":
            s9.setText("0");
            break;
        case "26":
            s10.setText("0");
            break;
        case "27":
            s11.setText("0");
            break;
        case "28":
            s12.setText("0");
            break;
        case "29":
            s13.setText("0");
            break;
        case "30":
            s14.setText("0");
            break;
        case "31":
            s15.setText("0");
            break;
        case "32":
            s16.setText("0");
            break;
        case "33":
            h1.setText("0");
            break;
        case "34":
            h2.setText("0");
            break;
        case "35":
            h3.setText("0");
            break;
        case "36":
            h4.setText("0");
            break;
        case "37":
            h5.setText("0");
            break;
        case "38":
            h6.setText("0");
            break;
        case "39":
            h7.setText("0");
            break;
        case "40":
            h8.setText("0");
            break;
        case "41":
            i1.setText("0");
            break;
        case "42":
            i2.setText("0");
            break;
        case "43":
            i3.setText("0");
            break;
        case "44":
            i4.setText("0");
            break;
        case "45":
            i5.setText("0");
            break;
        case "46":
            i6.setText("0");
            break;
        case "47":
            i7.setText("0");
            break;
        case "48":
            i8.setText("0");
            break;
        case "49":
            i9.setText("0");
            break;
        case "50":
            i10.setText("0");
            break;
        case "51":
            i11.setText("0");
            break;
        case "52":
            i12.setText("0");
            break;
        case "53":
            halfdozen1.setText("0");
            break;
        case "54":
            halfdozen2.setText("0");
            break;
        case "55":
            dozen1.setText("0");
            break;
       case "56":
            dozen2.setText("0");
            break;
    }
       } 
    }
    }//GEN-LAST:event_clearlistActionPerformed

    private void homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeActionPerformed
    Home.setVisible(true);
    Donuts.setVisible(false);
    Shakes.setVisible(false);
    Hotdrinks.setVisible(false);
    Iceddrinks.setVisible(false);
    foods.setVisible(false);
    shakes.setVisible(false);
    hotdrinks.setVisible(false);
    colddrinks.setVisible(false);
    menu.setVisible(true);
    sales.setVisible(true);
    }//GEN-LAST:event_homeActionPerformed

    private void foodsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodsActionPerformed
    Home.setVisible(false);
    Donuts.setVisible(true);
    Shakes.setVisible(false);
    Hotdrinks.setVisible(false);
    Iceddrinks.setVisible(false);
    }//GEN-LAST:event_foodsActionPerformed

    private void shakesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shakesActionPerformed
    Home.setVisible(false);
    Donuts.setVisible(false);
    Shakes.setVisible(true);
    Hotdrinks.setVisible(false);
    Iceddrinks.setVisible(false);
    }//GEN-LAST:event_shakesActionPerformed

    private void hotdrinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotdrinksActionPerformed
    Home.setVisible(false);
    Donuts.setVisible(false);
    Shakes.setVisible(false);
    Hotdrinks.setVisible(true);
    Iceddrinks.setVisible(false);
    }//GEN-LAST:event_hotdrinksActionPerformed

    private void colddrinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colddrinksActionPerformed
    Home.setVisible(false);
    Donuts.setVisible(false);
    Shakes.setVisible(false);
    Hotdrinks.setVisible(false);
    Iceddrinks.setVisible(true);
    }//GEN-LAST:event_colddrinksActionPerformed
int pX, pY;
    private void MainPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainPanelMousePressed
        pX = evt.getX();
        pY = evt.getY();
    }//GEN-LAST:event_MainPanelMousePressed

    private void MainPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainPanelMouseDragged
       this.setLocation(this.getLocation().x + evt.getX() - pX,
                         this.getLocation().y + evt.getY() - pY);
    }//GEN-LAST:event_MainPanelMouseDragged

    
    private void shake1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake1ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s1.setText(String.valueOf(i));
    
    addtable(116, "Jamocha M", i,110);
    
    cal();
    }//GEN-LAST:event_shake1ActionPerformed

    private void shake2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake2ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s2.setText(String.valueOf(i));
    
    addtable(117, "OreoChoco M", i,110);
    
    cal();
    }//GEN-LAST:event_shake2ActionPerformed

    private void shake11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake11ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s11.setText(String.valueOf(i));
    
    addtable(126, "Peach\n  Shake M", i,90);
    
    cal();
    }//GEN-LAST:event_shake11ActionPerformed

    private void shake4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake4ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s4.setText(String.valueOf(i));
    
    addtable(119, "Vanilla\n  Ice Cream M", i,110);
    
    cal();
    }//GEN-LAST:event_shake4ActionPerformed

    private void shake5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake5ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s5.setText(String.valueOf(i));
    
    addtable(120, "Jamocha L", i,125);
    
    cal();
    }//GEN-LAST:event_shake5ActionPerformed

    private void shake6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake6ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s6.setText(String.valueOf(i));
    
    addtable(121, "Oreo Choco L", i,125);
    
    cal();
    }//GEN-LAST:event_shake6ActionPerformed

    private void shake15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake15ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s15.setText(String.valueOf(i));
    
    addtable(130, "Peach Milk\n  Shake L", i,105);
    
    cal();
    }//GEN-LAST:event_shake15ActionPerformed

    private void shake8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake8ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s8.setText(String.valueOf(i));
    
    addtable(123, "Vanilla\n  Ice Cream L", i,125);
    
    cal();
    }//GEN-LAST:event_shake8ActionPerformed

    private void shake9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake9ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s9.setText(String.valueOf(i));
    
    addtable(124, "Vanilla\n  Shake M", i,110);
    
    cal();
    }//GEN-LAST:event_shake9ActionPerformed

    private void shake10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake10ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s10.setText(String.valueOf(i));
    
    addtable(125, "Caramel\n  Cinnamon M", i,110);
    
    cal();
    }//GEN-LAST:event_shake10ActionPerformed

    private void shake3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake3ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s3.setText(String.valueOf(i));
    
    addtable(118, "SweetMngo M", i,90);
    
    cal();
    }//GEN-LAST:event_shake3ActionPerformed

    private void sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s12.setText(String.valueOf(i));
    
    addtable(127, "Chocolate\n  Frappe M", i,90);
    
    cal();
    }//GEN-LAST:event_sActionPerformed

    private void shake13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake13ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s13.setText(String.valueOf(i));
    
    addtable(128, "Vanilla\n  Shake L", i,125);
    
    cal();
    }//GEN-LAST:event_shake13ActionPerformed

    private void shake14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake14ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s14.setText(String.valueOf(i));
    
    addtable(129, "Caramel\n  Cinnamon L", i,125);
    
    cal();
    }//GEN-LAST:event_shake14ActionPerformed

    private void shake7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake7ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s7.setText(String.valueOf(i));
    
    addtable(122, "SweetMngo L", i,105);
    
    cal();
    }//GEN-LAST:event_shake7ActionPerformed

    private void shake16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shake16ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    s16.setText(String.valueOf(i));
    
    addtable(131, "Chocolate\n  Frappe L", i,105);
    
    cal();
    }//GEN-LAST:event_shake16ActionPerformed

    private void clearcashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearcashActionPerformed
    String pass = "omcm";
    JPasswordField inputKeyField = new JPasswordField();
    int result = JOptionPane.showConfirmDialog(null, inputKeyField, "Enter the confirmation key:", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
        String inputKey = new String(inputKeyField.getPassword());
        if (pass.equals(inputKey)) {
            //Code Here

            cash.setText("");
            change.setText("");
            cash.setEnabled(true);
    
            
            
        } else {
            if (inputKey.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Key");
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect key, please try again.");
            }
        }
    }

    }//GEN-LAST:event_clearcashActionPerformed

    private void payActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payActionPerformed
     //This code calculate the total price of the orders
    String cashText = cash.getText();
    if (cashText.isEmpty()) {
    // show a prompt alert if the cash text field is empty
    JOptionPane.showMessageDialog(null, "Please enter amount.", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
    double ttl = Double.valueOf(total.getText().replaceAll(",", ""));
    double csh = Double.valueOf(cashText);
    if (csh < ttl) {
        // show a prompt alert if the cash value is not equal to the total value
        JOptionPane.showMessageDialog(null, "Not enough cash, Please enter amount of cash.", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        int purchasedId = 25051 + (int) (Math.random() * 6969);
        purchaseNumber.setText(String.valueOf(purchasedId));
        double chng = csh - ttl;
        DecimalFormat df = new DecimalFormat("#,###.##");
        change.setText(df.format(chng));
        
        // Calculate total quantity
        int rowCount = Table1.getRowCount();
        int totalQty = 0;
        for (int i = 0; i < rowCount; i++) {
            totalQty += Integer.parseInt(Table1.getValueAt(i, 2).toString());
        }
        totalQuantity.setText(String.valueOf(totalQty));
            Home.setVisible(true);
            Shakes.setVisible(false);
            Hotdrinks.setVisible(false);
            Iceddrinks.setVisible(false);
            foods.setVisible(false);
            shakes.setVisible(false);
            hotdrinks.setVisible(false);
            colddrinks.setVisible(false);
            menu.setVisible(true);
            sales.setVisible(true);
            menu.setEnabled(false);
            cash.setEnabled(false);
            Donuts.setVisible(false);
            clearcash.setEnabled(false);
    }
}

    }//GEN-LAST:event_payActionPerformed

    private void receiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiptActionPerformed
    //This code makes the receipt
    
    //This code hides the other UI when Receipt Button is pressed
    Receipt.setVisible(true);
    Order.setVisible(false);
    calculator.setVisible(true);
    //This is a method to create the Header of the receipt
    DearDoughtnut();
            try {
            //Creates a TableModel named df which is df
            DefaultTableModel df = (DefaultTableModel) Table1.getModel();
            //Get the Selected row count
            for (int i = 0; i < Table1.getRowCount(); i++) {
                //Get the value of inside the Columns
                String Name = df.getValueAt(i, 1).toString();
                String Qty = df.getValueAt(i, 2).toString();
                String Price = df.getValueAt(i, 3).toString();
                //Create 3 Columns named - Name,Qty,Price inside the JTextArea
                receiptarea.setText(receiptarea.getText()+"  "+ Name +"\t"+"        "+ Qty +"\t"+"               "+ Price +"\n");
            } 
            //Creates a layout at the bottom and store the values of Tax, Total, Cash, and Change
            receiptarea.setText(receiptarea.getText()+"---------------------------------------------------------------\n");
            receiptarea.setText(receiptarea.getText()+"  TAX" + "\t"+"        :"+"\t"+"               "+tax.getText()+"\n");
            receiptarea.setText(receiptarea.getText()+"  TOTAL" + "\t"+"        :"+"\t"+"               "+total.getText()+"\n");
            receiptarea.setText(receiptarea.getText()+"  CASH" + "\t"+"        :"+"\t"+"               "+cash.getText()+"\n");
            receiptarea.setText(receiptarea.getText()+"  CHANGE" + "\t"+"        :"+"\t"+"               "+change.getText()+"\n");
            receiptarea.setText(receiptarea.getText()+"---------------------------------------------------------------\n");
            receiptarea.setText(receiptarea.getText()+"       Thank you for being our valued customer!"+"\n");
            receiptarea.setText(receiptarea.getText()+"                         Please come again!"+"\n");
            
        } catch (Exception e){
            
        }
            createTable();
            menu.setEnabled(true);
            cash.setEnabled(true);
            clearall.setEnabled(true);
            clearlist.setEnabled(true);
    }//GEN-LAST:event_receiptActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
    DearDoughtnut();
            try {

            DefaultTableModel df = (DefaultTableModel) Table1.getModel();
            
            for (int i = 0; i < Table1.getRowCount(); i++) {
                
                String Name = df.getValueAt(i, 1).toString();
                String Qty = df.getValueAt(i, 2).toString();
                String Price = df.getValueAt(i, 3).toString();
                
                receiptarea.setText(receiptarea.getText()+"  "+ Name +"\t"+"        "+ Qty +"\t"+"               "+ Price +"\n");
            } 
            receiptarea.setText(receiptarea.getText()+"---------------------------------------------------------------\n");
            receiptarea.setText(receiptarea.getText()+"  TAX" + "\t"+"        :"+"\t"+"               "+tax.getText()+"\n");
            receiptarea.setText(receiptarea.getText()+"  TOTAL" + "\t"+"        :"+"\t"+"               "+total.getText()+"\n");
            receiptarea.setText(receiptarea.getText()+"  CASH" + "\t"+"        :"+"\t"+"               "+cash.getText()+"\n");
            receiptarea.setText(receiptarea.getText()+"  CHANGE" + "\t"+"        :"+"\t"+"               "+change.getText()+"\n");
            receiptarea.setText(receiptarea.getText()+"---------------------------------------------------------------\n");
            receiptarea.setText(receiptarea.getText()+"Thank You so much for"+"\n");
            
            receiptarea.print();

        } catch (Exception e){

        }
    }//GEN-LAST:event_printActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
    Order.setVisible(true);
    Receipt.setVisible(false);
    calculator.setVisible(true);
    }//GEN-LAST:event_backActionPerformed

    private void clearallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearallActionPerformed
    String pass = "omcm";
    JPasswordField inputKeyField = new JPasswordField();
    int result = JOptionPane.showConfirmDialog(null, inputKeyField, "Enter the confirmation key:", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
        String inputKey = new String(inputKeyField.getPassword());
        if (pass.equals(inputKey)) {
            //Clear order list and total
            cash.setText("");
            change.setText("");
            total.setText("");
            tax.setText("");
            purchaseNumber.setText("");
            DefaultTableModel dm = (DefaultTableModel)Table1.getModel();
            while(dm.getRowCount() > 0) {
                dm.removeRow(0);
                menu.setEnabled(true);
            }
        } else {
            if (inputKey.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Key");
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect key, please try again.");
            }
        }
    }

    }//GEN-LAST:event_clearallActionPerformed

    private void hotdrink1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotdrink1ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    h1.setText(String.valueOf(i));
    
    addtable(132, "Latte", i,90);
    
    cal();
    }//GEN-LAST:event_hotdrink1ActionPerformed

    private void hotdrink2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotdrink2ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    h2.setText(String.valueOf(i));
    
    addtable(133, "Cappuccino", i,90);
    
    cal();
    }//GEN-LAST:event_hotdrink2ActionPerformed

    private void hotdrink3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotdrink3ActionPerformed
     String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    h3.setText(String.valueOf(i));
    
    addtable(134, "Espresso M", i,60);
    
    cal();
    }//GEN-LAST:event_hotdrink3ActionPerformed

    private void hotdrink4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotdrink4ActionPerformed
     String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    h4.setText(String.valueOf(i));
    
    addtable(135, "Espresso L", i,75);
    
    cal();
    }//GEN-LAST:event_hotdrink4ActionPerformed

    private void hotdrink5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotdrink5ActionPerformed
     String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    h5.setText(String.valueOf(i));
    
    addtable(136, "Hot Chocolate\n  M", i,65);
    
    cal();
    }//GEN-LAST:event_hotdrink5ActionPerformed

    private void hotdrink6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotdrink6ActionPerformed
     String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    h6.setText(String.valueOf(i));
    
    addtable(137, "Hot Chocolate\n  L", i,75);
    
    cal();
    }//GEN-LAST:event_hotdrink6ActionPerformed

    private void hotdrink7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotdrink7ActionPerformed
     String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    h7.setText(String.valueOf(i));
    
    addtable(138, "Cafe Americano\n  M", i,85);
    
    cal();
    }//GEN-LAST:event_hotdrink7ActionPerformed

    private void hotdrink8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotdrink8ActionPerformed
     String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    h8.setText(String.valueOf(i));
    
    addtable(139, "Cafe Americano\n  L", i,95);
    
    cal();
    }//GEN-LAST:event_hotdrink8ActionPerformed

    private void icedrink1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink1ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i1.setText(String.valueOf(i));
    
    addtable(140, "Iced\n  Coffee M", i,125);
    
    cal();
    }//GEN-LAST:event_icedrink1ActionPerformed

    private void icedrink2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink2ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i2.setText(String.valueOf(i));
    
    addtable(141, "Iced\n  Coffee L", i,130);
    
    cal();
    }//GEN-LAST:event_icedrink2ActionPerformed

    private void icedrink3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink3ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i3.setText(String.valueOf(i));
    
    addtable(142, "Iced\n  Latte M", i,145);
    
    cal();
    }//GEN-LAST:event_icedrink3ActionPerformed

    private void icedrink4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink4ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i4.setText(String.valueOf(i));
    
    addtable(143, "Iced\n  Latte L", i,160);
    
    cal();
    }//GEN-LAST:event_icedrink4ActionPerformed

    private void icedrink5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink5ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i5.setText(String.valueOf(i));
    
    addtable(144, "Iced Caffè\n  Mocha M", i,170);
    
    cal();
    }//GEN-LAST:event_icedrink5ActionPerformed

    private void icedrink6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink6ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i6.setText(String.valueOf(i));
    
    addtable(145, "Iced Caffè\n  Mocha L", i,185);
    
    cal();
    }//GEN-LAST:event_icedrink6ActionPerformed

    private void icedrink7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink7ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i7.setText(String.valueOf(i));
    
    addtable(146, "Iced\n  Americano M", i,135);
    
    cal();
    }//GEN-LAST:event_icedrink7ActionPerformed

    private void icedrink8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink8ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i8.setText(String.valueOf(i));
    
    addtable(147, "Iced\n  Americano L", i,150);
    
    cal();
    }//GEN-LAST:event_icedrink8ActionPerformed

    private void icedrink9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink9ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i9.setText(String.valueOf(i));
    
    addtable(148, "Iced Caramel\n  Macchiato M", i,170);
    
    cal();
    }//GEN-LAST:event_icedrink9ActionPerformed

    private void icedrink10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink10ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i10.setText(String.valueOf(i));
    
    addtable(149, "Iced Caramel\n  Macchiato L", i,185);
    
    cal();
    }//GEN-LAST:event_icedrink10ActionPerformed

    private void icedrink11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink11ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i11.setText(String.valueOf(i));
    
    addtable(150, "Iced\n  Cappuccino M", i,145);
    
    cal();
    }//GEN-LAST:event_icedrink11ActionPerformed

    private void icedrink12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icedrink12ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    i12.setText(String.valueOf(i));
    
    addtable(151, "Iced\n  Cappuccino L", i,160);
    
    cal();
    }//GEN-LAST:event_icedrink12ActionPerformed

    private void perpieceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_perpieceActionPerformed
    PerPieceScrollPane.setVisible(true);
    HalfDozenScrollPane.setVisible(false);
    DozenScrollPane.setVisible(false);
    
    }//GEN-LAST:event_perpieceActionPerformed

    private void halfdozenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_halfdozenActionPerformed
    HalfDozenScrollPane.setVisible(true);
    PerPieceScrollPane.setVisible(false);
    DozenScrollPane.setVisible(false);
    }//GEN-LAST:event_halfdozenActionPerformed

    private void donut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut1ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d1.setText(String.valueOf(i));
    
    addtable(100, "Glaze\n  [Per Piece]", i,25);
    
    cal();
    }//GEN-LAST:event_donut1ActionPerformed

    private void donut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut2ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d2.setText(String.valueOf(i));
    
    addtable(101, "Apple n Pie\n  [Per Piece]", i,25);
    
    cal();
    }//GEN-LAST:event_donut2ActionPerformed

    private void donut3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut3ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d3.setText(String.valueOf(i));
    
    addtable(102, "Bavarian Kreme\n  [Per Piece]", i,25);
    
    cal();
    }//GEN-LAST:event_donut3ActionPerformed

    private void donut10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut10ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d10.setText(String.valueOf(i));
    
    addtable(103, "Double Chocolate\n  [Per Piece]", i,25);
    
    cal();
    }//GEN-LAST:event_donut10ActionPerformed

    private void donut12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut12ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d12.setText(String.valueOf(i));
    
    addtable(104, "Jelly\n  [Per Piece]", i,25);
    
    cal();
    }//GEN-LAST:event_donut12ActionPerformed

    private void donut6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut6ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d6.setText(String.valueOf(i));
    
    addtable(105, "Chocolate Coconut\n  [Per Piece]", i,25);
    
    cal();
    }//GEN-LAST:event_donut6ActionPerformed

    private void donut7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut7ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d7.setText(String.valueOf(i));
    
    addtable(106, "Chocolate Frosted\n  [Per Piece]", i,25);
    
    cal();
    }//GEN-LAST:event_donut7ActionPerformed

    private void donut8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut8ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d8.setText(String.valueOf(i));
    
    addtable(107, "Chocolate Glaze\n  [Per Piece]", i,25);
    
    cal();
    }//GEN-LAST:event_donut8ActionPerformed

    private void donut9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut9ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d9.setText(String.valueOf(i));
    
    addtable(108, "Chocolate Kreme\n  [Per Piece]", i,40);
    
    cal();
    }//GEN-LAST:event_donut9ActionPerformed

    private void donut4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut4ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d4.setText(String.valueOf(i));
    
    addtable(109, "Boston Kreme\n  [Per Piece]", i,40);
    
    cal();
    }//GEN-LAST:event_donut4ActionPerformed

    private void donut5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut5ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d5.setText(String.valueOf(i));
    
    addtable(110, "Butternut\n  [Per Piece]", i,40);
    
    cal();
    }//GEN-LAST:event_donut5ActionPerformed

    private void donut11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut11ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d11.setText(String.valueOf(i));
    
    addtable(111, "French Cruller\n  [Per Piece]", i,40);
    
    cal();
    }//GEN-LAST:event_donut11ActionPerformed

    private void donut13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut13ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d13.setText(String.valueOf(i));
    
    addtable(112, "Mapple Frosted\n  [Per Piece]", i,40);
    
    cal();
    }//GEN-LAST:event_donut13ActionPerformed

    private void donut14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut14ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d14.setText(String.valueOf(i));
    
    addtable(113, "Strawberry Frosted\n  [Per Piece]", i,40);
    
    cal();
    }//GEN-LAST:event_donut14ActionPerformed

    private void donut15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut15ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d15.setText(String.valueOf(i));
    
    addtable(114, "Vanilla Frosted\n  [Per Piece]", i,40);
    
    cal();
    }//GEN-LAST:event_donut15ActionPerformed

    private void donut16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donut16ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    d16.setText(String.valueOf(i));
    
    addtable(115, "Vanilla Kreme\n  [Per Piece]", i,40);
    
    cal();
    }//GEN-LAST:event_donut16ActionPerformed

    private void halfdozenbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_halfdozenbutton1ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    halfdozen1.setText(String.valueOf(i));
    
    addtable(152, "Classic Pack\n  [Half Dozen]", i,150);
    
    cal();
    }//GEN-LAST:event_halfdozenbutton1ActionPerformed

    private void halfdozenbutton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_halfdozenbutton2ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    halfdozen2.setText(String.valueOf(i));
    
    addtable(153, "Premium Pack\n  [Half Dozen]", i,240);
    
    cal();
    }//GEN-LAST:event_halfdozenbutton2ActionPerformed

    private void dozenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dozenActionPerformed
    DozenScrollPane.setVisible(true);
    HalfDozenScrollPane.setVisible(false);
    PerPieceScrollPane.setVisible(false);
    }//GEN-LAST:event_dozenActionPerformed

    private void dozenbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dozenbutton1ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    dozen1.setText(String.valueOf(i));
    
    addtable(154, "Classic Pack\n  [Dozen]", i,300);
    
    cal();
    }//GEN-LAST:event_dozenbutton1ActionPerformed

    private void dozenbutton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dozenbutton2ActionPerformed
    String input = JOptionPane.showInputDialog("Please enter a value:");
    if (input == null || input.trim().length() == 0) {
        return;
    }
    int i = Integer.parseInt(input);
    dozen2.setText(String.valueOf(i));
    
    addtable(155, "Premium Pack\n  [Dozen]", i,480);
    
    cal();
    }//GEN-LAST:event_dozenbutton2ActionPerformed

    private void menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActionPerformed
    menu.setVisible(false);
    Home.setVisible(false);
    Donuts.setVisible(true);
    Shakes.setVisible(false);
    Hotdrinks.setVisible(false);
    Iceddrinks.setVisible(false);
    foods.setVisible(true);
    shakes.setVisible(true);
    hotdrinks.setVisible(true);
    colddrinks.setVisible(true);
    sales.setVisible(false);
    }//GEN-LAST:event_menuActionPerformed

    private void MinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeActionPerformed
    setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_MinimizeActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
    int result = JOptionPane.showConfirmDialog(null, "Please confirm before closing","Exit", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
        System.exit(0);

        }
    }//GEN-LAST:event_ExitActionPerformed

    private void salesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesActionPerformed
    new SalesUI().setVisible(true);
    }//GEN-LAST:event_salesActionPerformed

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
            java.util.logging.Logger.getLogger(DearDoughnut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DearDoughnut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DearDoughnut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DearDoughnut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DearDoughnut().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AAAAAAAa1;
    private javax.swing.JLabel AAAAAAAa2;
    private javax.swing.JLabel AAAAAAAa3;
    private javax.swing.JLabel AAAAAAAa4;
    private javax.swing.JLabel CashJLabel;
    private javax.swing.JLabel ChangeJLabel;
    private javax.swing.JLabel Date;
    private javax.swing.JLabel Date2;
    private javax.swing.JPanel Donuts;
    private javax.swing.JPanel Dozen;
    private javax.swing.JScrollPane DozenScrollPane;
    private javax.swing.JButton Exit;
    private javax.swing.JPanel HalfDozen;
    private javax.swing.JScrollPane HalfDozenScrollPane;
    private javax.swing.JPanel Home;
    private javax.swing.JPanel Hotdrinks;
    private javax.swing.JPanel Iceddrinks;
    private javax.swing.JLabel Label1;
    private javax.swing.JLabel Label2;
    private javax.swing.JButton Logout;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton Minimize;
    private javax.swing.JPanel Order;
    private javax.swing.JPanel PerPiece;
    private javax.swing.JScrollPane PerPieceScrollPane;
    private javax.swing.JPanel Receipt;
    private javax.swing.JScrollPane ScrollPane3;
    private javax.swing.JScrollPane Scrollpane1;
    private javax.swing.JScrollPane Scrollpane2;
    private javax.swing.JPanel Shakes;
    private javax.swing.JPanel ShakesScrollPane;
    private javax.swing.JLabel Tab;
    private javax.swing.JLabel Tab3;
    private javax.swing.JTable Table1;
    private javax.swing.JLabel TaxJLabel;
    private javax.swing.JLabel Time;
    private javax.swing.JLabel Time2;
    private javax.swing.JLabel TotalJLabel;
    private javax.swing.JLabel administrator1;
    private javax.swing.JLabel administrator2;
    private javax.swing.JButton back;
    private javax.swing.JPanel calculator;
    private javax.swing.JTextField cash;
    private javax.swing.JLabel change;
    private javax.swing.JPanel classicdonuts;
    private javax.swing.JButton clearall;
    private javax.swing.JButton clearcash;
    private javax.swing.JButton clearlist;
    private javax.swing.JButton colddrinks;
    private javax.swing.JLabel d1;
    private javax.swing.JLabel d10;
    private javax.swing.JLabel d11;
    private javax.swing.JLabel d12;
    private javax.swing.JLabel d13;
    private javax.swing.JLabel d14;
    private javax.swing.JLabel d15;
    private javax.swing.JLabel d16;
    private javax.swing.JLabel d2;
    private javax.swing.JLabel d3;
    private javax.swing.JLabel d4;
    private javax.swing.JLabel d5;
    private javax.swing.JLabel d6;
    private javax.swing.JLabel d7;
    private javax.swing.JLabel d8;
    private javax.swing.JLabel d9;
    private javax.swing.JButton donut1;
    private javax.swing.JButton donut10;
    private javax.swing.JButton donut11;
    private javax.swing.JButton donut12;
    private javax.swing.JButton donut13;
    private javax.swing.JButton donut14;
    private javax.swing.JButton donut15;
    private javax.swing.JButton donut16;
    private javax.swing.JButton donut2;
    private javax.swing.JButton donut3;
    private javax.swing.JButton donut4;
    private javax.swing.JButton donut5;
    private javax.swing.JButton donut6;
    private javax.swing.JButton donut7;
    private javax.swing.JButton donut8;
    private javax.swing.JButton donut9;
    private javax.swing.JButton dozen;
    private javax.swing.JLabel dozen1;
    private javax.swing.JLabel dozen2;
    private javax.swing.JButton dozenbutton1;
    private javax.swing.JButton dozenbutton2;
    private javax.swing.JPanel dozenpanel1;
    private javax.swing.JPanel dozenpanel2;
    private javax.swing.JButton foods;
    private javax.swing.JLabel h1;
    private javax.swing.JLabel h2;
    private javax.swing.JLabel h3;
    private javax.swing.JLabel h4;
    private javax.swing.JLabel h5;
    private javax.swing.JLabel h6;
    private javax.swing.JLabel h7;
    private javax.swing.JLabel h8;
    private javax.swing.JButton halfdozen;
    private javax.swing.JLabel halfdozen1;
    private javax.swing.JLabel halfdozen2;
    private javax.swing.JButton halfdozenbutton1;
    private javax.swing.JButton halfdozenbutton2;
    private javax.swing.JPanel halfdozenpanel1;
    private javax.swing.JPanel halfdozenpanel2;
    private javax.swing.JButton home;
    private javax.swing.JButton hotdrink1;
    private javax.swing.JButton hotdrink2;
    private javax.swing.JButton hotdrink3;
    private javax.swing.JButton hotdrink4;
    private javax.swing.JButton hotdrink5;
    private javax.swing.JButton hotdrink6;
    private javax.swing.JButton hotdrink7;
    private javax.swing.JButton hotdrink8;
    private javax.swing.JButton hotdrinks;
    private javax.swing.JLabel i1;
    private javax.swing.JLabel i10;
    private javax.swing.JLabel i11;
    private javax.swing.JLabel i12;
    private javax.swing.JLabel i2;
    private javax.swing.JLabel i3;
    private javax.swing.JLabel i4;
    private javax.swing.JLabel i5;
    private javax.swing.JLabel i6;
    private javax.swing.JLabel i7;
    private javax.swing.JLabel i8;
    private javax.swing.JLabel i9;
    private javax.swing.JButton icedrink1;
    private javax.swing.JButton icedrink10;
    private javax.swing.JButton icedrink11;
    private javax.swing.JButton icedrink12;
    private javax.swing.JButton icedrink2;
    private javax.swing.JButton icedrink3;
    private javax.swing.JButton icedrink4;
    private javax.swing.JButton icedrink5;
    private javax.swing.JButton icedrink6;
    private javax.swing.JButton icedrink7;
    private javax.swing.JButton icedrink8;
    private javax.swing.JButton icedrink9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton menu;
    private javax.swing.JLabel orderlist;
    private javax.swing.JButton pay;
    private javax.swing.JButton perpiece;
    private javax.swing.JPanel premiumdonuts;
    private javax.swing.JLabel price;
    private javax.swing.JLabel price1;
    private javax.swing.JLabel price10;
    private javax.swing.JLabel price11;
    private javax.swing.JLabel price12;
    private javax.swing.JLabel price13;
    private javax.swing.JLabel price14;
    private javax.swing.JLabel price15;
    private javax.swing.JLabel price16;
    private javax.swing.JLabel price17;
    private javax.swing.JLabel price18;
    private javax.swing.JLabel price19;
    private javax.swing.JLabel price2;
    private javax.swing.JLabel price20;
    private javax.swing.JLabel price21;
    private javax.swing.JLabel price22;
    private javax.swing.JLabel price23;
    private javax.swing.JLabel price24;
    private javax.swing.JLabel price25;
    private javax.swing.JLabel price26;
    private javax.swing.JLabel price27;
    private javax.swing.JLabel price28;
    private javax.swing.JLabel price29;
    private javax.swing.JLabel price3;
    private javax.swing.JLabel price30;
    private javax.swing.JLabel price31;
    private javax.swing.JLabel price32;
    private javax.swing.JLabel price33;
    private javax.swing.JLabel price34;
    private javax.swing.JLabel price35;
    private javax.swing.JLabel price36;
    private javax.swing.JLabel price37;
    private javax.swing.JLabel price38;
    private javax.swing.JLabel price39;
    private javax.swing.JLabel price4;
    private javax.swing.JLabel price40;
    private javax.swing.JLabel price41;
    private javax.swing.JLabel price42;
    private javax.swing.JLabel price43;
    private javax.swing.JLabel price44;
    private javax.swing.JLabel price45;
    private javax.swing.JLabel price46;
    private javax.swing.JLabel price47;
    private javax.swing.JLabel price48;
    private javax.swing.JLabel price49;
    private javax.swing.JLabel price5;
    private javax.swing.JLabel price50;
    private javax.swing.JLabel price51;
    private javax.swing.JLabel price52;
    private javax.swing.JLabel price53;
    private javax.swing.JLabel price54;
    private javax.swing.JLabel price55;
    private javax.swing.JLabel price6;
    private javax.swing.JLabel price7;
    private javax.swing.JLabel price8;
    private javax.swing.JLabel price9;
    private javax.swing.JButton print;
    private javax.swing.JLabel purchaseNumber;
    private javax.swing.JButton receipt;
    private javax.swing.JTextArea receiptarea;
    private javax.swing.JButton s;
    private javax.swing.JLabel s1;
    private javax.swing.JLabel s10;
    private javax.swing.JLabel s11;
    private javax.swing.JLabel s12;
    private javax.swing.JLabel s13;
    private javax.swing.JLabel s14;
    private javax.swing.JLabel s15;
    private javax.swing.JLabel s16;
    private javax.swing.JLabel s2;
    private javax.swing.JLabel s3;
    private javax.swing.JLabel s4;
    private javax.swing.JLabel s5;
    private javax.swing.JLabel s6;
    private javax.swing.JLabel s7;
    private javax.swing.JLabel s8;
    private javax.swing.JLabel s9;
    private javax.swing.JButton sales;
    private javax.swing.JPanel shadow1;
    private javax.swing.JPanel shadow10;
    private javax.swing.JPanel shadow11;
    private javax.swing.JPanel shadow12;
    private javax.swing.JPanel shadow13;
    private javax.swing.JPanel shadow14;
    private javax.swing.JPanel shadow15;
    private javax.swing.JPanel shadow16;
    private javax.swing.JPanel shadow17;
    private javax.swing.JPanel shadow18;
    private javax.swing.JPanel shadow19;
    private javax.swing.JPanel shadow2;
    private javax.swing.JPanel shadow20;
    private javax.swing.JPanel shadow21;
    private javax.swing.JPanel shadow22;
    private javax.swing.JPanel shadow23;
    private javax.swing.JPanel shadow24;
    private javax.swing.JPanel shadow25;
    private javax.swing.JPanel shadow26;
    private javax.swing.JPanel shadow27;
    private javax.swing.JPanel shadow28;
    private javax.swing.JPanel shadow29;
    private javax.swing.JPanel shadow3;
    private javax.swing.JPanel shadow30;
    private javax.swing.JPanel shadow31;
    private javax.swing.JPanel shadow32;
    private javax.swing.JPanel shadow33;
    private javax.swing.JPanel shadow34;
    private javax.swing.JPanel shadow35;
    private javax.swing.JPanel shadow36;
    private javax.swing.JPanel shadow37;
    private javax.swing.JPanel shadow38;
    private javax.swing.JPanel shadow39;
    private javax.swing.JPanel shadow4;
    private javax.swing.JPanel shadow40;
    private javax.swing.JPanel shadow49;
    private javax.swing.JPanel shadow5;
    private javax.swing.JPanel shadow50;
    private javax.swing.JPanel shadow51;
    private javax.swing.JPanel shadow52;
    private javax.swing.JPanel shadow53;
    private javax.swing.JPanel shadow54;
    private javax.swing.JPanel shadow55;
    private javax.swing.JPanel shadow56;
    private javax.swing.JPanel shadow57;
    private javax.swing.JPanel shadow58;
    private javax.swing.JPanel shadow59;
    private javax.swing.JPanel shadow6;
    private javax.swing.JPanel shadow60;
    private javax.swing.JPanel shadow7;
    private javax.swing.JPanel shadow8;
    private javax.swing.JPanel shadow9;
    private javax.swing.JButton shake1;
    private javax.swing.JButton shake10;
    private javax.swing.JButton shake11;
    private javax.swing.JButton shake13;
    private javax.swing.JButton shake14;
    private javax.swing.JButton shake15;
    private javax.swing.JButton shake16;
    private javax.swing.JButton shake2;
    private javax.swing.JButton shake3;
    private javax.swing.JButton shake4;
    private javax.swing.JButton shake5;
    private javax.swing.JButton shake6;
    private javax.swing.JButton shake7;
    private javax.swing.JButton shake8;
    private javax.swing.JButton shake9;
    private javax.swing.JButton shakes;
    private javax.swing.JLabel tax;
    private javax.swing.JLabel title;
    private javax.swing.JLabel total;
    private javax.swing.JLabel totalQuantity;
    // End of variables declaration//GEN-END:variables
    

}
