package SharedClasses;

/**
 * Created by rotem on 07/04/2017.
 */
public class OrderItem {
    private int orderID;
    private int catalogNumber;
    private int SupplierID;
    private int ItemID;
    private int quantity;
    private double cost;
    private int discount;
    private double finalCost;


    public OrderItem(int orderID, int supplierID,int ItemID, int quantity, double finalCost){
        this.orderID = orderID;
        this.SupplierID = supplierID;
        this.ItemID = ItemID;
        this.quantity = quantity;
        this.finalCost = finalCost;
    }


    public int getOrderID(){return orderID;}

    public void setOrdetID(int orderID){this.orderID=orderID;}

    public int getCatalogNumber(){return  catalogNumber;}

    public void setCatalogNumber(int catalogNumber){this.catalogNumber=catalogNumber;}

    public int getItemID(){return ItemID;}

    public void setItemID(int itemID){this.ItemID = itemID;}

    public int getQuantity(){return quantity;}

    public void setQuantity(int quantity){this.quantity = quantity;}

    public double getCost(){return cost;}

    public void setCost(double cost){this.cost = cost;}

    public int getDiscount(){return discount;}

    public void setDiscount(int discount){this.discount= discount;}

    public double getFinalCost(){return finalCost;}

    public void setFinalCost(double finalCost){this.finalCost = finalCost;}


    public int getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(int supplierID) {
        SupplierID = supplierID;
    }
}
