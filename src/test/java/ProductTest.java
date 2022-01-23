import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.goit.shop.Product;
import static org.assertj.core.api.Assertions.*;

public class ProductTest {

    private static final double EPSILON = 0.000000001;
    private static Product testP;


    @BeforeEach
    void setUp(){
        testP = new Product("H", 6d, 14d, 15);
    }

    @Test
    void setPrice(){
        testP.setPrice(6d);
        assertThat(testP.getPrice()).isEqualTo(6d, offset (EPSILON));

        assertThatThrownBy(() -> testP.setPrice(-1)).isInstanceOf(RuntimeException.class)
                .hasMessage("Price is not valid");
    }

    @Test
    void setPromoPrice() {
        testP.setPromoPrice(14d);
        assertThat(testP.getPromoPrice()).isEqualTo(14d, offset(EPSILON));

        assertThatThrownBy(() -> testP.setPrice(0d)).isInstanceOf(RuntimeException.class)
                .hasMessage("Price is not valid");
    }

    @Test
    void setQty(){
        testP.setQty(56);
        assertThat(testP.getQty()).isEqualTo(56);

        assertThatThrownBy(() -> testP.setQty(0)).isInstanceOf(RuntimeException.class)
                .hasMessage("Quantity is not valid");

    }
}
