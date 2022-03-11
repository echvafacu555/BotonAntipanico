package echevasoft.antipanico.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {
    public BaseDeDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario (id int,nomcontacto1 text," +
                "numcontacto1 int,nomcontacto2 text, numcontacto2 int)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
