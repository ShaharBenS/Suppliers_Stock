package SharedClasses;

/**
 * Created by keren on 4/6/2017.
 */
public class Item {
    private int itemID;
    private int categoryNumber;
    private String name;
    private String manufacture;

    public Item(int itemID,  String name, int categoryNumber, String manufacture) {
        this.itemID = itemID;
        this.categoryNumber = categoryNumber;
        this.name = name;
        this.manufacture = manufacture;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }
}