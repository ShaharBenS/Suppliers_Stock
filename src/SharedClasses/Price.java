package SharedClasses;

/**
 * Created by Omri on 19-Apr-17.
 */
public class Price
{
    private int order_id;
    private int sell_price;
    private int buy_price;
    private int perecentage;
    private Date start;
    private Date end;

    public Price(int item_id, int order_id, int sell_price, int buy_price, int perecentage, Date start, Date end) {
        this.order_id = order_id;
        this.sell_price = sell_price;
        this.buy_price = buy_price;
        this.perecentage = perecentage;
        this.start = start;
        this.end = end;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }

    public int getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(int buy_price) {
        this.buy_price = buy_price;
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
