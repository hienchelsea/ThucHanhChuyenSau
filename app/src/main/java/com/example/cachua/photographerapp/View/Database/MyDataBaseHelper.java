package com.example.cachua.photographerapp.View.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_photo";

    private static final String TABLE_USERS = "users";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_REVIEWS = "reviews";
    private static final String TABLE_OPTIONS = "options";
    private static final String TABLE_MESSAGES = "messages";
    private static final String TABLE_LOCATIONS = "locations";
    private static final String TABLE_IMAGES = "images";
    private static final String TABLE_COUPONS = "coupons";
    private static final String TABLE_ALBUMS = "albums";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BIO = "bio";
    private static final String COLUMN_BIRTH= "birth";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone_number";
    private static final String COLUMN_ROLE = "role";

    private static final String COLUMN_COUPON = "coupon";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_OPTIONS = "options";
    private static final String COLUMN_PEOPLE = "people";
    private static final String COLUMN_PHOTOGRAPHER = "photographer";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_TOTAL = "total";
    private static final String COLUMN_USER = "user";


    private static final String COLUMN_REVIEW = "review";
    private static final String COLUMN_RATE = "rate";


    private static final String COLUMN_ACCESSORIES = "accessories";
    private static final String COLUMN_OWNER = "owner";
    private static final String COLUMN_PRICE_PER_HOUR = "price_per_hour";
    private static final String COLUMN_PRICE_PER_DAY = "price_per_day";
    private static final String COLUMN_PRINTS = "prints";
    private static final String COLUMN_SHOTS = "shots";
    private static final String COLUMN_TYPE = "type";


    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_RECEIVER = "receiver";
    private static final String COLUMN_SENDER = "sender";


    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_TAGS = "tags";


    private static final String COLUMN_ALBUM = "album";
    private static final String COLUMN_CAPTION = "caption";
    private static final String COLUMN_PATH = "path";


    private static final String COLUMN_END_DATE = "endDate";
    private static final String COLUMN_START_DATE = "startDate";
    private static final String COLUMN_VALUE = "value";








    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String scriptCreateTableAlbum = "CREATE TABLE " + TABLE_ALBUMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_OWNER + " TEXT,"
                + COLUMN_TAGS + " TEXT " + ")";

        db.execSQL(scriptCreateTableAlbum);

        String scriptCreateTableCoupons = "CREATE TABLE " + TABLE_COUPONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_END_DATE + " TEXT,"
                + COLUMN_START_DATE + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_OWNER + " TEXT,"
                + COLUMN_VALUE + " INTEGER " + ")";

        db.execSQL(scriptCreateTableCoupons);

        String scriptCreateTableImages = "CREATE TABLE " + TABLE_IMAGES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ALBUM + " TEXT,"
                + COLUMN_CAPTION + " TEXT,"
                + COLUMN_OWNER + " TEXT,"
                + COLUMN_PATH + " TEXT,"
                + COLUMN_TAGS + " TEXT " + ")";

        db.execSQL(scriptCreateTableImages);

        String scriptCreateTableLocations = "CREATE TABLE " + TABLE_LOCATIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_DESCRIPTION+ " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_TAGS + " TEXT " + ")";

        db.execSQL(scriptCreateTableLocations);

        String scriptCreateTableMessages = "CREATE TABLE " + TABLE_MESSAGES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_MESSAGE+ " TEXT,"
                + COLUMN_RECEIVER + " TEXT,"
                + COLUMN_SENDER + " TEXT " + ")";

        db.execSQL(scriptCreateTableMessages);

        String scriptCreateTableOptions = "CREATE TABLE " + TABLE_OPTIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ACCESSORIES+ " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_NOTE + " TEXT,"
                + COLUMN_OWNER + " TEXT,"
                + COLUMN_PRICE_PER_DAY + " INTEGER,"
                + COLUMN_PRICE_PER_HOUR + " INTEGER,"
                + COLUMN_PRINTS + " INTEGER,"
                + COLUMN_SHOTS + " INTEGER,"
                + COLUMN_TYPE + " TEXT" + ")";

        db.execSQL(scriptCreateTableOptions);

        String scriptCreateTableOrders = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_COUPON+ " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_NOTE + " TEXT,"
                + COLUMN_OPTIONS + " TEXT,"
                + COLUMN_PEOPLE + " INTEGER,"
                + COLUMN_PHOTOGRAPHER + " TEXT,"
                + COLUMN_STATUS + " TEXT,"
                + COLUMN_TOTAL + " INTEGER,"
                + COLUMN_USER + " TEXT" + ")";

        db.execSQL(scriptCreateTableOrders);

        String scriptCreateTableUsers = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_BIO+ " TEXT,"
                + COLUMN_BIRTH + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_ROLE + " TEXT" + ")";

        db.execSQL(scriptCreateTableUsers);

        String scriptCreateTableReviews = "CREATE TABLE " + TABLE_REVIEWS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_USER+ " TEXT,"
                + COLUMN_PHOTOGRAPHER + " TEXT,"
                + COLUMN_REVIEW + " TEXT,"
                + COLUMN_RATE + " INTEGER" + ")";

        db.execSQL(scriptCreateTableReviews);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUPONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBUMS);

    }
}
