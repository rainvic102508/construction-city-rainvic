package com.ahomehk.constructioncity.items;

import java.io.Serializable;

/**
 * Created by rainvic on 29/4/15.
 */
public class Item extends ItemType implements Serializable{

    private final static String TAG = "Item";

    private String _item_id;
    private String _type_id;
    private String _provider_id;
    private String item_price_min;
    private String item_price_max;
    private String item_width;
    private String item_height;
    private String item_thickness;
    private String item_width_height;
    private String item_img_file;
    private String item_title;
    private String item_created_at;
    private String item_extra_description;
    private String item_tags;
    private String img_add;
    private String item_color;
    private String item_finish;
    private String[] img_names;
    private String img_names_str;
    private String item_place_of_origin;
    private String item_lead_time;

    public Item() {
    }

    public Item(int _item_id, int _type_id, int _provider_id,
                double item_price_min, double item_price_max, int item_width,
                int item_height, int item_thickness, String item_width_height,
                String item_img_file, String item_title, String item_created_at,
                String item_extra_description, String item_tags, String item_color, String item_finish,
                String place_of_origin, int lead_time) {
        this._item_id = Integer.toString(_item_id);
        this._type_id = Integer.toString(_type_id);
        this._provider_id = Integer.toString(_provider_id);
        this.item_price_min = Double.toString(item_price_min);
        this.item_price_max = Double.toString(item_price_max);
        this.item_width = Integer.toString(item_width);
        this.item_height = Integer.toString(item_height);
        this.item_thickness = Integer.toString(item_thickness);
        this.item_width_height = item_width_height;
        this.item_img_file = item_img_file;
        this.item_title = item_title;
        this.item_created_at = item_created_at;
        this.item_extra_description = item_extra_description;
        this.item_tags = item_tags;
        this.item_color = item_color;
        this.item_finish = item_finish;
        this.item_place_of_origin = place_of_origin;
        this.item_lead_time = Integer.toString(lead_time);


        if(getType_main() != null){
            setImg_add();
        }

    }

    public Item(String _item_id, String _type_id, String _provider_id,
                String item_price_min, String item_price_max, String item_width,
                String item_height, String item_thickness, String item_width_height,
                String item_img_file, String item_title, String item_created_at,
                String item_extra_description, String item_tags, String item_color, String item_finish,
                String place_of_origin, String lead_time) {
        this._item_id = _item_id;
        this._type_id = _type_id;
        this._provider_id = _provider_id;
        this.item_price_min = item_price_min;
        this.item_price_max = item_price_max;
        this.item_width = item_width;
        this.item_height = item_height;
        this.item_thickness = item_thickness;
        this.item_width_height = item_width_height;
        this.item_img_file = item_img_file;
        this.item_title = item_title;
        this.item_created_at = item_created_at;
        this.item_extra_description = item_extra_description;
        this.item_tags = item_tags;
        this.item_color = item_color;
        this.item_finish = item_finish;
        this.item_place_of_origin = place_of_origin;
        this.item_lead_time = lead_time;


        if(getType_main() != null){
            setImg_add();
        }
    }



    public String get_item_id_str() {
        return _item_id;
    }
    public int get_item_id() {
        return Integer.parseInt(_item_id);
    }
    public void set_item_id(int _item_id) {
        this._item_id = Integer.toString(_item_id);
    }
    public void set_item_id(String _item_id) {
        this._item_id = _item_id;
    }



    public String get_type_id_str() {
        return _type_id;
    }
    public int get_type_id() {
        return Integer.parseInt(_type_id);
    }
    public void set_type_id(int _type_id) {
        this._type_id = Integer.toString(_type_id);
    }
    public void set_type_id(String _type_id) {
        this._type_id = _type_id;
    }



    public String get_provider_id_str() {
        return _provider_id;
    }
    public int get_provider_id() {
        return Integer.parseInt(_provider_id);
    }
    public void set_provider_id(int _provider_id) {
        this._provider_id = Integer.toString(_provider_id);
    }
    public void set_provider_id(String _provider_id) {
        this._provider_id = _provider_id;
    }



