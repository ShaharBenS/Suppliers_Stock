package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import SharedClasses.Date;
import SharedClasses.Order;
import SharedClasses.OrderItem;
/**
 * Created by rotem on 07/04/2017.
 */
public class OrdersItems {

    private Connection c;
    private java.sql.Statement stmt;

    public OrdersItems(Connection c) {
        this.c = c;
        stmt=null;
    }


    public boolean addOrderItem(OrderItem orderItem) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO OrdersItems (OrderID, catalogNumber, ItemName, Quantity, Cost, Discount, FinalCost) " +
                    "VALUES (?,?,?,?,?,?,?);");
            ps.setInt(1, orderItem.getOrderID());
            ps.setInt(2, orderItem.getCatalogNumber());
            ps.setString(3, orderItem.getItemName());
            ps.setInt(4,orderItem.getQuantity());
            ps.setDouble(5,orderItem.getCost());
            ps.setInt(6,orderItem.getDiscount());
            ps.setDouble(7,orderItem.getFinalCost());

            ps.executeUpdate();
            c.commit();
            ps.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
    
    public boolean setQuantity(int orderID,int itemID,int quantity){
   	 try {
            String sql = "UPDATE OrdersItems SET quantity = ? WHERE OrderID = ? and catalogNumber=?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, orderID);
            pstmt.setInt(3, itemID);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
   }
    
    public OrderItem[] getOrderItems(int orderID){
    	OrderItem[] orderItems = null;
        try {
            String sqlQuary = "SELECT * FROM OrdersItems WHERE OrderID = " + orderID + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            orderItems= new OrderItem[rs.getFetchSize()];
            for(int i=0; i<orderItems.length;i++){
            	orderItems[i] = new OrderItem(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getDouble(5),rs.getInt(6),rs.getDouble(7) );
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return orderItems;
    }

    public boolean removeOrderItem(int orderID, int itemID){
    	try {
            String sql = "DELETE FROM OrdersItems WHERE OrderID = ? and catalogNumber=?;";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, orderID);
            pstmt.setInt(2,itemID );
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


}
