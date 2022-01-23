package ua.goit.shop;

import java.util.Locale;
import java.util.Objects;

public class Product {


    private String productId;
    private double price;
    private double promoPrice;
    private long qty;

    public Product(String productId, double price, double promoPrice, long promoQty) {

        if (isProductValid(productId) && isPriceValid(price) && isPriceValid(promoPrice) && isQtyValid(promoQty)) {
            this.productId = productId;
            this.price = price;
            this.promoPrice = promoPrice;
            this.qty = promoQty;
        }
    }

    public Product(String productId, double price) {
        this(productId, price, price, Long.MAX_VALUE);
    }

    public String getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        if (isPriceValid(price)) {
            this.price = price;
        }
    }

    public void setPromoPrice(double promoPrice) {
        if (isPriceValid(promoPrice)) {
            this.promoPrice = promoPrice;
        }
    }

    public void setQty(long qty) {
        if (isQtyValid(qty)) {
            this.qty = qty;
        }
    }

    public double getPromoPrice() {
        return promoPrice;
    }

    public long getQty() {
        return qty;
    }

    private boolean isPriceValid(double price) {
        if (price > 0d) {
            return true;
        } else {
            throw new RuntimeException("Price is not valid");
        }
    }

    private boolean isQtyValid(long qty) {
        if (qty > 0L) {
            return true;
        } else {
            throw new RuntimeException("Quantity is not valid");
        }
    }

    private boolean isProductValid(String productId) {
        String abc = "ABCDEFGHIGKLMNOPQRSTUWVXYZ";
        if (productId != null && productId.length() == 1 && abc.contains(productId.toUpperCase(Locale.ROOT))) {
            return true;
        } else {
            throw new RuntimeException("Product ID is not valid");
        }
    }

    public double getPriceByQty(long gqty) {
        if (gqty < 0L) {
            throw new RuntimeException("Quantity is less than 0");
        }
        if (gqty < qty) {
            return price * gqty;
        } else {
            return (gqty / qty) * promoPrice + (gqty % qty) * price;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product product)) {
            return false;
        }
//        Product product = (Product) o;
        return Double.compare(product.getPrice(), getPrice()) == 0 && Double.compare(product.getPromoPrice(), getPromoPrice()) == 0
                && getQty() == product.getQty() && Objects.equals(getProductId(), product.getProductId());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getPromoPrice(), getPrice(), getProductId(), getQty());
    }
}
