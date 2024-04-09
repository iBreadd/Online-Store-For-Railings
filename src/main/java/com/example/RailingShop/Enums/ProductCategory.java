package com.example.RailingShop.Enums;
/*Видовете продукти са следните:
Храни (food)
Напитки (drinks)
Санитарни (sanitary)
Парапети (railing)
Аксесоари, декорация, други (others)
*/
public enum ProductCategory {
    FOOD("food"),DRINKS("drinks"),SANITARY("sanitary"),RAILINGS("railings"),OTHERS("others");

    public String category;

    ProductCategory(String category){
        this.category=category;
    }

    public String getValue(){
        return this.category;
    }

}
