package com.ahomehk.constructioncity.items;

import java.util.ArrayList;

/**
 * Created by rainvic on 26/7/15.
 */
public class ProductType {
    String type;
    ArrayList<String> items = new ArrayList<>();

    public ProductType() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {

        return type;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {

        this.items = items;
    }

    public void addItme(String item){
        this.items.add(item);
    }
}
