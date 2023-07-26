package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();

    @Test
    public void calculatesPrice() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, "drinks"),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1, "drinks")
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }

    @Test
    public void appliesDiscounts() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, "drinks"),
                new Product("Cheese", BigDecimal.valueOf(3.5), 3, "food")
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(30.5), result);
    }

    // testing task 3
    @Test
    public void appliesCategoryDiscount() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, "drinks"),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, "drinks"),
                new Product("Cheese", BigDecimal.valueOf(8), 2, "food")
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(31.84), result);
    }

    // testing task 4
    @Test
    public void customDiscountsWorking() {
        ShoppingCartService customService = new ShoppingCartService(0.2,5,0.1,10);
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.4), 6, "drinks"),
                new Product("Coffee", BigDecimal.valueOf(4.2), 4, "drinks"),
                new Product("Cheese", BigDecimal.valueOf(10), 12, "food")
        ));

        BigDecimal result = customService.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(126.72), result);
    }
}