package com.example.myrestaurant;

import android.net.Uri;

public class Category {
    private String ID;
    private String Name;
    private String Description;
    private String ImageUrl;
    private Uri ImageUri;

    public Category(){}

    public Category(String ID, String name, String description, String imageUrl, Uri imageUri) {
        this.ID = ID;
        Name = name;
        Description = description;
        ImageUrl = imageUrl;
        ImageUri = imageUri;
    }

    public Category(String id, String name, String description, String imageUrl) {
        Name = name;
        Description = description;
        ImageUrl = imageUrl;
        ID = id;


    }

    public Uri getImageUri() {
        return ImageUri;
    }

    public void setImageUri(Uri imageUri) {
        ImageUri = imageUri;
    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
