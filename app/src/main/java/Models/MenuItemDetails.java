package Models;

public class MenuItemDetails {
    private String ID;
    private String ImageUrl;
    private String Name;
    private double Price;
    private double TotalPrice;
    private int Quantity;

    public MenuItemDetails(){

    }

    public MenuItemDetails(String Id,String imageUrl, String name, double price, double totalPrice, int quantity) {
        ImageUrl = imageUrl;
        Name = name;
        Price = price;
        TotalPrice = totalPrice;
        Quantity = quantity;
        ID=Id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
