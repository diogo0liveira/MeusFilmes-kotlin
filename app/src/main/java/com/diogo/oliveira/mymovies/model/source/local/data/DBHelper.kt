package com.diogo.oliveira.mymovies.model.source.local.data

import android.database.sqlite.SQLiteDatabase
import com.dao.mobile.artifact.common.data.helper.DBConnectionHelper
import org.jetbrains.anko.db.*

/**
 * Created in 03/08/18 14:17.
 *
 * @author Diogo Oliveira.
 */
private const val DB_NAME = "MyMoviesApp.db"
private const val DB_VERSION = 1

abstract class DBHelper<T>(table: String) : DBConnectionHelper<T>(DB_NAME, DB_VERSION, table)
{
    override fun onCreate(database: SQLiteDatabase)
    {
        database.createTable(TABLE_MOVIE, true, *dumpTableMovie())
    }

    override fun onConfigure(database: SQLiteDatabase)
    {
        /* Necessário para CASCADE */
        database.setForeignKeyConstraintsEnabled(true)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        database.dropTable(TABLE_MOVIE, true)
        onCreate(database)
    }

    private fun dumpTableMovie(): Array<Pair<String, SqlType>>
    {
     return  arrayOf(
                COLUMN_MOV_ID           to INTEGER + NOT_NULL + PRIMARY_KEY,
                COLUMN_MOV_TITLE        to TEXT + NOT_NULL,
                COLUMN_MOV_RELEASE_DATE to TEXT + NOT_NULL,
                COLUMN_MOV_OVERVIEW     to TEXT + NOT_NULL,
                COLUMN_MOV_COVER        to TEXT)
    }
}