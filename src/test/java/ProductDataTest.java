import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.goit.shop.Product;
import ua.goit.shop.ProductData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.*;

class ProductDataTest {

    private static Map<String, Product> copy;
    private static final double MARGIN = 0.000000001;
    private static Product p;

    @BeforeEach
    void setUp() {
        copy = ProductData.getAllProducts();
        p = new Product("P", 6d, 10, 5);
        ProductData.addOrUpdateProduct(p);
    }

    @AfterEach
    void tearDown(){
        ProductData.setAllProducts(copy);
    }

    @Test
    void getAllProducts(){
    assertThat(ProductData.getAllProducts().size()).isGreaterThan(0);
        assertThat(ProductData.getAllProducts()).containsValue(p);
    }

    @Test
    void setAllProducts(){
        Product a = new Product("A", 5d,13d, 4);
        Product b = new Product("N",3d,12d,3);

        Map<String, Product> testM = Map.of(a.getProductId(), a, b.getProductId(),b);
        ProductData.setAllProducts(testM);
        assertThat(testM).isEqualTo(ProductData.getAllProducts());

        assertThatThrownBy(() ->{
            ProductData.setAllProducts(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void addOrUpdateProduct(){
        assertThat(ProductData.getAllProducts()).containsKey(p.getProductId());
        assertThat(ProductData.getAllProducts()).containsValue(p);
        p.setPromoPrice(5.2d);
        assertThat(ProductData.getProductById(p.getProductId()).getPromoPrice()).isEqualTo(5.2d, offset(MARGIN));
        assertThatThrownBy(() -> {
            ProductData.addOrUpdateProduct(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void removeProductById(){
        assertThat(ProductData.getAllProducts()).containsValue(p);
        ProductData.removeProductById("P");
        assertThat(ProductData.getAllProducts()).doesNotContainValue(p);
        assertThatThrownBy(() -> {
            ProductData.removeProductById(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void removeProductByProduct(){
        assertThat(ProductData.getAllProducts()).containsValue(p);
        ProductData.removeProductByProduct(p);
        assertThat(ProductData.getAllProducts()).doesNotContainValue(p);
        assertThatThrownBy(() -> {
            ProductData.removeProductByProduct(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void calculateCost(){
        String cartOfGroceriesBlanks = " AAABC ";
        String cartOfGroceries = "AABCC";
        String cartWithDigits = "A1BB4";
        String cartWithCases = "AaBb";
        String cartWithKirril = "AпBяC";
        String cartWithOtherLetters = "ABCDERY";

        assertThat(ProductData.calculateCost(cartOfGroceriesBlanks)).isEqualTo(8.25d, offset(MARGIN));
        assertThat(ProductData.calculateCost(cartOfGroceries)).isEqualTo(8.75d, offset(MARGIN));
        assertThat(ProductData.calculateCost(cartWithDigits)).isEqualTo(9.75d, offset(MARGIN));
        assertThat(ProductData.calculateCost(cartWithCases)).isEqualTo(11d, offset(MARGIN));
        assertThat(ProductData.calculateCost(" ")).isEqualTo(0d, offset(MARGIN));
        assertThat(ProductData.calculateCost(null)).isEqualTo(0d, offset(MARGIN));

        assertThatThrownBy(() -> {
            ProductData.calculateCost(cartWithKirril);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Some IDs are not valid");
        assertThatThrownBy(() -> {
            ProductData.calculateCost(cartWithOtherLetters);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Some IDs are not valid");

        ProductData.setAllProducts(new ConcurrentHashMap<>());
        assertThatThrownBy(() ->{
            ProductData.calculateCost(cartWithOtherLetters);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("The Prices Not Available");

    }

    @Test
    void getProductById(){
        assertThat(ProductData.getProductById(p.getProductId())).isEqualTo(p);
        assertThatThrownBy(() -> {
            ProductData.getProductById(null);
        }).isInstanceOf(NullPointerException.class);
    }

}
