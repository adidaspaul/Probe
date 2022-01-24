import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.goit.shop.Product;
import static org.assertj.core.api.Assertions.*;

public class ProductTest {

    private static final double MARGIN = 0.000000001;
    private static Product testP;


    @BeforeEach
    void setUp(){
        testP = new Product("H", 6d, 14d, 15);
    }

    @Test
    void setPrice(){
        testP.setPrice(6d);
        assertThat(testP.getPrice()).isEqualTo(6d, offset (MARGIN));

        assertThatThrownBy(() -> testP.setPrice(-1)).isInstanceOf(RuntimeException.class)
                .hasMessage("Price is not valid");
    }

    @Test
    void setPromoPrice() {
        testP.setPromoPrice(14d);
        assertThat(testP.getPromoPrice()).isEqualTo(14d, offset(MARGIN));

        assertThatThrownBy(() -> testP.setPrice(0d)).isInstanceOf(RuntimeException.class)
                .hasMessage("Price is not valid");
    }

    @Test
    void setQty(){
        testP.setQty(4);
        assertThat(testP.getQty()).isEqualTo(4);

        assertThatThrownBy(() -> testP.setQty(0)).isInstanceOf(RuntimeException.class)
                .hasMessage("Quantity is not valid");

    }

    @Test
    void getPriceByQty(){
        assertThat(testP.getPriceByQty(1L)).isEqualTo(6d, offset(MARGIN));
        assertThat(testP.getPriceByQty(2L)).isEqualTo(12d, offset(MARGIN));
        assertThat(testP.getPriceByQty(5L)).isEqualTo(30d, offset(MARGIN));
        assertThat(testP.getPriceByQty(20L)).isEqualTo(44d, offset(MARGIN));
        assertThat(testP.getPriceByQty(0)).isEqualTo(0, offset(MARGIN));

        assertThatThrownBy(() -> testP.getPriceByQty(-1L)).isInstanceOf(RuntimeException.class)
                .hasMessage("Quantity is less than 0");
    }

    @Test
    void testEquals(){
        Product productReplica = testP;
        Product copyOfProduct = new Product("H", 6d, 14d, 15);
        Product copyOfProduct2 = new Product("T", 6d, 14d, 15);
        Product copyOfProduct3 = new Product("H", 7d, 14d, 15);
        Product copyOfProduct4 = new Product("H", 6d, 10d, 15);
        Product copyOfProduct5 = new Product("H", 6d, 14d,14);

        assertThat(testP).isEqualTo(testP);
        assertThat(testP).isEqualTo(productReplica);
        assertThat(testP).isEqualTo(copyOfProduct);
        assertThat(testP).isNotEqualTo(copyOfProduct3);
        assertThat(testP).isNotEqualTo(copyOfProduct4);
        assertThat(testP).isNotEqualTo(copyOfProduct5);
        assertThat(testP).isNotEqualTo(copyOfProduct2);
    }

    @Test
    void testHash(){
        Product productReplica = testP;
        Product copyOfProduct = new Product("H", 6d, 14d,15);
        assertThat(testP.hashCode()).isEqualTo(testP.hashCode());
        assertThat(testP.hashCode()).isEqualTo(productReplica.hashCode());
        assertThat(testP.hashCode()).isEqualTo(copyOfProduct.hashCode());
    }
}
