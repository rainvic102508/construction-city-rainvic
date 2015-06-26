package com.ahomehk.constructioncity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ahomehk.constructioncity.items.Item;
import com.ahomehk.constructioncity.items.ItemType;
import com.ahomehk.constructioncity.items.Provider;
import com.ahomehk.constructioncity.items.TableInfo;

import java.util.ArrayList;

/**
 * Created by rainvic on 28/4/15.
 */
public class DBAdapter extends SQLiteOpenHelper {


    private static final String TAG = "DBAdapter";

    //menu type
    private final static String PRODUCT = "product";
    private final static String SERVICE = "service";
    private final static String JOB_REFERENCE = "job reference";
    private final static String NEWS = "news";

    // database version
    private final static int DATABASE_VERSION = 1;

    //database name
    private final static String DATABASE_NAME = "ahomehk.db";

    //table name
    public final static String TABLE_UPDATE = "updateTable";

    //FTS table name
    public final static String FTS_TABLE_ITEM = "ahomehkItem";
    public final static String FTS_TABLE_ITEM_TYPE = "ahomehkItemType";
    public final static String FTS_TABLE_PROVIDER = "ahomehkProvider";

    private Context mContext;

    /**
     *  column name
     */

    // UPDATE table
    public final static String COLUMN_UPDATE_ID = "_update_id";
    public final static String COLUMN_TABLE_NAME = "table_name";
    public final static String COLUMN_RESENT_MODIFIED = "update_resent_modified";

    // ITEM table
    public final static String COLUMN_ITEM_ID = "_item_id";
    public final static String COLUMN_TYPE_ID = "_type_id";
    public final static String COLUMN_PROVIDER_ID = "_provider_id";
    public final static String COLUMN_ITEM_PRICE_MIN = "item_price_min";
    public final static String COLUMN_ITEM_PRICE_MAX = "item_price_max";
    public final static String COLUMN_ITEM_WIDTH = "item_width";
    public final static String COLUMN_ITEM_HEIGHT = "item_height";
    public final static String COLUMN_ITEM_THICKNESS = "item_thickness";
    public final static String COLUMN_ITEM_IMG_FILE = "item_img_file";
    public final static String COLUMN_ITEM_TITLE = "item_title";
    public final static String COLUMN_ITEM_CREATED_AT = "item_created_at";
    public final static String COLUMN_ITEM_EXTRA_DESCRIPTION = "item_extra_description";
    public final static String COLUMN_ITEM_TAG = "item_tags";
    public final static String COLUMN_ITEM_COLOR = "item_color";
    public final static String COLUMN_ITEM_FINISH = "item_finish";
    public final static String COLUMN_ITEM_IMG_NAMES = "item_img_names";

    // ITEM_TYPE table
    public final static String COLUMN_TYPE_MAIN = "type_main";
    public final static String COLUMN_TYPE_ONE = "type_one";
    public final static String COLUMN_TYPE_TWO = "type_two";
    public final static String COLUMN_TYPE_THREE = "type_three";
    public final static String COLUMN_TYPE_FOUR = "type_four";
    public final static String COLUMN_TYPE_FIVE = "type_five";
    public final static String COLUMN_TYPE_EXTRA = "type_extra";
    public final static String COLUMN_TYPE_CREATED_AT = "type_created_at";


    // PROVIDER table
    public final static String COLUMN_PROVIDER_TITLE = "provider_title";
    public final static String COLUMN_PROVIDER_CONTACT = "provider_contact";
    public final static String COLUMN_PROVIDER_EMAIL = "provider_email";
    public final static String COLUMN_PROVIDER_EXTRA_INFO = "provider_extra_info";
    public final static String COLUMN_PROVIDER_CREATED_AT = "provider_created_at";
    public final static String COLUMN_PROVIDER_LINK = "provider_link";


    // create table sql command
    private final static String CREATE_UPDATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_UPDATE + " (" +
            COLUMN_UPDATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TABLE_NAME + " TEXT," +
            COLUMN_RESENT_MODIFIED + " TEXT" +
            ");";


