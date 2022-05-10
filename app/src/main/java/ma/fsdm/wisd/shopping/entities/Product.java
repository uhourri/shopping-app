package ma.fsdm.wisd.shopping.entities;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Product implements Serializable {
    String reference;
    String name;
    String description;
    int price;
    Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Product(String reference, String name, String description, int price, Bitmap image) {
        this.reference = reference;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Product() {
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "reference='" + reference + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
