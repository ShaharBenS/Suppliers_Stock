package BL;

import DAL.Category_Data;
import DAL.Product_Data;
import SharedClasses.Category;
import SharedClasses.Products;

/**
 * Created by Shahar on 29/03/17.
 */
//hi
public class CategoryManagement {
    private Category_Data _CD;
    private Product_Data PD;

    public CategoryManagement(Category_Data cd, Product_Data pd) {
        _CD = cd;
        this.PD = pd;
    }

    public boolean addCategory(String line) {
        String[] cParts = line.split("\\s+");
        if (cParts.length != 2 && cParts.length != 3) return false;
        Category c;
        try {
            /*0*/
            int id = Integer.parseInt(cParts[0]);
            if (cParts[0].length() != 3) return false;
            /*1*/ /* << NOTHING TO CHECK << */
            int idF = -1;
            if (cParts.length == 3) {
                if (cParts[2].length() != 3) return false;
                else idF = Integer.parseInt(cParts[2]);
            }

            c = new Category(id, cParts[1], idF);

        } catch (Exception e) {
            return false;
        }
        return _CD.addCategory(c);
    }

    /*
        If category not found returns NULL
     */
    public Category getCategory(int id) {
        return _CD.getCategory(id);
    }

    public boolean updateCategoryId(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            if (prop[0].length() != 3 || prop[1].length() != 3) return false;
            int id = Integer.parseInt(prop[0]);
            int nid = Integer.parseInt(prop[1]);
            return _CD.updateCategoryId(id, nid);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateCategoryName(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            if (prop[0].length() != 3) return false;
            int id = Integer.parseInt(prop[0]);
            return _CD.updateCategoryName(id, prop[1]);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateCategoryIdFather(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            if (prop[0].length() != 3 || prop[1].length() != 3) return false;
            int id = Integer.parseInt(prop[0]);
            int nid = Integer.parseInt(prop[1]);
            return _CD.updateCategoryIdFather(id, nid);
        } catch (Exception e) {
            return false;
        }
    }

    public String[] productReportbyCategory(String line) {
        String[] cats = line.split("\\s+");
        Category[] cArr = new Category[cats.length];
        for (int i = 0; i < cArr.length; i++) {
            int id = 0;
            try {
                id = Integer.parseInt(cats[i]);
            } catch (Exception e) {
                return new String[]{"1 or more INVALID ID"};
            }
            if (cats[i].length() != 3) return new String[]{"1 or more INVALID ID"};
            cArr[i] = _CD.getCategory(id);
        }
        Products[] products = PD.getAllProductsbyCat(cArr);
        String[] plist = new String[products.length];
        for (int i = 0; i < plist.length; i++)
            plist[i] = products[i].toString();
        return plist;
    }

}