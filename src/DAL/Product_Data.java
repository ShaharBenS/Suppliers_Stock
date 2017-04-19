package DAL;

import SharedClasses.Category;
import SharedClasses.Date;
import SharedClasses.Products;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shahar on 29/03/17.
 */
public class Product_Data
{

    private Connection connection;
    public Product_Data(Connection c)
    {
        connection = c;
    }

    /* DATE FOR REGULAR PROPERTIES */

    // ADD NEW PRODUCT TO DATABASE
    public boolean addProduct(Products p)
    {
        String query1 = "INSERT INTO PRODUCTS(ID, LOCATION, MANUFACTURE, AMOUNT_STORE, AMOUNT_STORAGE, AMOUNT_DEFECT" +
                ", MINIMAL_AMOUNT, CATEGORY_CODE) VALUES(?,?,?,?,?,?,?,?)";
        String query2 = "INSERT INTO PRODUCTS_PRICE(ID,PRICE_COST,PRICE_SELL,DISCOUNT,DATE_START,DATE_END) " +
                "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement _ps = connection.prepareStatement(query1);

            _ps.setInt(1,p.getId());
            _ps.setString(2,p.getLocation());
            _ps.setString(3,p.getManufacture());
            _ps.setInt(4, p.getAmountInStore());
            _ps.setInt(5, p.getAmountInWarehouse());
            _ps.setInt(6, p.getDefectAmount());
            _ps.setInt(7, p.getMinimalAmount());
            _ps.setInt(8, p.getCategoryCode());

            _ps.executeUpdate();

            _ps = connection.prepareStatement(query2);

            _ps.setInt(1,p.getId());
            _ps.setInt(2,p.getBuyPrice());
            _ps.setInt(3,p.getSellPrice());
            _ps.setInt(4,p.getDiscount());
            _ps.setString(5,p.getDateStartDiscount() == null ? "NULL" :
                                                            p.getDateStartDiscount().toString());
            _ps.setString(6,p.getDateEndDiscount() == null ? "NULL" :
                                            p.getDateEndDiscount().toString());

            _ps.executeUpdate();

            return true;

        } catch (SQLException e)
        {
            return false;
        }
    }

    public Products[] getAllDefectProducts()
    {
        Products[] products = null; //TODO: <<< fix this <<<
        List<Products> productsList = new ArrayList<Products>();
        String query =  "SELECT * " +
                        "FROM PRODUCTS CROSS JOIN PRODUCTS_PRICE " +
                        "WHERE AMOUNT_DEFECT > 0 AND PRODUCTS.ID = PRODUCTS_PRICE.ID";

        try
        {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            int index = 0;
            while(result.next())
            {
                productsList.add(buildProductFromResultSet(result));
                index++;
            }
            products = new Products[index];
            return productsList.toArray(products);

        } catch (SQLException e)
        {
            return null;
        }
    }

    /*
        This method gets a result Set and build a products Object which will then be returned
     */
    private Products buildProductFromResultSet(ResultSet result)
    {
        Products p = new Products();

        try {
            p.setId(result.getInt("ID"));
            p.setLocation(result.getString("LOCATION"));
            p.setManufacture(result.getString("MANUFACTURE"));
            p.setAmountInStore(result.getInt("AMOUNT_STORE"));
            p.setAmountInWarehouse(result.getInt("AMOUNT_STORAGE"));
            p.setDefectAmount(result.getInt("AMOUNT_DEFECT"));
            p.setMinimalAmount(result.getInt("MINIMAL_AMOUNT"));
            p.setCategoryCode(result.getInt("CATEGORY_CODE"));
            p.setCurrentAmount(p.getDefectAmount() + p.getAmountInStore() + p.getAmountInWarehouse());

            p.setBuyPrice(result.getInt("PRICE_COST"));
            p.setSellPrice(result.getInt("PRICE_SELL"));
            p.setDiscount(result.getInt("DISCOUNT"));
            p.setDateStartDiscount(result.getString("DATE_START"));
            p.setDateEndDiscount(result.getString("DATE_END"));
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return p;
    }
    private Category buildCategoryFromResultSet(ResultSet result)
    {
        Category c = new Category();

        try
        {
            c.setId(result.getInt("ID"));
            c.setName(result.getString("NAME"));
            String id_father = result.getString("ID_FATHER");
            if(id_father == null)
            {
                c.setIdFather(-1);
            }
            else
            {
                c.setIdFather(Integer.parseInt(id_father));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return c;
    }
    public Products[] getAllProducts()
    {
        Products [] products = null;
        String query =  "SELECT * " +
                        "FROM PRODUCTS CROSS JOIN PRODUCTS_PRICE " +
                        "WHERE PRODUCTS.ID = PRODUCTS_PRICE.ID";

        try
        {
            List<Products> productsArrayList = new ArrayList<Products>();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            int index = 0;
            while(result.next())
            {
                productsArrayList.add(buildProductFromResultSet(result));
                index++;
            }
            products = new Products[index];
            products = productsArrayList.toArray(products);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return products;
    }

    public Products[] getAllProductsbyCat(Category[] c)
    {
        Products [] products = {};
        List<Products> productsList = new ArrayList<Products>();
        int index = 0;
        int c_index = 0;
        try {
            while (c.length != 0)
            {
                String query1 = "SELECT PRODUCTS.*,PRODUCTS_PRICE.*" +
                        "FROM PRODUCTS CROSS JOIN PRODUCTS_PRICE CROSS JOIN CATEGORY " +
                        "WHERE PRODUCTS.ID = PRODUCTS_PRICE.ID AND PRODUCTS.CATEGORY_CODE = CATEGORY.ID " +
                        "AND (";
                for (int i = 0; i < c.length; i++) {
                    if (i == c.length - 1) {
                        query1 += "CATEGORY.ID = " + c[i].getId() + " );";
                        break;
                    }
                    query1 += "CATEGORY.ID = " + c[i].getId() + " OR ";
                }

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query1);
                while(resultSet.next())
                {
                    productsList.add(buildProductFromResultSet(resultSet));
                    index++;
                }
                List<Category> c_list = new ArrayList<Category>();
                String query2 = "SELECT C.* " +
                                "FROM CATEGORY AS C " +
                                "WHERE ( ";
                for(int i = 0 ; i < c.length; i++)
                {
                    if(i == c.length-1)
                    {
                        query2+= "C.ID_FATHER = "+c[i].getId()+");";
                        break;
                    }
                    query2 += "C.ID_FATHER = "+c[i].getId()+" OR ";
                }
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query2);
                while(resultSet.next())
                {
                    c_list.add(buildCategoryFromResultSet(resultSet));
                    c_index++;
                }
                c = new Category[c_index];
                c_index = 0;
                c = c_list.toArray(c);

            }
            products = new Products[index];
            products = productsList.toArray(products);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return products;
    }

    public boolean updateCategoryDiscount(int id,int discount, Date start, Date end)
    {
        Products [] products = getAllProductsbyCat(new Category[]{new Category(id,"")});
        for (Products product : products) {
            updateProductDiscount(product.getId(), discount);
            updateStartDate(product.getId(), start);
            updateEndDate(product.getId(), end);
        }
        return true;
    }

    //RETURN PRODUCT FROM DATABASE IF EXISTS, ELSE RETURN NULL
    public Products getProduct(int id)
    {
        String query = "SELECT * " +
                        "FROM PRODUCTS_PRICE CROSS JOIN PRODUCTS " +
                        "WHERE PRODUCTS.ID = PRODUCTS_PRICE.ID AND PRODUCTS.ID = "+id+";";
        Products products = null;
        try
        {
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery(query);

            products = buildProductFromResultSet(result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return products;
    }

    /*
     * This method gets the name of the column the id of the products and the new value.
     */
    private boolean updateColumnInProduct(String columnName, int id, Object newValue)
    {
        String query = "UPDATE PRODUCTS SET "+columnName+" = '"+newValue+"' WHERE ID = "+id+";";
        try {
            Statement _ps = connection.createStatement();
            int result = _ps.executeUpdate(query);
            if(result <= 0)
            {
                return false;
            }
            return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateProductId(int id, int newId)
    {
        return updateColumnInProduct("ID",id,newId);
    }

    public boolean updateProductLocation(int id, String location)
    {
        return updateColumnInProduct("LOCATION",id,location);
    }

    public boolean updateProductManufacture(int id, String manufacture)
    {
        return updateColumnInProduct("MANUFACTURE",id,manufacture);
    }

    public boolean updateProductAmountInWarehouse(int id, int amount)
    {
        return updateColumnInProduct("AMOUNT_STORAGE",id,amount);
    }

    public boolean updateProductAmountInStore(int id, int amount)
    {
        return updateColumnInProduct("AMOUNT_STORE",id,amount);
    }

    public boolean updateProductDefectAmount(int id, int amount)
    {
        return updateColumnInProduct("AMOUNT_DEFECT",id,amount);
    }

    public boolean updateProductCategoryCode(int id, int categoryCode)
    {
        return updateColumnInProduct("CATEGORY_CODE",id,categoryCode);
    }

    public boolean updateProductMinimalAmount(int id, int amount)
    {
        return updateColumnInProduct("MINIMAL_AMOUNT",id,amount);
    }


    /*
     * This method gets the name of the column the id of the products_price and the new value.
     */
    private boolean updateColumnInProductPrice(String columnName, int id, Object newValue)
    {
        String query = "UPDATE PRODUCTS_PRICE SET "+columnName+" = '"+newValue+"' WHERE ID = "+id+";";
        try {
            Statement _ps = connection.createStatement();
            _ps.executeUpdate(query);
            return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    /* DATA FOR PRICES */

    public boolean updateBuyPrice(int id,int price)
    {
        return updateColumnInProductPrice("PRICE_COST",id,price);
    }

    public boolean updateSellPrice(int id,int price)
    {
        return updateColumnInProductPrice("PRICE_SELL",id,price);
    }

    public boolean updateStartDate(int id, Date start)
    {
        return updateColumnInProductPrice("DATE_START",id,start);
    }

    public boolean updateEndDate(int id,Date end)
    {
        return updateColumnInProductPrice("DATE_END",id,end);
    }

    public boolean updateProductDiscount(int id,int discount)
    {
        return updateColumnInProductPrice("DISCOUNT",id,discount);
    }
}