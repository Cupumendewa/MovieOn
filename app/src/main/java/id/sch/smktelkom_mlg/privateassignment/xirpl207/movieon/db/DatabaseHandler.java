package id.sch.smktelkom_mlg.privateassignment.xirpl207.movieon.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public abstract class DatabaseHandler extends SQLiteOpenHelper
{
    
    public DatabaseHandler(Context context, String DBName, int DBVer)
    {
        super(context, DBName, null, DBVer);
    }
    
    public DatabaseHandler(Context context, final String DBPath, final String DBName, int DBVer)
    {
        super(new ContextWrapper(context)
        {
            @Override
            public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                                       SQLiteDatabase.CursorFactory factory)
            {
                
                File dir = new File(DBPath);
                if (!dir.exists())
                {
                    if (!dir.mkdirs())
                    {
                        return null;
                    }
                }
                return SQLiteDatabase.openDatabase(DBPath + File.separator + DBName, null,
                        SQLiteDatabase.CREATE_IF_NECESSARY);
            }
        }, DBName, null, DBVer);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        createTables(db);
    }
    
    protected abstract void createTables(SQLiteDatabase db);
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        dropTables(db);
        
        onCreate(db);
    }
    
    protected abstract void dropTables(SQLiteDatabase db);
    
    public void reCreateTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        dropTables(db);
        onCreate(db);
    }
    
    public void startTrans()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
    }
    
    public void endTrans()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    
    public void closeDB()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();
    }
    
    public long add(String tableName, ContentValues values)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(tableName, null, values);
    }
    
    public int update(String tableName, ContentValues values)
    {
        return update(tableName, values, null, null);
    }
    
    public int update(String tableName, ContentValues values, String whereClause,
                      String[] whereArgs)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(tableName, values, whereClause, whereArgs);
    }
    
    public int delete(String tableName, String whereClause, String[] whereArgs)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, whereClause, whereArgs);
    }
    
    public int countTable(String tableName)
    {
        String selectQuery = "SELECT * FROM " + tableName;
        return count(selectQuery);
    }
    
    public int count(String selectQuery)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    
    public boolean isExist(String tableName, String[] cols, String whereClause, String[] whereArgs)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(tableName, cols, whereClause, whereArgs, null, null, null, "1");
        
        if (cursor != null)
        {
            if (cursor.getCount() > 0)
            {
                db.close();
                return true;
            }
        }
        
        db.close();
        return false;
    }
    
    public Cursor getWhere(String tableName, String[] cols, String whereClause, String[] whereArgs)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(tableName, cols, whereClause, whereArgs, null, null, null, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        
        return cursor;
    }
    
    public Cursor getWhere(String selectQuery, String[] queryArgs)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(selectQuery, queryArgs);
        
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        
        return cursor;
    }
    
    public Cursor getAll(String tableName)
    {
        String selectQuery = "SELECT  * FROM " + tableName;
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        
        return cursor;
    }
    
}
