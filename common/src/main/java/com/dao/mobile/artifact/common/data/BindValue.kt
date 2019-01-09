package com.dao.mobile.artifact.common.data

import android.database.sqlite.SQLiteStatement
import androidx.annotation.NonNull
import com.dao.mobile.artifact.common.Numbers
import java.math.BigDecimal

/**
 * Created in 22/08/18 16:29.
 *
 * @author Diogo Oliveira.
 */
class BindValue(@NonNull private var statement: SQLiteStatement, private val countParams: Int)
{
    private var index: Int = 1

    fun set(value: Int?)
    {
        if(isNull(value)) statement.bindNull(index)
        else statement.bindLong(index, value!!.toLong())
        next()
    }

    fun set(value: Short?)
    {
        if(isNull(value)) statement.bindNull(index)
        else statement.bindLong(index, value!!.toLong())
        next()
    }

    fun set(value: Long?)
    {
        if(isNull(value)) statement.bindNull(index)
        else statement.bindLong(index, value!!)
        next()
    }

    fun set(value: Double?)
    {
        if(isNull(value)) statement.bindNull(index)
        else statement.bindDouble(index, value!!)
        next()
    }

    fun set(value: BigDecimal)
    {
        if(isNull(value)) statement.bindNull(index)
        else statement.bindDouble(index, Numbers.valueOf(value)!!)
        next()
    }

    fun set(value: String)
    {
        if(isNull(value)) statement.bindNull(index)
        else statement.bindString(index, value)
        next()
    }

    fun set(value: ByteArray)
    {
        if(isNull(value)) statement.bindNull(index)
        else statement.bindBlob(index, value)
        next()
    }

    private operator fun next()
    {
        index = if(index == countParams) 1 else index + 1
    }

    private fun isNull(value: Any?): Boolean
    {
        return value == null
    }
}