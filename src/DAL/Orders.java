package DAL;

import java.sql.Connection;

import SharedClasses.Date;
import SharedClasses.Item;
import SharedClasses.Order;
import SharedClasses.OrderItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rotem on 07/04/2017.
 */
public class Orders {

    private Connection c;
    private java.sql.Statement stmt;

    public Orders(Connection c) {
        this.c = c;
        stmt=null;
    }

    public boolean addOrder(Order order) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO Orders (OrderID, SupplierID, Date, ContactID) " +
                    "VALUES (?,?,?,?,?);");
            ps.setInt(1, order.getOrderID());
            ps.setInt(2, order.getSupplier());
            ps.setString(3,order.getDate().toString());
            ps.setString(4,order.getContactID());

            ps.executeUpdate();
            c.commit();
            ps.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public boolean setContactID(int orderID,String conID){
    	 try {
             String sql = "UPDATE Orders SET ContactID = ? WHERE OrderID = ?";

             PreparedStatement pstmt = c.prepareStatement(sql);

             // set the corresponding param
             pstmt.setString(1, conID);
             pstmt.setInt(2, orderID);
             // update
             pstmt.executeUpdate();

             c.commit();
             pstmt.close();
             return true;
         } catch (SQLException e) {
             return false;
         }
    }
    
    public String getOrder(int orderID){
    	String order = "";
        try {
            String sqlQuary = "SELECT * FROM Orders WHERE OrderID = " + orderID + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            order+= rs.getInt(1);
            order+= " ";
            order+=  rs.getInt(2);
            order+= " ";
            order+= new Date(rs.getString(3));
            order+= " ";
            order+= rs.getString(4);
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return order;
    }
    
    public List<String> getOrderSup(int supID){
    	List<String>ordersSup = new ArrayList<>();
        try {
            String sqlQuary = "SELECT * FROM Orders WHERE SupplierID = '" + supID + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            //System.out.println(rs.getFetchSize());
            while (rs.next()){
                ordersSup.add("" +rs.getInt(1) + " " + rs.getInt(2) + " "+ new Date(rs.getDate(3))+ " "+ rs.getString(4));
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return ordersSup;
    }
    
    public boolean removeOrder(int orderID){
    	try {
            String sql = "DELETE FROM Orders WHERE OrderID = ?;";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, orderID);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean checkOrderExist(int orderID){
    	try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orders where OrderID = '" + orderID + "';");
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }
}
