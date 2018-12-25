package com.example.venky.capstoneproject.Models;

/**
 * Created by VENKY on 12/20/2018.
 */

public class Category {

    String idCategory;
    String strCategory;
    String strCategoryThumb;
    String strCategoryDescription;

    public Category(String idCategory, String strCategory,
                    String strCategoryThumb, String strCategoryDescription) {
        this.idCategory = idCategory;
        this.strCategory = strCategory;
        this.strCategoryThumb = strCategoryThumb;
        this.strCategoryDescription = strCategoryDescription;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }
}
