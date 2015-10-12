package com.signalripple.fitnessbike.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtil {

	private SQLiteDatabase db = null;

	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	//
	 public SQLiteDatabase openDatabase() {
		 if(db == null)
//			 openOrCreateDatabase("Student.db", Context.MODE_PRIVATE, null);
		db = SQLiteDatabase.create(null);
	// SQLiteDatabase database = null;
	// try {
	// // 获得localdb.db文件的绝对路径
	// String databaseFilename = YwConst.SDBASE_PATH + "/"
	// + YwConst.DATABASE_FOLDER + "/" + YwConst.DATABASE_FILENAME;
	// File dir = new File(YwConst.SDBASE_PATH + "/"
	// + YwConst.DATABASE_FOLDER);
	// // 如果/sdcard/zft/database目录中存在，创建这个目录
	// if (!dir.exists())
	// dir.mkdir();
	// // 如果在/sdcard/zft/database目录中不存在
	// // pdandroid.db文件不存在
	// // SD卡的目录（/sdcard/zft/database）
	// if (!(new File(databaseFilename)).exists()) {
	// return null;
	// }
	// // 打开/sdcard/zft/database目录中的zftdb.db文件
	// database = SQLiteDatabase.openOrCreateDatabase(databaseFilename,
	// null);
	// } catch (Exception e) {
	// e.printStackTrace();
	// } 
	 return db;
	 }

	public Cursor query(String tableName, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		Cursor cursor = null;
		try {
			db = this.openDatabase();
			if (db == null)
				return null;
			cursor = db.query(tableName, columns, selection, selectionArgs,
					groupBy, having, orderBy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}

	public boolean insert(String tableName, ContentValues values) {
		boolean flag = false;
		try {
			db = this.openDatabase();
			if (db == null)
				return flag;
			db.insert(tableName, null, values);
			flag = true;
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			if (db != null)
				db.close();
		}
		return flag;
	}

	public boolean insertForMap(String tableName, HashMap<?, ?> parameter) {
		boolean flag = false;
		String columns = "", values = "";
		Iterator<?> it = parameter.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			columns += key + ",";
			values += parameter.get(key) + ",";
		}
		if (columns.endsWith(","))
			columns = columns.substring(0, columns.lastIndexOf(","));
		if (values.endsWith(","))
			values = values.substring(0, values.lastIndexOf(","));

		String sql = "insert into " + tableName + " (" + columns + ") values ("
				+ values + ")";

		try {
			db = this.openDatabase();
			if (db == null)
				return flag;
			db.execSQL(sql);
			flag = true;
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			if (db != null)
				db.close();
		}
		return flag;
	}

	public int update(String tableName, ContentValues values,
			String whereClause, String[] whereArgs) {
		int count = -1;
		try {
			db = this.openDatabase();
			if (db == null)
				return count;
			count = db.update(tableName, values, whereClause, whereArgs);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return count;
	}

	public int delete(String tableName, String whereClause, String[] whereArgs) {
		int count = -1;
		try {
			db = this.openDatabase();
			if (db == null)
				return count;
			count = db.delete(tableName, whereClause, whereArgs);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return count;
	}

	public void doForSql(String sql) {
		try {
			db = this.openDatabase();
			if (db == null)
				return;
			db.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return;
	}

	public Cursor queryForSql(String sql, String[] selectionArgs) {
		Cursor cursor = null;
		try {
			db = this.openDatabase();
			if (db == null)
				return cursor;
			cursor = db.rawQuery(sql, selectionArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}

	private DatabaseUtil dbUtil = new DatabaseUtil();

	public String query() {
		JSONArray jsonArray = new JSONArray();
		try {
			Cursor cursor = dbUtil.query("User", null, null, null, null, null,
					"id");
			if (cursor != null) {
				while (cursor.moveToNext()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id",
							cursor.getInt(cursor.getColumnIndex("id")));
					jsonObject.put("name",
							cursor.getString(cursor.getColumnIndex("name")));
					jsonObject.put("tel",
							cursor.getString(cursor.getColumnIndex("tel")));
					jsonObject.put("parentid",
							cursor.getInt(cursor.getColumnIndex("parentid")));

					jsonArray.put(jsonObject);
				}
				cursor.close();
			}
			if (dbUtil.getDb() != null)
				dbUtil.getDb().close();
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}