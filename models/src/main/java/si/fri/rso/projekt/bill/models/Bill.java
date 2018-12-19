package si.fri.rso.projekt.bill.models;

public class Bill {

    private double price;
    private boolean paid;
    private String date;
    private int menuID;
    private int orderID;

    public Bill(int orderID, int menuID, double price, String date, boolean paid) {
        this.price = price;
        this.paid = paid;
        this.date = date;
        this.menuID = menuID;
        this.orderID = orderID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
