package id.sch.smktelkom_mlg.privateassignment.xirpl207.movieon.db;

public class Table
{
    
    public static String getSQLCreateParam(String name, String[] colName, String[] colType)
    {
        String sql = "CREATE TABLE " + name + "(";
        int i = 0;
        for (; i < colName.length - 1; i++)
        {
            
            sql += colName[i] + " " + colType[i];
            sql += " ,";
            
        }
        sql += colName[i] + " " + colType[i] + ")";
        return sql;
    }
    
    public static String getSQLDropParam(String name)
    {
        return "DROP TABLE IF EXISTS " + name;
    }
    
}
