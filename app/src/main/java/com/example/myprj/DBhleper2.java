package com.example.myprj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhleper2 extends SQLiteOpenHelper {

    public static final String DBNAME = "New.db";

    public DBhleper2(Context context) {
        super(context, "New.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        // Créer la table pour stocker les informations du formulaire
        MyDB.execSQL("CREATE TABLE form_data ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT,"
                + "category TEXT,"
                + "sector TEXT,"
                + "city TEXT,"
                + "contract_type TEXT,"
                + "description TEXT)");

        // Créer la table pour les utilisateurs


    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS form_data");

        onCreate(MyDB);
    }




    // Méthode pour insérer les données du formulaire dans la table form_data
    public boolean insertFormData(String title, String category, String sector, String city, String contractType, String description) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("category", category);
        contentValues.put("sector", sector);
        contentValues.put("city", city);
        contentValues.put("contract_type", contractType);
        contentValues.put("description", description);

        long result = MyDB.insert("form_data", null, contentValues);
        return result != -1; // Return true if insertion is successful, false otherwise
    }
    public int getFormCountInCity(String city) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT COUNT(*) FROM form_data WHERE city = ?", new String[]{city});
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }
}
