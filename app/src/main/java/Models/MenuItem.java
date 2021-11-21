package Models;

import android.view.Display;

public class MenuItem {
    private String Name;
    private String ImageUrl;
    private double Price;
    private String Description;
    private String Category;
    private String Id;

    public MenuItem(){}

    public MenuItem(String name, String imageUrl, double price, String description, String category,String id) {
        Name = name;
        ImageUrl = imageUrl;
        Price = price;
        Description = description;
        Category = category;
        Id=id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
