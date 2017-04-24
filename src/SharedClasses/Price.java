package SharedClasses;

/**
 * Created by Omri on 19-Apr-17.
 */
public class Price
{
    private int itemID;
    private int sell_price;
    private int perecentage;
    private Date start;
    private Date end;

    public Price(int item_id, int itemID, int sell_price, int buy_price, int perecentage, Date start, Date end) {
        this.itemID = itemID;
        this.sell_price = sell_price;
        this.perecentage = perecentage;
        this.start = start;
        this.end = end;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }

    public int getPerecentage() {
        return perecentage;
    }

    public void setPerecentage(int perecentage) {
        this.perecentage = perecentage;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


}
