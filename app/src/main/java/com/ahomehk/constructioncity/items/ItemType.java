package com.ahomehk.constructioncity.items;

import java.io.Serializable;

/**
 * Created by rainvic on 29/4/15.
 */
public class ItemType implements Serializable {

    private String _type_id;
    private String type_main;
    private String type_one;
    private String type_two;
    private String type_three;
    private String type_four;
    private String type_five;
    private String type_extra;
    private String type_created_at;

    public ItemType() {
    }

    public ItemType(int _type_id, String type_main, String type_one,
                    String type_two, String type_three, String type_four,
                    String type_five, String type_extra, String type_created_at) {
        this._type_id = Integer.toString(_type_id);
        this.type_main = type_main;
        this.type_one = type_one;
        this.type_two = type_two;
        this.type_three = type_three;
        this.type_four = type_four;
        this.type_five = type_five;
        this.type_extra = type_extra;
        this.type_created_at = type_created_at;
    }

    public ItemType(ItemType clone){
        this._type_id = clone.get_type_id_str();
        this.type_main = clone.getType_main();
        this.type_one = clone.getType_one();
        this.type_two = clone.getType_two();
        this.type_three = clone.getType_three();
        this.type_four = clone.getType_four();
        this.type_five = clone.getType_five();
        this.type_extra = clone.getType_extra();
        this.type_created_at = clone.getType_created_at();
    }

    public String get_type_id_str() {
        return _type_id;
    }
    public void set_type_id(int _type_id) {
        this._type_id = Integer.toString(_type_id);
    }
    public int get_type_id() {
        return Integer.parseInt(_type_id);
    }
    public void set_type_id(String _type_id) {
        this._type_id = _type_id;
    }

    public String getType_main() {
        return type_main;
    }

    public void setType_main(String type_main) {
        this.type_main = type_main;
    }

    public String getType_one() {
        return type_one;
    }

    public void setType_one(String type_one) {
        this.type_one = type_one;
    }

    public String getType_two() {
        return type_two;
    }

    public void setType_two(String type_two) {
        this.type_two = type_two;
    }

    public String getType_three() {
        return type_three;
    }

    public void setType_three(String type_three) {
        this.type_three = type_three;
    }

    public String getType_four() {
        return type_four;
    }

    public void setType_four(String type_four) {
        this.type_four = type_four;
    }

    public String getType_five() {
        return type_five;
    }

    public void setType_five(String type_five) {
        this.type_five = type_five;
    }

    public String getType_extra() {
        return type_extra;
    }

    public void setType_extra(String type_extra) {
        this.type_extra = type_extra;
    }

    public String getType_created_at() {
        return type_created_at;
    }

    public void setType_created_at(String type_created_at) {
        this.type_created_at = type_created_at;
    }
}
