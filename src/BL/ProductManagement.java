package BL;

import DAL.Items;
import DAL.Prices;
import DAL.Quantities;
import SharedClasses.Date;
import SharedClasses.Item;
import SharedClasses.Price;
import SharedClasses.Quantity;

/**
 * Created by Shahar on 29/03/17.
 */
public class ProductManagement {
    private Items ITEMS;
    private Prices PRICES;
    private Quantities QUANTITIES;
    private SupplierBL SBL;

    public ProductManagement(Items items, Prices prices, Quantities quantities ,SupplierBL sbl)
    {
        this.ITEMS = items;
        this.PRICES = prices;
        this.QUANTITIES = quantities;
        this.SBL = sbl;
    }

    // ADD NEW PRODUCT TO DATABASE
     // FORMAT: [0:ID] [1:LOCATION] [2:MANUFACTURE] [3:MINIMAL AMOUNT] [4:CATEGORY CODE] [5:NAME] [6:SELL PRICE]
    public boolean addProduct(String pLine) {
        String[] pParts = pLine.split("\\s+");
        if (pParts.length != 7) return false;
        Quantity quantity;
        Item item;
        Price price;
        try {
            /*0*/
            int id = Integer.parseInt(pParts[0]);
            if (pParts[0].length() != 6) return false;
            /*3*/
            int minimal = Integer.parseInt(pParts[3]);
            /*4*/
            int cCode = Integer.parseInt(pParts[4]);
            if (pParts[4].length() != 3) return false;
            int sell = Integer.parseInt(pParts[6]);
            item = new Item(id, pParts[5], cCode, pParts[2]);
            quantity = new Quantity(id, pParts[1],0,0,minimal,0,0);
            price = new Price(id, sell, 0, null,null);

        } catch (Exception e) {
            return false;
        }
        return (ITEMS.addItem(item) && PRICES.addItemPrice(price) && QUANTITIES.addItemQuantity(quantity));
    }

    //RETURN PRODUCT FROM DATABASE IF EXISTS, ELSE RETURN NULL
    public Products getProduct(int id) {
        return PD.getProduct(id);
    }

    public boolean updateProductId(String ids) {
        String[] sid = ids.split("\\s+");
        if (sid.length != 2) return false;
        try {
            int id = Integer.parseInt(sid[0]);
            int newId = Integer.parseInt(sid[1]);
            return PD.updateProductId(id, newId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateProductLocation(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            return PD.updateProductLocation(id, prop[1]);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateProductManufacture(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            return PD.updateProductManufacture(id, prop[1]);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateProductAmountInWarehouse(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int num1 = Integer.parseInt(prop[0]);
            int num2 = Integer.parseInt(prop[1]);
            boolean ans = PD.updateProductAmountInWarehouse(num1, num2);
            checkIfNeedToAlert(num1);
            return ans;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateProductAmountInStore(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int num1 = Integer.parseInt(prop[0]);
            int num2 = Integer.parseInt(prop[1]);
            Products p = PD.getProduct(num1);
            if (p.getAmountInStore() > num2) {
                return PD.updateProductAmountInStore(num1, num2);
            } else {
                if (p.getAmountInWarehouse() - num2 + p.getAmountInStore() < 0) return false;
                boolean ans = updateProductAmountInWarehouse("" + num1 + " " + (p.getAmountInWarehouse() - num2 + p.getAmountInStore()));
                if (!ans) return false;
                boolean ans2 = PD.updateProductAmountInStore(num1, num2);
                return ans2;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateProductDefectAmount(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int num1 = Integer.parseInt(prop[0]);
            int num2 = Integer.parseInt(prop[1]);
            return PD.updateProductDefectAmount(num1, num2);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateProductCategoryCode(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int num1 = Integer.parseInt(prop[0]);
            int num2 = Integer.parseInt(prop[1]);
            return PD.updateProductCategoryCode(num1, num2);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateProductMinimalAmount(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int num1 = Integer.parseInt(prop[0]);
            int num2 = Integer.parseInt(prop[1]);
            boolean ans = PD.updateProductMinimalAmount(num1, num2);
            checkIfNeedToAlert(num1);
            return ans;
        } catch (Exception e) {
            return false;
        }
    }

    public String productReport(String line) {
        if (line.length() != 6) return "Invalid ID";
        try {
            int id = Integer.parseInt(line);
            Products p = PD.getProduct(id);
            if (p == null) return "ID not found!";
            else return p.toStringStock();
        } catch (Exception e) {
            return "Invalid ID";
        }
    }

    public String[] getAllProducts() {
        Products[] pList = PD.getAllProducts();
        String[] allP = new String[pList.length];
        for (int i = 0; i < allP.length; i++)
            allP[i] = pList[i].toString();
        return allP;
    }

    public String[] getAllDefectProducts() {
        Products[] pList = PD.getAllDefectProducts();
        if (pList.length == 0) return new String[]{"No Defects found !"};
        String[] allP = new String[pList.length];
        for (int i = 0; i < allP.length; i++)
            allP[i] = pList[i].toStringDefects();
        return allP;
    }

    private void checkIfNeedToAlert(int id) {
        Products products = PD.getProduct(id);
        if (products.getAmountInWarehouse() <= products.getMinimalAmount()) {
            int supplierID = SBL.getSupplierID(id);
            int orderID = SBL.addOrder(supplierID,new Date(new java.util.Date()));
            SBL.addOrderItem(orderID,supplierID,id, -1 ); //TODO Order quantity
        }
    }


}