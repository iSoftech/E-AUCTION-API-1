package com.cognizant.fse.eauction.seller.constant;

/**
 * Product Category Enum is to manage all the defined Product Categories
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
public enum ProductCategory {
    PAINTING("Painting"),
    SCULPTURE("Sculpture"),
    ORNAMENT("Ornament");

    private String alias;

    ProductCategory(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public static ProductCategory fromAlias(String alias) {
        for (ProductCategory category : ProductCategory.values()) {
            if (category.getAlias().equals(alias)) {
                return category;
            }
        }
        return null;
    }
}


