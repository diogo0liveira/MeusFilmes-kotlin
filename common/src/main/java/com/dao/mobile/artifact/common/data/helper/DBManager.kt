package com.dao.mobile.artifact.common.data.helper

import android.database.sqlite.SQLiteDatabase
import com.dao.mobile.artifact.common.ApplicationController
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

/**
 * Created in 22/08/18 14:22.
 *
 * @author Diogo Oliveira.
 */
object DBManager
{
    private lateinit var connection : DBConnectionHelper<*>
    internal val database: Database by lazy { Database(connection) }

    fun initialize(connection: DBConnectionHelper<*>): DBManager
    {
        this.connection = connection
        return this
    }

    fun readable(): SQLiteDatabase
    {
        return database.readableDatabase
    }

    fun writable(transaction: Boolean = false): SQLiteDatabase
    {
        val database = database.writableDatabase

        if(transaction)
        {
            database.beginTransaction()
        }

        return database
    }

    internal class Database(private val helper: DBConnectionHelper<*>) :
            ManagedSQLiteOpenHelper(ApplicationController.getInstance().getContext(), helper.name, version = helper.version)
    {
        override fun onCreate(database: SQLiteDatabase)
        {
            helper.onCreate(database)
        }

        override fun onConfigure(database: SQLiteDatabase)
        {
            helper.onConfigure(database)
        }

        override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int)
        {
            helper.onUpgrade(database, oldVersion, newVersion)
        }
    }
}