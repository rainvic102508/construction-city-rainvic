package com.ahomehk.constructioncity.items;

/**
 * Created by rainvic on 1/5/15.
 */
public class TableInfo {
    private String table_name;
    private String update_resent_modified;
    private String update_created_at;

    public TableInfo() {
    }

    public TableInfo(String table_name, String update_created_at) {

        this.table_name = table_name;
        this.update_created_at = update_created_at;
    }

    public TableInfo(String table_name, String update_resent_modified, String update_created_at) {

        this.table_name = table_name;
        this.update_resent_modified = update_resent_modified;
        this.update_created_at = update_created_at;
    }


    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getUpdate_resent_modified() {
        return update_resent_modified;
    }

    public void setUpdate_resent_modified(String update_resent_modified) {
        this.update_resent_modified = update_resent_modified;
    }

    public String getUpdate_created_at() {
        return update_created_at;
    }

    public void setUpdate_created_at(String update_created_at) {
        this.update_created_at = update_created_at;
    }


}
