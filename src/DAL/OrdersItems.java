package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import SharedClasses.OrderItem;
import javafx.util.Pair;

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
          //  PreparedStatement ps = c.prepareStatement("INSERT INTO OrdersItems (OrderID, catalogNumber,SupplierID, ItemID, Quantity, FinalCost) " +
           //         "VALUES (?,?,?,?,?,?,?);");
            PreparedStatement ps = c.prepareStatement("INSERT INTO OrdersItems (OrderID, ItemID,Quantity, SupplierID, FinalCost) " +
                    "VALUES (?,?,?,?,?);");
            ps.setInt(1, orderItem.getOrderID());
            ps.setInt(2, orderItem.getItemID());
            ps.setInt(3,orderItem.getQuantity());
            ps.setInt(4,orderItem.getSupplierID());
            ps.setDouble(5,orderItem.getFinalCost());

            ps.executeUpdate();
            c.commit();
            ps.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public Pair[] getAllFinalPrices()
    {
        Pair[] allProducts;
        try {
            String sqlQuary = "SELECT OI.ItemID, OI.FinalCost FROM OrdersItems as OI JOIN Orders as O WHERE OI.OrderID = O.OrderID " +
                                    "GROUP BY OI.ItemID " +
                                    "HAVING MAX(OI.Date);";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            allProducts = new Pair[rs.getFetchSize()];
            for(int i=0; i<allProducts.length;i++){
                allProducts[i] = new Pair(rs.getInt(1),rs.getDouble(2));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) { return null; }
        return allProducts;
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
            	orderItems[i] = new OrderItem(rs.getInt(1),rs.getInt(2),
                        rs.getInt(3), rs.getInt(4),rs.getDouble(5) );
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

    public boolean checkItemExistInOrder(int orderID, int itemID){
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM OrdersItems where catalogNumber = '" + orderID + "' and itemID = '" + itemID + "';");
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