    public double getItem_price_min() {
        return Double.parseDouble(item_price_min);
    }
    public String getItem_price_min_str() {
        return item_price_min;
    }
    public void setItem_price_min(String item_price_min) {
        this.item_price_min = item_price_min;
    }
    public void setItem_price_min(double item_price_min) {
        this.item_price_min = Double.toString(item_price_min);
    }



    public double getItem_price_max() {
        return Double.parseDouble(item_price_max);
    }
    public String getItem_price_max_str() {
        return item_price_max;
    }
    public void setItem_price_max(double item_price_max) {
        this.item_price_max = Double.toString(item_price_max);
    }
    public void setItem_price_max(String item_price_max) {
        this.item_price_max = item_price_max;
    }



    public int getItem_width() {
        return Integer.parseInt(item_width);
    }
    public void setItem_width(String item_width) {
        this.item_width = item_width;
    }
    public String getItem_width_str() {
        return item_width;
    }
    public void setItem_width(int item_width) {
        this.item_width = Integer.toString(item_width);
    }



    public String getItem_height_str() {
        return item_height;
    }
    public void setItem_height(String item_height) {
        this.item_height = item_height;
    }
    public int getItem_height() {
        return Integer.parseInt(item_height);
    }
    public void setItem_height(int item_height) {
        this.item_height = Integer.toString(item_height);
    }



    public String getItem_thickness_str() {
        return item_thickness;
    }
    public void setItem_thickness(String item_thickness) {
        this.item_thickness = item_thickness;
    }
    public int getItem_thickness() {
        return Integer.parseInt(item_thickness);
    }
    public void setItem_thickness(int item_thickness) {
        this.item_thickness = Integer.toString(item_thickness);
    }


    public String getItem_width_height() {
        return item_width_height;
    }

    public void setItem_width_height(String item_width_height) {
        this.item_width_height = item_width_height;
    }

    public String getItem_img_file() {
        return item_img_file;
    }
    public void setItem_img_file(String item_img_file) {
        this.item_img_file = item_img_file;
        if(getType_main() != null){
            setImg_add();
        }
    }



    public String getItem_title() {
        return item_title;
    }
    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }



    public String getItem_created_at() {
        return item_created_at;
    }
    public void setItem_created_at(String item_created_at) {
        this.item_created_at = item_created_at;
    }



    public String getItem_extra_description() {
        return item_extra_description;
    }
    public void setItem_extra_description(String item_extra_description) {
        this.item_extra_description = item_extra_description;
    }



    public String getItem_tags() {
        return item_tags;
    }
    public void setItem_tags(String item_tags) {
        this.item_tags = item_tags;
    }

    public String getImg_add() {
        return img_add;
    }
    public void setImg_add(){
        String server_add = "http://www.ahomehk.com/ahomehk/user/img/";
        img_add = server_add + item_img_file+"/";
    }

    public void setImg_names(String img_names){
        this.img_names = img_names.split(":");
        this.img_names_str = img_names;
    }

    public String[] getImg_names(){
        return img_names;
    }

    public String getImg_names_str(){
        return img_names_str;
    }



    public String getItem_color() {
        return item_color;
    }
    public String getItem_finish() {
        return item_finish;
    }


    public void setItem_color(String item_color) {
        this.item_color = item_color;
    }
    public void setItem_finish(String item_finish) {
        this.item_finish = item_finish;
    }

    public void setImg_names(String[] img_names) {
        this.img_names = img_names;
    }
    public void setImg_names_str(String img_names_str) {
        this.img_names_str = img_names_str;
    }

    public String getItem_place_of_origin() {
        return item_place_of_origin;
    }

    public String getItem_lead_time() {
        return item_lead_time;
    }

    public void setItem_place_of_origin(String item_place_of_origin) {
        this.item_place_of_origin = item_place_of_origin;
    }

    public void setItem_lead_time(String item_lead_time) {
        this.item_lead_time = item_lead_time;
    }

}