    // create FTS table sql command
    private final static String CREATE_ITEM_FTS_TABLE = "CREATE VIRTUAL TABLE " + FTS_TABLE_ITEM + " USING fts4(" +
            COLUMN_ITEM_ID + " ," +
            COLUMN_TYPE_ID + " ," +
            COLUMN_PROVIDER_ID + "  ," +
            COLUMN_ITEM_PRICE_MIN + " ," +
            COLUMN_ITEM_PRICE_MAX + " ," +
            COLUMN_ITEM_WIDTH + " ," +
            COLUMN_ITEM_HEIGHT + " ," +
            COLUMN_ITEM_THICKNESS + " ," +
            COLUMN_ITEM_IMG_FILE + " ," +
            COLUMN_ITEM_TITLE + " ," +
            COLUMN_ITEM_CREATED_AT + " ," +
            COLUMN_ITEM_EXTRA_DESCRIPTION + " ," +
            COLUMN_ITEM_TAG + " ," +
            COLUMN_ITEM_COLOR + " ," +
            COLUMN_ITEM_FINISH + " ," +
            COLUMN_ITEM_IMG_NAMES + " " +
            ");";

    private final static String CREATE_ITEM_TYPE_FTS_TABLE = "CREATE VIRTUAL TABLE " + FTS_TABLE_ITEM_TYPE + " USING fts4(" +
            COLUMN_TYPE_ID + " ," +
            COLUMN_TYPE_MAIN + " ," +
            COLUMN_TYPE_ONE + " ," +
            COLUMN_TYPE_TWO + " ," +
            COLUMN_TYPE_THREE + " ," +
            COLUMN_TYPE_FOUR + " ," +
            COLUMN_TYPE_FIVE + " ," +
            COLUMN_TYPE_EXTRA + " ," +
            COLUMN_TYPE_CREATED_AT + " " +
            ");";

    private final static String CREATE_PROVIDER_FTS_TABLE = "CREATE VIRTUAL TABLE " + FTS_TABLE_PROVIDER + " USING fts4(" +
            COLUMN_PROVIDER_ID + " ," +
            COLUMN_PROVIDER_TITLE + " ," +
            COLUMN_PROVIDER_CONTACT + " ," +
            COLUMN_PROVIDER_EMAIL + " ," +
            COLUMN_PROVIDER_EXTRA_INFO + " ," +
            COLUMN_PROVIDER_CREATED_AT + " ," +
            COLUMN_PROVIDER_LINK + " " +
            ");";


    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public DBAdapter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create tables
        db.execSQL(CREATE_UPDATE_TABLE);

        //create FTS tables
        db.execSQL(CREATE_ITEM_FTS_TABLE);
        db.execSQL(CREATE_ITEM_TYPE_FTS_TABLE);
        db.execSQL(CREATE_PROVIDER_FTS_TABLE);


        //mainly this function is for the update table
        updateTableInitial(db);
        Log.i(TAG, "onCreate(SQLiteDatabase db): database successfully created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        // On upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPDATE);

        db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_ITEM_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_PROVIDER);


