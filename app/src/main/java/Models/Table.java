package Models;

public class Table {
    private String ID;
    private String Name;
    private String ImageUrl;
    private String Status;

    public Table(){}

    public Table(String ID, String name, String imageUrl,String status) {
        this.ID = ID;
        Name = name;
        ImageUrl = imageUrl;
        this.Status = status;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
