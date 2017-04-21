package DAL;

import java.sql.Connection;

import SharedClasses.Date;
import SharedClasses.Item;
import SharedClasses.Order;
import SharedClasses.OrderItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            PreparedStatement ps = c.prepareStatement("INSERT INTO Orders (OrderID, SupplierID, SupplierName, Date, ContactNumber) " +
                    "VALUES (?,?,?,?,?);");
            ps.setInt(1, order.getOrderID());
            ps.setInt(2, order.getSupplier());
            ps.setString(3, order.getSupplierName());
            ps.setString(4,order.getDate().toString());
            ps.setString(5,order.getContactNum());

            ps.executeUpdate();
            c.commit();
            ps.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public boolean setContactNum(int orderID,String conNum){
    	 try {
             String sql = "UPDATE Orders SET ContactNumber = ? WHERE OrderID = ?";

             PreparedStatement pstmt = c.prepareStatement(sql);

             // set the corresponding param
             pstmt.setString(1, conNum);
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
    
    public Order getOrder(int orderID){
    	Order order = null;
        try {
            String sqlQuary = "SELECT * FROM Orders WHERE OrderID = " + orderID + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            order = new Order(rs.getInt(1), rs.getInt(2), rs.getString(3), new Date(rs.getString(4)), rs.getString(5));
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return order;
    }
    
    public Order[] getOrderSup(int supID){
    	Order[] ordersSup = null;
        try {
            String sqlQuary = "SELECT * FROM Orders WHERE SupplierID = " + supID + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            ordersSup= new Order[rs.getFetchSize()];
            for(int i=0; i<ordersSup.length;i++){
            	ordersSup[i] = new Order(rs.getInt(1), rs.getInt(2), rs.getString(3), new Date(rs.getString(4)), rs.getString(5));
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
