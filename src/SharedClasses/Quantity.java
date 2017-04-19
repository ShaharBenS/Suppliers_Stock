package SharedClasses;

/**
 * Created by Omri on 19-Apr-17.
 */
public class Quantity {

    private int id;
    private String location;
    private int defects;
    private int warehouse;
    private int minimum;
    private int store;
    private int amount_to_order;
    private int current;

    public Quantity(int id, String location, int defects, int warehouse, int minimum, int store, int amount_to_order)
    {
        this.id = id;
        this.location = location;
        this.defects = defects;
        this.warehouse = warehouse;
        this.minimum = minimum;
        this.store = store;
        this.amount_to_order = amount_to_order;
        UpdateCurrent();
    }

    private void UpdateCurrent() { this.current = store+warehouse+defects; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDefects() {
        return defects;
    }

    public void setDefects(int defects) {
        this.defects = defects;
        UpdateCurrent();
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
        UpdateCurrent();
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
        UpdateCurrent();
    }

    public int getAmount_to_order() {
        return amount_to_order;
    }

    public void setAmount_to_order(int amount_to_order) {
        this.amount_to_order = amount_to_order;
    }

    public int getCurrent() {
        return current;
    }

}
