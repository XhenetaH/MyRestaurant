package Models;

import java.util.ArrayList;

public class Orders {

    private String ID;
    public static String Table="";
    private String TableID;
    private double Total;

    public Orders(){

    }

    public Orders(double total, String ID,String tableID) {
        this.Total = total;
        this.ID = ID;
        this.TableID = tableID;

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
