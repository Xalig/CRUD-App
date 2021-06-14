package com.example.crud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf

val database_name = "database"
val table_name = "users"
val col_name = "name"
val col_age = "age"
val col_id = "id"

class DBHelper (var context: Context):SQLiteOpenHelper(context,
database_name, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        var creaTable = " CREATE TABLE " + table_name + "(" +
                col_id +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                col_name + " VARCHAR(256)," + col_age + " INTEGER)"
        db?.execSQL(creaTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun insertData(user: User) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_name, user.name)
        cv.put(col_age, user.age)
        var result = db.insert(table_name, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(): MutableList<User> {
        var list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = " select * from " + table_name
        var result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var user = User()
                user.id = result.getString(result.getColumnIndex(col_id)).toInt()
                user.name = result.getString(result.getColumnIndex(col_name))
                user.age = result.getString(result.getColumnIndex(col_age)).toInt()
                list.add(user)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun updateData() {
        val db = this.readableDatabase
        var query = "select * from $table_name"
        var result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var cv = ContentValues()
                cv.put(col_age, (result.getInt(result.getColumnIndex(col_age))) + 1)
                cv.put(col_name, (result.getString(result.getColumnIndex(col_name))) + " " + "++")
                db.update(
                    table_name, cv, "$col_id=? AND $col_name=?",
                    arrayOf(
                        result.getString(result.getColumnIndex(col_id)), result.getString(
                            result.getColumnIndex(
                                col_name
                            )
                        )
                    )
                )
            } while (result.moveToNext())
        }
    }
    fun deleteData() {
        val db = this.writableDatabase
        db.delete(table_name, null, null)
        db.close()
    }
}
