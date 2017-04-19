package BL;

import DAL.Product_Data;
import SharedClasses.Date;
import SharedClasses.Products;
import SharedClasses.Supplier;

/**
 * Created by Shahar on 29/03/17.
 */
public class ProductManagement {
    Product_Data PD;
    SupplierBL SBL;

    public ProductManagement(Product_Data PD,SupplierBL sbl)
    {
        this.PD = PD;
        this.SBL = sbl;
    }

    // ADD NEW PRODUCT TO DATABASE
    // FORMAT: [0:ID] [1:LOCATION] [2:MANUFACTURE] [3:CURRENT AMOUNT] [4:MINIMAL AMOUNT] [5:CATEGORY CODE] [6:BUY PRICE] [7:SELL PRICE]
    public boolean addProduct(String pLine) {
        String[] pParts = pLine.split("\\s+");
        if (pParts.length != 8) return false;
        Products p;
        try {
            /*0*/
            int id = Integer.parseInt(pParts[0]);
            if (pParts[0].length() != 6) return false;
            /*1*//*2*/ /* << NOTHING TO CHECK << */
            /*3*/
            int amount = Integer.parseInt(pParts[3]);
            /*4*/
            int minimal = Integer.parseInt(pParts[4]);
            /*5*/
            int cCode = Integer.parseInt(pParts[5]);
            /*6*/
            int buy = Integer.parseInt(pParts[6]);
            /*7*/
            int sell = Integer.parseInt(pParts[7]);
            if (pParts[5].length() != 3) return false;


            p = new Products(id, pParts[1], pParts[2], 0, amount, minimal, cCode, buy, sell);

        } catch (Exception e) {
            return false;
        }
        return PD.addProduct(p);
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