package DAL;

import SharedClasses.*;
import SharedClasses.Date;

import java.sql.*;

/**
 * Created by Shahar on 21/04/17.
 */
public class Prices
{
    private Connection conn;
    private Categories CATEGORIES;

    public Prices(Connection conn, Categories categories) {
        this.conn = conn;
        CATEGORIES = categories;
    }

    /*
        returning price object or null if id does not exist
     */
    public Price getPrice(int id)
    {
        Price price = null;

        try
        {
            String query = "SELECT * FROM PRICES AS P WHERE Pa.itemID = "+id+";";
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            price = new Price(resultSet.getInt("ItemID"),resultSet.getInt("SellPrice"),
                    resultSet.getInt("Percentage"),new Date(resultSet.getDate("DateStart")),
                    new Date(resultSet.getDate("DateEnd")));
            stmt.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return price;
    }

    public boolean addItemPrice(Price price)
    {
        try
        {
            PreparedStatement p_stmt = conn.prepareStatement("INSERT INTO PRICES (ItemID,SellPrice,Percentage,DateStart," +
                    "DateEnd) VALUES(?,?,?,?,?);");
            p_stmt.setInt(1,price.getItemID());
            p_stmt.setInt(2,price.getSell_price());
            p_stmt.setInt(3,price.getPerecentage());
            p_stmt.setDate(4,price.getStart() == null ? null : price.getStart().toSQLdate());
            p_stmt.setDate(5, price.getEnd() == null ? null : price.getEnd().toSQLdate());
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
    private boolean updateField(String fieldName,int orderID,Object newValue)
    {
        String query = "UPDATE PRICES SET "+fieldName+" = '"+newValue+"' WHERE ID = "+orderID+";";
        try
        {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query) > 0;
        }
        catch (SQLException e)
        {
            return false;
        }
    }
    public boolean updateSellPrice(int orderId,int sellPrice)
    {
        return updateField("SellPrice",orderId,sellPrice);
    }
    public boolean updatePercentage(int orderId,int percentage)
    {
        return updateField("Percentage",orderId,percentage);
    }
    public boolean updateDate(boolean startOrEnd, int orderId, Date newDate)
    {
        return startOrEnd ? updateField("DateStart",orderId,newDate) : updateField("DateEnd",orderId,newDate);
    }

    public boolean updateCategoryDiscount(int id, int discount, Date start, Date end)
    {
        Item[] products = CATEGORIES.getAllProductsbyCat(new Category[]{new Category(id,"")});
        for (Item item : products) {
            updatePercentage(item.getItemID(), discount);
            updateDate(true,item.getItemID(), start);
            updateDate(false,item.getItemID(), end);
        }
        return true;
    }

}
