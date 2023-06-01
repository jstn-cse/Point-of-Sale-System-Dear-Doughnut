/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
/**
 *
 * @author spfi-lipa-otc
 */
public class InsertData {
    
    public static Connection mycon(){
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/receiptlist","root","");
            Statement stmt = c.createStatement();
            return c ;
            
        } catch (Exception e){
        }
        return null;
    }
    
}
