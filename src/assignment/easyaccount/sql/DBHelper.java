
package assignment.easyaccount.sql;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import assignment.easyaccount.entity.Account;
import assignment.easyaccount.entity.AccountEnum;
import assignment.easyaccount.util.CalendarUtil;


public class DBHelper
{
	private Context context;
	private SQLiteDatabase dbInstance;
	private DBCreator dbCreator;
	private static final String DB_NAME = "db_easyaccount";
	private static final int DB_VERSION = 1;
	
	public static final String TABLE_NAME="myAccount";
	public static final String COLUMN_ID = "account_id";
	public static final String COLUMN_TYPE="account_type";
	public static final String COLUMN_MONEY="account_money";
	public static final String COLUMN_DATE = "account_date";
	
	private static final String CREATE_TABLE= new StringBuffer().append("Create table ").append(TABLE_NAME)
								.append(" (")
								.append(COLUMN_ID).append(" integer primary key,")
								.append(COLUMN_TYPE).append(" integer not null,")
								.append(COLUMN_MONEY).append(" real not null,")
								.append(COLUMN_DATE).append(" text not null)")
								.toString();
	public DBHelper(Context context)
	{
		this.context = context;
	}
	public void open()
	{
		dbCreator = new DBCreator(context,DB_NAME,null,DB_VERSION,CREATE_TABLE,TABLE_NAME);
		dbInstance = dbCreator.getWritableDatabase();
	}
	public void close()
	{
		dbCreator.close();
	}
	public void insert(ContentValues values)
	{
		dbInstance.insert(TABLE_NAME, null, values);
	}
	public void update(ContentValues values,String whereClause,String[] whereArgs)
	{
		dbInstance.update(TABLE_NAME, values, whereClause, whereArgs);
	}
	public void delete(String whereClause, String[] whereArgs)
	{
		dbInstance.delete(TABLE_NAME, whereClause, whereArgs);
	}

	public Cursor query(String sql, String[] selectionArgs)
	{
		return dbInstance.rawQuery(sql, selectionArgs);
	}
	public ArrayList<Account> getQueryAccountList(String sql,String[] args)
	{
		ArrayList<Account> accoutList = new ArrayList<Account>();
		open();
		Cursor cursor = query(sql, args);
		while (cursor.moveToNext()) {
			Account account = new Account();
			account.setAccountId(cursor.getInt(0));
			account.setType(AccountEnum.getAccountEnum(cursor.getInt(1)));
			account.setMoney(cursor.getFloat(2));
			account.setDate(cursor.getString(3));
			accoutList.add(account);
		}
		if(!cursor.isClosed())
		{
			cursor.close();
		}
		close();
		return accoutList;
	}
	private class DBCreator extends SQLiteOpenHelper
	{
		private Context context;
		private String createTableSql;
		private String tableName;
		

		public DBCreator(Context context, String dbname, CursorFactory factory,
				int version,String createTableSql,String tableName)
		{
			super(context, dbname, factory, version);
			this.context = context;
			this.createTableSql = createTableSql;
			this.tableName = tableName;
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(createTableSql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			db.execSQL("drop table if exists "+tableName);
			onCreate(db);
		}
	}
}
