package com.dao.mobile.artifact.common.data.query

import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log.d
import com.dao.mobile.artifact.common.data.helper.DBManager
import org.jetbrains.anko.db.select

/**
 * Created in 23/08/18 15:00.
 *
 * @author Diogo Oliveira.
 */
class Select(private val logger: Boolean = false, private val table: String, private val manager: DBManager)
{
    private val where: Where by lazy { Where() }
    private var columns: Array<String> = arrayOf()

    fun columns(vararg columns: String)
    {
        this.columns = arrayOf(*columns)
    }

    fun where(clause: Clause): Where
    {
        this.where.clause(clause)
        return where
    }

    fun exec(): QueryCursor
    {
        if(logger)
        {
            printLogging()
        }

      return  manager.database.use {
            select(table)
                    .columns(*columns)
                    .whereArgs(where.clause.where(), *where.clause.args())
                    .groupBy(where.group)
                    .having(where.having)
                    .groupBy(where.sort)
                    .limit(where.limit)
                    .exec { QueryCursor(this) }
        }
    }

    private fun printLogging()
    {
        val query = SQLiteQueryBuilder.buildQueryString(
                false,
                table,
                columns,
                where.clause.where(),
                where.group,
                where.having,
                where.sort,
                where.limit.toString())

        if(where.clause.args().isNotEmpty())
        {
            where.clause.args().forEach {
                query.replaceFirst("\\?", it.second.toString())
            }
        }

        d(TAG, query)
    }


    inner class Where : WhereClause()
    {
        var clause: Clause = Clause()
        var having: String = ""
        var group: String = ""
        var sort: String = ""
        var limit: Int = -1

        override fun clause(): Clause
        {
            return clause
        }

        override fun clause(clause: Clause)
        {
            this.clause = clause
        }

        fun exec(): QueryCursor
        {
            return this@Select.exec()
        }

        fun groupBy(group: String): Where
        {
            this.group = group
            return this
        }

        fun having(having: String): Where
        {
            this.having = having
            return this
        }

        fun sort(sort: String): Where
        {
            this.sort = sort
            return this
        }

        fun limit(limit: Int): Where
        {
            this.limit = limit
            return this
        }
    }
}