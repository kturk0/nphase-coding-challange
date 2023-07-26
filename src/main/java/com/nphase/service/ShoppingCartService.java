package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

public class ShoppingCartService {
    public final double productDiscount;
    public final int productThreshold;
    public final double categoryDiscount;
    public final int categoryThreshold;

    public ShoppingCartService() {
        productDiscount = 0.1;
        productThreshold = 3;
        categoryDiscount = 0.1;
        categoryThreshold = 3;
    }

    public ShoppingCartService(double productDiscount, int productThreshold, double categoryDiscount, int categoryThreshold) {
        this.productDiscount = productDiscount;
        this.productThreshold = productThreshold;
        this.categoryDiscount = categoryDiscount;
        this.categoryThreshold = categoryThreshold;
    }

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        HashMap<String, Integer> categoryMap = new HashMap<String,Integer>();
        shoppingCart.getProducts().forEach(
                product -> categoryMap.put(product.getCategory(), categoryMap.getOrDefault(product.getCategory(),0) + product.getQuantity()));

        return shoppingCart.getProducts()
                .stream()
                .map(product -> calculatePrice(product,categoryMap))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal calculatePrice(Product product, HashMap<String,Integer> categoryMap) {
        BigDecimal price = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
        BigDecimal discount = BigDecimal.valueOf(1);
        if(product.getQuantity() > productThreshold)
            discount = discount.subtract(BigDecimal.valueOf(productDiscount));
        if(categoryMap.get(product.getCategory()) > categoryThreshold)
            discount = discount.subtract(BigDecimal.valueOf(categoryDiscount));
        return price.multiply(discount).round(new MathContext(4, RoundingMode.CEILING)).stripTrailingZeros();
    }
}
