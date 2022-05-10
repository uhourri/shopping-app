package ma.fsdm.wisd.shopping.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Command implements Serializable {
    User user;
    Product product;
    int quantity;
    String date;

    public Command(User user, Product product, int quantity) {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.date = simpleDateFormat.format(new Date());
    }

    public Command() {
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Command{" +
                "user=" + user +
                ", product=" + product +
                ", quantity=" + quantity +
                ", date='" + date + '\'' +
                '}';
    }
}
