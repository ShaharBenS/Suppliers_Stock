package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import SharedClasses.OrderItem;
/**
 * Created by rotem on 07/04/2017.
 */
public class OrdersItems {

    private Connection c;

    public OrdersItems(Connection c) {
        this.c = c;
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



}