        // Create new tables
        onCreate(db);

    }

    public void createTable(String table){
        SQLiteDatabase db = getWritableDatabase();
        switch (table){
            case FTS_TABLE_ITEM:
                db.execSQL(CREATE_ITEM_FTS_TABLE);
                break;
            case FTS_TABLE_ITEM_TYPE:
                db.execSQL(CREATE_ITEM_TYPE_FTS_TABLE);
                break;
            case FTS_TABLE_PROVIDER:
                db.execSQL(CREATE_PROVIDER_FTS_TABLE);
                break;
            default:
                Log.w(TAG, "createTable() : table could not be found by "+table);
        }
    }

    public void dropTable(String table){
        SQLiteDatabase db = getWritableDatabase();
        switch (table){
            case FTS_TABLE_ITEM:
                db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_ITEM);
                break;
            case FTS_TABLE_ITEM_TYPE:
                db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_ITEM_TYPE);
                break;
            case FTS_TABLE_PROVIDER:
                db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_PROVIDER);
                break;
            default:
                Log.w(TAG, "dropTable() : table could not be found by "+table);
        }
        db.close();
    }


    // Update table initialization
    private void updateTableInitial(SQLiteDatabase db){

        String date = "2014-01-01 00:00:00";

        TableInfo item = new TableInfo(FTS_TABLE_ITEM, date);
        TableInfo type = new TableInfo(FTS_TABLE_ITEM_TYPE, date);
        TableInfo provider = new TableInfo(FTS_TABLE_PROVIDER, date);


        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_NAME, item.getTable_name());
        values.put(COLUMN_RESENT_MODIFIED, item.getUpdate_created_at());
        db.insert(TABLE_UPDATE, null, values);

        values.put(COLUMN_TABLE_NAME, type.getTable_name());
        values.put(COLUMN_RESENT_MODIFIED, type.getUpdate_created_at());
        db.insert(TABLE_UPDATE, null, values);

        values.put(COLUMN_TABLE_NAME, provider.getTable_name());
        values.put(COLUMN_RESENT_MODIFIED, provider.getUpdate_created_at());
        db.insert(TABLE_UPDATE, null, values);


    }

    /**
     * Temporary method in order to move database from server
     * @param items
     */

    public void moveItemTable(ArrayList<Item> items){
        SQLiteDatabase db = getWritableDatabase();

        for(int i=0; i<items.size(); i++){

            ContentValues values = new ContentValues();
            values.put(COLUMN_ITEM_ID, items.get(i).get_item_id());
            values.put(COLUMN_TYPE_ID, items.get(i).get_type_id());
            values.put(COLUMN_PROVIDER_ID, items.get(i).get_provider_id());
            values.put(COLUMN_ITEM_PRICE_MIN, items.get(i).getItem_price_min());
            values.put(COLUMN_ITEM_PRICE_MAX, items.get(i).getItem_price_max());
            values.put(COLUMN_ITEM_WIDTH, items.get(i).getItem_width());
            values.put(COLUMN_ITEM_HEIGHT, items.get(i).getItem_height());
            values.put(COLUMN_ITEM_THICKNESS, items.get(i).getItem_thickness());
            values.put(COLUMN_ITEM_IMG_FILE, items.get(i).getItem_img_file());
            values.put(COLUMN_ITEM_TITLE, items.get(i).getItem_title());
            values.put(COLUMN_ITEM_CREATED_AT, items.get(i).getItem_created_at());
            values.put(COLUMN_ITEM_EXTRA_DESCRIPTION, items.get(i).getItem_extra_description());
            values.put(COLUMN_ITEM_TAG, items.get(i).getItem_tags());
            values.put(COLUMN_ITEM_COLOR, items.get(i).getItem_color());
            values.put(COLUMN_ITEM_FINISH, items.get(i).getItem_finish());
            values.put(COLUMN_ITEM_IMG_NAMES, items.get(i).getImg_names_str());
            Log.i(TAG, "img_names: "+items.get(i).getImg_names_str());


            db.insert(FTS_TABLE_ITEM, null, values);
        }
        db.close();
    }
    public void moveItemTypeTable(ArrayList<ItemType> items){
        SQLiteDatabase db = getWritableDatabase();

        for(int i=0; i<items.size(); i++){
            ContentValues values = new ContentValues();
            values.put(COLUMN_TYPE_ID, items.get(i).get_type_id());
            values.put(COLUMN_TYPE_MAIN, items.get(i).getType_main());
            values.put(COLUMN_TYPE_ONE, items.get(i).getType_one());
            values.put(COLUMN_TYPE_TWO, items.get(i).getType_two());
            values.put(COLUMN_TYPE_THREE, items.get(i).getType_three());
            values.put(COLUMN_TYPE_FOUR, items.get(i).getType_four());
            values.put(COLUMN_TYPE_FIVE, items.get(i).getType_five());
            values.put(COLUMN_TYPE_EXTRA, items.get(i).getType_extra());
            values.put(COLUMN_TYPE_CREATED_AT, items.get(i).getType_created_at());

            db.insert(FTS_TABLE_ITEM_TYPE, null, values);
        }

        db.close();
    }
    public void moveProviderTable(ArrayList<Provider> providers){
        SQLiteDatabase db = getWritableDatabase();

        for(int i=0; i<providers.size(); i++){
            ContentValues values = new ContentValues();
            values.put(COLUMN_PROVIDER_ID, providers.get(i).get_provider_id());
            values.put(COLUMN_PROVIDER_TITLE, providers.get(i).getProvider_title());
            values.put(COLUMN_PROVIDER_CONTACT, providers.get(i).getProvider_contact());
            values.put(COLUMN_PROVIDER_EMAIL, providers.get(i).getProvider_email());
            values.put(COLUMN_PROVIDER_EXTRA_INFO, providers.get(i).getProvider_extra_info());
            values.put(COLUMN_PROVIDER_CREATED_AT, providers.get(i).getProvider_created_at());
            values.put(COLUMN_PROVIDER_LINK, providers.get(i).getProvider_link());

            db.insert(FTS_TABLE_PROVIDER, null, values);
        }

        db.close();
    }

    /**
     * Get Product view
     */
    public ArrayList<Item> getItems(String data) {
        SQLiteDatabase db = getReadableDatabase();
        //String sql = "SELECT * FROM " + VIEW_PRODUCTS + " ORDER BY " + COLUMN_TYPE_ID + " ASC;";

        String sql = "SELECT "+ FTS_TABLE_ITEM +".*, "+ FTS_TABLE_ITEM_TYPE +".*" +
                " FROM " + FTS_TABLE_ITEM_TYPE +
                " JOIN " + FTS_TABLE_ITEM +
                " ON " + FTS_TABLE_ITEM + "." + COLUMN_TYPE_ID + "=" + FTS_TABLE_ITEM_TYPE + "." + COLUMN_TYPE_ID +
                " WHERE " + FTS_TABLE_ITEM_TYPE + "." + COLUMN_TYPE_MAIN + " MATCH \'"+data+"\';";
        ArrayList<Item> productArr = new ArrayList<>();

        //cursor point to a location in your result
        Cursor c = db.rawQuery(sql, null);

        //move cursor to first
        c.moveToFirst();

        while (!c.isAfterLast()) {
            String img_file = c.getString(c.getColumnIndex(COLUMN_ITEM_IMG_FILE));
            Item product = new Item(
                    c.getString(c.getColumnIndex(COLUMN_ITEM_ID)), c.getString(c.getColumnIndex(COLUMN_TYPE_ID)), c.getString(c.getColumnIndex(COLUMN_PROVIDER_ID)),
                    c.getString(c.getColumnIndex(COLUMN_ITEM_PRICE_MIN)), c.getString(c.getColumnIndex(COLUMN_ITEM_PRICE_MAX)), c.getString(c.getColumnIndex(COLUMN_ITEM_WIDTH)),
                    c.getString(c.getColumnIndex(COLUMN_ITEM_HEIGHT)), c.getString(c.getColumnIndex(COLUMN_ITEM_THICKNESS)), img_file,
                    c.getString(c.getColumnIndex(COLUMN_ITEM_TITLE)), c.getString(c.getColumnIndex(COLUMN_ITEM_CREATED_AT)), c.getString(c.getColumnIndex(COLUMN_ITEM_EXTRA_DESCRIPTION)),
                    c.getString(c.getColumnIndex(COLUMN_ITEM_TAG)), c.getString(c.getColumnIndex(COLUMN_ITEM_COLOR)),
                    c.getString(c.getColumnIndex(COLUMN_ITEM_FINISH))
            );
            product.setType_main(c.getString(c.getColumnIndex(COLUMN_TYPE_MAIN)));
            product.setType_one(c.getString(c.getColumnIndex(COLUMN_TYPE_ONE)));
            product.setType_two(c.getString(c.getColumnIndex(COLUMN_TYPE_TWO)));
            product.setType_three(c.getString(c.getColumnIndex(COLUMN_TYPE_THREE)));
            product.setType_four(c.getString(c.getColumnIndex(COLUMN_TYPE_FOUR)));
            product.setType_five(c.getString(c.getColumnIndex(COLUMN_TYPE_FIVE)));
            product.setType_extra(c.getString(c.getColumnIndex(COLUMN_TYPE_EXTRA)));
            product.setType_created_at(c.getString(c.getColumnIndex(COLUMN_TYPE_CREATED_AT)));
            product.setImg_add();
            String img_names = c.getString(c.getColumnIndex(COLUMN_ITEM_IMG_NAMES));
            product.setImg_names(img_names);
            productArr.add(product);
            c.moveToNext();
            //  Log.i(TAG, "Item img names: " + img_names + " BY img file: " + img_file);
        }
        db.close();
        return productArr;
    }


    /**
     * Get Product view
     */
    public ArrayList<Item> getSearchResult(String keyword) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT "+ FTS_TABLE_ITEM +".*, "+ FTS_TABLE_ITEM_TYPE +".*" +
                " FROM " + FTS_TABLE_ITEM_TYPE +
                " JOIN " + FTS_TABLE_ITEM +
                " ON " + FTS_TABLE_ITEM + "." + COLUMN_TYPE_ID + "=" + FTS_TABLE_ITEM_TYPE + "." + COLUMN_TYPE_ID +
                " WHERE " + FTS_TABLE_ITEM + " MATCH \'" + keyword + "\';";
        ArrayList<Item> productArr = new ArrayList<>();

        //cursor point to a location in your result
        Cursor c = db.rawQuery(sql, null);

        //move cursor to first
        c.moveToFirst();

        while (!c.isAfterLast()) {
            String img_file = c.getString(c.getColumnIndex(COLUMN_ITEM_IMG_FILE));
            Item product = new Item(
                    c.getString(c.getColumnIndex(COLUMN_ITEM_ID)), c.getString(c.getColumnIndex(COLUMN_TYPE_ID)), c.getString(c.getColumnIndex(COLUMN_PROVIDER_ID)),
                    c.getString(c.getColumnIndex(COLUMN_ITEM_PRICE_MIN)), c.getString(c.getColumnIndex(COLUMN_ITEM_PRICE_MAX)), c.getString(c.getColumnIndex(COLUMN_ITEM_WIDTH)),
                    c.getString(c.getColumnIndex(COLUMN_ITEM_HEIGHT)), c.getString(c.getColumnIndex(COLUMN_ITEM_THICKNESS)), img_file,
                    c.getString(c.getColumnIndex(COLUMN_ITEM_TITLE)), c.getString(c.getColumnIndex(COLUMN_ITEM_CREATED_AT)), c.getString(c.getColumnIndex(COLUMN_ITEM_EXTRA_DESCRIPTION)),
                    c.getString(c.getColumnIndex(COLUMN_ITEM_TAG)), c.getString(c.getColumnIndex(COLUMN_ITEM_COLOR)),
                    c.getString(c.getColumnIndex(COLUMN_ITEM_FINISH))
            );
            product.setType_main(c.getString(c.getColumnIndex(COLUMN_TYPE_MAIN)));
            product.setType_one(c.getString(c.getColumnIndex(COLUMN_TYPE_ONE)));
            product.setType_two(c.getString(c.getColumnIndex(COLUMN_TYPE_TWO)));
            product.setType_three(c.getString(c.getColumnIndex(COLUMN_TYPE_THREE)));
            product.setType_four(c.getString(c.getColumnIndex(COLUMN_TYPE_FOUR)));
            product.setType_five(c.getString(c.getColumnIndex(COLUMN_TYPE_FIVE)));
            product.setType_extra(c.getString(c.getColumnIndex(COLUMN_TYPE_EXTRA)));
            product.setType_created_at(c.getString(c.getColumnIndex(COLUMN_TYPE_CREATED_AT)));
            product.setImg_add();
            String img_names = c.getString(c.getColumnIndex(COLUMN_ITEM_IMG_NAMES));
            product.setImg_names(img_names);
            productArr.add(product);
            c.moveToNext();
           // Log.i(TAG, "Item img names: " + img_names + " BY img file: " + img_file);
        }


        db.close();
        return productArr;
    }


    /**
     * Check whether the table needs to be updated or not.
     * if yes, return table name
     * if not, return null
     * @param table_name
     * @param new_date
     * @return table name: need to update, null: do not need to update
     */
    public String needUpdate(String table_name, String new_date){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_RESENT_MODIFIED};
        String where_clause = COLUMN_TABLE_NAME + " like \"" + table_name + "\" AND " + COLUMN_RESENT_MODIFIED + " < Datetime('" + new_date + "') ";
        Cursor c = db.query(true, TABLE_UPDATE, columns, where_clause, null, null, null, null, null);

        int number_of_row = c.getCount();

        db.close();

        if(number_of_row >= 1)
            //if we need to update, return table name
            return table_name;
        else//if we do not need to update, return null
            return null;

    }

    /**
     * Update the COLUMN_RESENT_MODIFIED to the new date after update the table
     * @param table_name
     * @param new_date
     */
    public void updateToNewDate(String table_name, String new_date){
        String whereClause = COLUMN_TABLE_NAME + " like \"" + table_name + "\"";

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESENT_MODIFIED, new_date);
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_UPDATE, values, whereClause, null);
        db.close();
    }

    public Provider getProviderOfItem(int provider_id){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + FTS_TABLE_PROVIDER + " WHERE " + COLUMN_PROVIDER_ID + " = " + Integer.toString(provider_id);
        Provider provider = new Provider();
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();

        provider.set_provider_id(provider_id);
        provider.setProvider_contact(c.getString(c.getColumnIndex(COLUMN_PROVIDER_CONTACT)));
        provider.setProvider_created_at(c.getString(c.getColumnIndex(COLUMN_PROVIDER_CREATED_AT)));
        provider.setProvider_email(c.getString(c.getColumnIndex(COLUMN_PROVIDER_EMAIL)));
        provider.setProvider_extra_info(c.getString(c.getColumnIndex(COLUMN_PROVIDER_EXTRA_INFO)));
        provider.setProvider_link(c.getString(c.getColumnIndex(COLUMN_PROVIDER_LINK)));
        provider.setProvider_title(c.getString(c.getColumnIndex(COLUMN_PROVIDER_TITLE)));

        db.close();
        return provider;
    }


}

















