@file:Suppress("unused")

package com.dao.mobile.artifact.common.data.query

import android.content.ContentValues
import android.util.Log
import com.dao.mobile.artifact.common.data.helper.DBManager

/**
 * Created in 23/08/18 15:36.
 *
 * @author Diogo Oliveira.
 */
class QueryBuilder(private val table: String, private val manager: DBManager)
{
    constructor(join: InnerJoin, manager: DBManager) : this(join.build(), manager)

    private var logger = false

    fun select(vararg columns: String): Select
    {
        val select = Select(logger, table, manager)
        select.columns(*columns)
        return select
    }

    fun exists(): Exists
    {
        return Exists(logger, table, manager)
    }

    fun insert(values: ContentValues): Insert
    {
        return Insert(table, manager, values)
    }

    fun update(values: ContentValues): Update
    {
        return Update(table, manager, values)
    }

    fun delete(): Delete
    {
        return Delete(table, manager)
    }

    fun enableLogging(logger: Boolean): QueryBuilder
    {
        this.logger = logger
        return this
    }

    fun raw(sql: String): QueryCursor
    {
        return raw(sql, null)
    }

    fun raw(sql: String, args: Array<String>?): QueryCursor
    {
        if(logger)
        {
            Log.d(TAG, "Query: $sql")
        }

        return QueryCursor(manager.database.use {
            val cursor = rawQuery(sql, args)
            cursor.close()
            cursor
        })
    }
}