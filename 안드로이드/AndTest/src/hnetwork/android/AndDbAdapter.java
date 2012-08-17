package hnetwork.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


 
public class AndDbAdapter {

	private static final String FIELD1 = "name";
	private static final String FIELD2 = "passwd";
	private static final String KEY    = "_id";
	
	private static final String TAG = "AndDbAdapter";
	private static final String DATABASE_NAME = "data";
	private static final String DATABASE_TABLE = "notes";
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_CREATE = 
		"CREATE TABLE notes (_id integer primary key autoincrement, "
		+ "name text not null, passwd text not null);";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;
	
	public AndDbAdapter(Context ctx)
	{
		this.mCtx = ctx;
	}
	public AndDbAdapter open() throws android.database.SQLException
	{
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();  // get database
		
		// mDbHelper.onUpgrade(mDb, 1, 2);
		return this;
	}
	
	public void close()
	{
		mDbHelper.close();
	}	
	
	/**
	 * Insert data
	 * @param name
	 * @param passwd
	 * @return
	 */
	public long insertData(String  name, String passwd)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(FIELD1, name);
		initialValues.put(FIELD2, passwd);
		
		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}
	/**
	 * Update data
	 * 
	 * @param key
	 * @param name
	 * @param passwd
	 * @return
	 */
	public boolean updateData(long key, String name, String passwd)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(FIELD1, name);
		initialValues.put(FIELD2, passwd);
		
		return (mDb.update(DATABASE_TABLE, initialValues, KEY+"="+key, null) > 0);
	}
	/**
	 * Delete data
	 * @param key
	 * @return
	 */
	public boolean deleteData(long key)
	{
		return mDb.delete(DATABASE_TABLE, KEY+"-"+key, null) > 0;
	}
	/**
	 * All fetch
	 * @return
	 */
	public Cursor fetchAllData()
	{
		String[] columns = new String[] { KEY, FIELD1, FIELD2 };
		
		return mDb.query(DATABASE_TABLE, columns, null, null, null, null, null);
	}
	
	public Cursor fetchData(long key) throws SQLException
	{
		String[] columns = new String[] { KEY, FIELD1, FIELD2 };
		String where = KEY+"="+key;
		Cursor mCursor = null;
		
		mCursor = mDb.query(true, DATABASE_TABLE, columns, where, null, null, null, null, null);
		
		if (mCursor != null)
			mCursor.moveToFirst();		// Cursor move first row
		
		return mCursor;
	}
	
	/**
	 * DatabaseHelper class
	 *
	 * This class create database.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion+", which will destroy all old data");
			
			db.execSQL("DROP TABLE IF EXISTS notes");
			onCreate(db);
		}
		
		
	}
}
