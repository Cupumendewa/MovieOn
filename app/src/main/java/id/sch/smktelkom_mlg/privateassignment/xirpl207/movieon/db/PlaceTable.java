package id.sch.smktelkom_mlg.privateassignment.xirpl207.movieon.db;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.learn.localdatabase1.model.Place;

public class PlaceTable extends Table
{
    
    public static final String NAME = "tplace";
    
    public static final String[] COLNAME =
            new String[]{"JUDUL", "DESKRIPSI", "DETAIL", "LOKASI", "FOTO"};
    public static final String[] COLTYPE = new String[]{"TEXT", "TEXT", "TEXT", "TEXT", "TEXT"};
    
    public static List<Place> ITEMS = new ArrayList<Place>();
    
    public static String getSQLCreate()
    {
        return getSQLCreateParam(NAME, COLNAME, COLTYPE);
    }
    
    public static String getSQLDrop()
    {
        return getSQLDropParam(NAME);
    }
    
    private static ContentValues getValues(Place place)
    {
        ContentValues values = new ContentValues();
        values.put(COLNAME[0], place.judul);
        values.put(COLNAME[1], place.deskripsi);
        values.put(COLNAME[2], place.detail);
        values.put(COLNAME[3], place.lokasi);
        values.put(COLNAME[4], place.foto);
        return values;
    }
    
    public static void getAll(DatabaseHandler db)
    {
        ITEMS.clear();
        Cursor cursor = db.getAll(NAME);
        
        if (cursor != null && cursor.getCount() > 0)
        {
            do
            {
                Place place = new Place(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                ITEMS.add(place);
            } while (cursor.moveToNext());
        }
    }
    
    public static void getPlaceWhereJudul(DatabaseHandler db, String judul)
    {
        ITEMS.clear();
        String query = "SELECT " + NAME + "." + COLNAME[0] + ", " + NAME + "." + COLNAME[1] +
                ", " + NAME + "." + COLNAME[2] + ", " + NAME + "." + COLNAME[3] +
                ", " + NAME + "." + COLNAME[4] +
                " FROM " + NAME +
                " WHERE " + NAME + "." + COLNAME[0] + "=?";
        Cursor cursor = db.getWhere(query, new String[]{judul});
        if (cursor != null && cursor.getCount() > 0)
        {
            do
            {
                Place place = new Place(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                ITEMS.add(place);
            } while (cursor.moveToNext());
        }
    }
    
    public static void getPlaceLike(DatabaseHandler db, String text)
    {
        ITEMS.clear();
        String query = "SELECT * FROM " + NAME +
                " WHERE " + NAME + "." + COLNAME[0] + " LIKE ?" +
                " OR " + NAME + "." + COLNAME[1] + " LIKE ?" +
                " OR " + NAME + "." + COLNAME[3] + " LIKE ?";
        Cursor cursor = db.getWhere(query,
                new String[]{"%" + text + "%", "%" + text + "%", "%" + text + "%"});
        if (cursor != null && cursor.getCount() > 0)
        {
            do
            {
                Place place =
                        new Place(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                                cursor.getString(3), cursor.getString(4));
                ITEMS.add(place);
            } while (cursor.moveToNext());
        }
    }
    
    public static boolean isEmpty(DatabaseHandler db)
    {
        return !db.isExist(NAME, null, null, null);
    }
    
    private static boolean isExist(DatabaseHandler db, String colName, String value)
    {
        return db.isExist(NAME, new String[]{colName}, colName + "=?", new String[]{value});
    }
    
    public static boolean isExistPlace(DatabaseHandler db, String judul)
    {
        return isExist(db, COLNAME[0], judul);
    }
    
    public static void add(DatabaseHandler db, Place place)
    {
        db.add(NAME, getValues(place));
    }
    
    public static void updateWhereJudul(DatabaseHandler db, String judul, Place place)
    {
        db.update(NAME, getValues(place), COLNAME[0] + "=?", new String[]{judul});
    }
    
    private static void delete(DatabaseHandler db, String colName, String value)
    {
        db.delete(NAME, colName + "=?", new String[]{value});
    }
    
    public static void delete(DatabaseHandler db, Place place)
    {
        delete(db, COLNAME[0], place.judul);
    }
    
}
