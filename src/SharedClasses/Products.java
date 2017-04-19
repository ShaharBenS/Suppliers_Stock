package SharedClasses;


/**
 * Created by Shahar on 29/03/17.
 */
public class Products {
    private int id;
    private String location;
    private String manufacture;
    private int currentAmount;
    private int amountInWarehouse;
    private int amountInStore;
    private int minimalAmount;
    private int defectAmount;
    private int categoryCode;

    private int buyPrice;
    private int sellPrice;
    private Date dateStartDiscount;
    private Date dateEndDiscount;
    private int discount;

    public Products(int id, String location, String manufacture, int amountInStore, int amountInWarehouse, int minimalAmount, int categoryCode, int buyPrice, int sellPrice) {
        this.id = id;
        this.location = location;
        this.manufacture = manufacture;
        this.amountInWarehouse = amountInWarehouse;
        this.amountInStore = amountInStore;
        this.currentAmount = amountInWarehouse + amountInStore;
        /**/
        defectAmount = 0;
        this.categoryCode = categoryCode;
        this.minimalAmount = minimalAmount;

        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        /**/
        dateStartDiscount = null;
        /**/
        dateEndDiscount = null;
        /**/
        discount = 0;
    }

    public Products() {
        dateStartDiscount = null;
        dateEndDiscount = null;
        discount = 0;
        defectAmount = 0;
    }

    public boolean equals(Products products) {
        boolean condition1 = this.id == products.id &&
                this.location.equals(products.location) &&
                this.manufacture.equals(products.manufacture) &&
                this.currentAmount == products.currentAmount && this.amountInStore == products.amountInStore &&
                this.amountInWarehouse == products.amountInWarehouse && this.minimalAmount == products.minimalAmount &&
                this.defectAmount == products.defectAmount && this.categoryCode == products.categoryCode &&
                this.buyPrice == products.buyPrice && this.sellPrice == products.sellPrice
                && this.discount == products.discount;
        boolean condition2 = ((this.dateStartDiscount == null) ? null == products.dateStartDiscount :
                this.dateStartDiscount.equals(products.dateStartDiscount)) &&
                ((this.dateEndDiscount == null) ? null == products.dateEndDiscount :
                        this.dateEndDiscount.equals(products.dateEndDiscount));
        return condition1 && condition2;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getAmountInWarehouse() {
        return amountInWarehouse;
    }

    public void setAmountInWarehouse(int amountIntWarehouse) {
        this.amountInWarehouse = amountIntWarehouse;
    }

    public int getAmountInStore() {
        return amountInStore;
    }

    public void setAmountInStore(int amountInStore) {
        this.amountInStore = amountInStore;
    }

    public int getMinimalAmount() {
        return minimalAmount;
    }

    public void setMinimalAmount(int minimalAmount) {
        this.minimalAmount = minimalAmount;
    }

    public int getDefectAmount() {
        return defectAmount;
    }

    public void setDefectAmount(int defectAmount) {
        this.defectAmount = defectAmount;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Date getDateStartDiscount() {
        return dateStartDiscount == null ? null : new Date(dateStartDiscount);
    }

    public void setDateStartDiscount(String date_start) {
        if (date_start == null) {
            dateStartDiscount = null;
        } else {
            this.dateStartDiscount = date_start.equals("NULL") ? null : new Date(date_start);
        }
    }

    public void setDateStartDiscount(Date dateStartDiscount) {
        this.dateStartDiscount = new Date(dateStartDiscount);
    }

    public Date getDateEndDiscount() {
        return dateEndDiscount == null ? null : new Date(dateEndDiscount);
    }

    public void setDateEndDiscount(String date_end) {
        if (date_end == null) {
            dateEndDiscount = null;
        } else {
            this.dateEndDiscount = date_end.equals("NULL") ? null : new Date(date_end);
        }
    }

    public void setDateEndDiscount(Date dataEndDiscount) {
        this.dateEndDiscount = new Date(dataEndDiscount);
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        String s = "************************\n";
        s += "Product ID: " + id + "\n";
        s += "Location: " + location + "\n";
        s += "Manufacture: " + manufacture + "\n";
        s += "Current Amount: " + currentAmount + "\n";
        s += "Amount in Store: " + amountInStore + "\n";
        s += "Amount in Warehouse: " + amountInWarehouse + "\n";
        s += "Defect Amount: " + defectAmount + "\n";
        s += "Minimal Amount: " + minimalAmount + "\n";
        s += "Category Code: " + categoryCode + "\n";
        s += "Buy Price: " + buyPrice + "\n";
        s += "Sell Price: " + sellPrice + "\n";
        s += "Discount: " + discount + "%\n";
        if (dateStartDiscount == null) s += "Discount start date: NULL\n";
        else s += "Discount start date: " + dateStartDiscount.toString() + "\n";
        if (dateEndDiscount == null) s += "Discount end date: NULL\n";
        else s += "Discount end date: " + dateEndDiscount.toString() + "\n";
        s += "************************\n";
        return s;
    }

    public String toStringDefects() {
        String s = "************************\n";
        s += "Product ID: " + id + "\n";
        s += "Location: " + location + "\n";
        s += "Manufacture: " + manufacture + "\n";
        s += "Current Amount: " + currentAmount + "\n";
        s += "Amount in Store: " + amountInStore + "\n";
        s += "Amount in Warehouse: " + amountInWarehouse + "\n";
        s += "Defect Amount: " + defectAmount + "\n";
        s += "Category Code: " + categoryCode + "\n";
        s += "************************\n";
        return s;
    }

    public String toStringStock() {
        String s = "************************\n";
        s += "Product ID: " + id + "\n";
        s += "Current Amount: " + currentAmount + "\n";
        s += "Amount in Store: " + amountInStore + "\n";
        s += "Amount in Warehouse: " + amountInWarehouse + "\n";
        s += "Defect Amount: " + defectAmount + "\n";
        s += "Minimal Amount" + minimalAmount + "\n";
        s += "************************\n";
        return s;
    }
}