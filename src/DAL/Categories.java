package DAL;

import SharedClasses.Category;
import SharedClasses.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shahar on 29/03/17.
 */
public class Categories
{
    private Connection connection;
    public Categories(Connection c)
    {
        connection = c;
    }


    public boolean addCategory(Category c)
    {
        String query = "INSERT INTO CATEGORY(ID,NAME,ID_FATHER) VALUES(?,?,?)";
        try {
            PreparedStatement _ps = connection.prepareStatement(query);
            _ps.setInt(1, c.getId());
            _ps.setString(2, c.getName());
            if(c.getIdFather()!=-1)
                _ps.setInt(3, c.getIdFather());

            _ps.executeUpdate();
            return true;

        } catch (SQLException e)
        {
            return false;
        }

    }
    public boolean categoryExists(int id)
    {
        String query = "SELECT * FROM CATEGORY WHERE ID = "+id+";";
        try
        {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
    /*
        If category not found returns NULL
     */
    public Category getCategory(int id)
    {
        String query = "SELECT * FROM CATEGORY WHERE ID = "+id+";";
        Category category = null;
        try
        {
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery(query);

            int catID = result.getInt("ID");
            String catName = result.getString("NAME");
            category = new Category(catID, catName);
            category.setIdFather(result.getString("ID_FATHER") == null
            ? -1 : result.getInt("ID_FATHER"));

        } catch (SQLException e) {
            return category;
        }
        return category;
    }
    public boolean updateCategoryId(int id,int newID)
    {
        if(getCategory(id) == null) return false;
        String query = "UPDATE CATEGORY SET ID = ? WHERE ID = ?";
        try {
            PreparedStatement _ps = connection.prepareStatement(query);
            _ps.setInt(1, newID);
            _ps.setInt(2, id);
            int result = _ps.executeUpdate();
            return result > 0;

        } catch (SQLException e)
        {
            return false;
        }
    }
    public boolean updateCategoryName(int id,String name)
    {
        String query = "UPDATE CATEGORY SET NAME = ? WHERE ID = ?";
        try {
            PreparedStatement _ps = connection.prepareStatement(query);
            _ps.setString(1, name);
            _ps.setInt(2, id);
            return _ps.executeUpdate() > 0;

        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateCategoryIdFather(int id,int newFatherId)
    {
        String query = "UPDATE CATEGORY SET ID_FATHER = ? WHERE ID = ?";
        try {
             PreparedStatement _ps = connection.prepareStatement(query);
            _ps.setInt(1, newFatherId);
            _ps.setInt(2, id);
            return _ps.executeUpdate()>0;


        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public Item[] getAllProductsbyCat(Category[] cArr)
    {
        Item [] products = {};
        List<Item> productsList = new ArrayList<>();
        int index = 0;
        int c_index = 0;
        try {
            while (cArr.length != 0)
            {
                String query1 = "SELECT ITEMS.ID" +
                                "FROM ITEMS" +
                                "WHERE (";
                for (int i = 0; i < cArr.length; i++) {
                    if (i == cArr.length - 1) {
                        query1 += "ITEMS.CategoryNumber = " + cArr[i].getId() + " );";
                        break;
                    }
                    query1 += "ITEMS.CategoryNumber = " + cArr[i].getId() + " OR ";
                }

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query1);
                while(resultSet.next())
                {
                    productsList.add(buildItemFromResultSet(resultSet));
                    index++;
                }
                List<Category> c_list = new ArrayList<>();
                String query2 = "SELECT C.* " +
                        "FROM CATEGORY AS C " +
                        "WHERE ( ";
                for(int i = 0 ; i < cArr.length; i++)
                {
                    if(i == cArr.length-1)
                    {
                        query2+= "C.ID_FATHER = "+cArr[i].getId()+");";
                        break;
                    }
                    query2 += "C.ID_FATHER = "+cArr[i].getId()+" OR ";
                }
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query2);
                while(resultSet.next())
                {
                    c_list.add(buildCategoryFromResultSet(resultSet));
                    c_index++;
                }
                cArr = new Category[c_index];
                c_index = 0;
                cArr = c_list.toArray(cArr);

            }
            products = new Item[index];
            products = productsList.toArray(products);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return products;
    }

    private Category buildCategoryFromResultSet(ResultSet resultSet)
    {
        Category c = new Category();

        try
        {
            c.setId(resultSet.getInt("ID"));
            c.setName(resultSet.getString("NAME"));
            c.setIdFather(resultSet.getInt("ID_FATHER"));

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return c;
    }

    private Item buildItemFromResultSet(ResultSet resultSet)
    {
        Item item = new Item();

        try{
            item.setItemID(resultSet.getInt("ID"));
            item.setName(resultSet.getString("NAME"));
            item.setCategoryNumber(resultSet.getInt("CategoryNumber"));
            item.setManufacture(resultSet.getString("Manufacture"));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return item;
    }
}