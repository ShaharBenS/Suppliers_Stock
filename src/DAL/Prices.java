package DAL;

import SharedClasses.Price;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by Shahar on 21/04/17.
 */
public class Prices
{
    private Connection conn;

    public Prices(Connection conn) {
        this.conn = conn;
    }

    public boolean addItemPrice(Price price)
    {
        try
        {
            PreparedStatement p_stmt = conn.prepareStatement("INSERT INTO PRICES(ItemID,OrderID,SellPrice,BuyPrice,DateStart," +
                    "DateEnd) VALUES(?,?,?,?,?,?);");
            p_stmt.setInt(1,price.getItem_id());
            p_stmt.setInt(2,price.getOrder_id());
            p_stmt.setInt(3,price.getSell_price());
            p_stmt.setInt(4,price.getBuy_price());
            p_stmt.setDate(5,price.getStart().toSQLdate());
            p_stmt.setDate(6, price.getEnd().toSQLdate());
            p_stmt.executeUpdate();

            conn.commit();
            p_stmt.close();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
