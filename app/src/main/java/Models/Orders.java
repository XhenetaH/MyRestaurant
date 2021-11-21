package Models;

import java.util.ArrayList;

public class Orders {

    private String ID;
    public static String Table="";
    private String TableID;
    private double Total;
    private ArrayList<MenuItemDetails> MenuItemList;
    private String Status;

    public Orders(){

    }

    public ArrayList<MenuItemDetails> getMenuItemList() {
        return MenuItemList;
    }

    public void setMenuItemList(ArrayList<MenuItemDetails> menuItemList) {
        MenuItemList = menuItemList;
    }

    public Orders(String ID, String tableID, double total, ArrayList<MenuItemDetails> menuItemList) {
        this.ID = ID;
        TableID = tableID;
        Total = total;
        MenuItemList = menuItemList;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTableID() {
        return TableID;
    }

    public void setTableID(String tableID) {
        TableID = tableID;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


}
