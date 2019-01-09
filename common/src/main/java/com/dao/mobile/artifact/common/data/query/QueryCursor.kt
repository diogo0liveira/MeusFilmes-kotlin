@file:Suppress("unused")

package com.dao.mobile.artifact.common.data.query

import android.database.CharArrayBuffer
import android.database.Cursor
import com.dao.mobile.artifact.common.Numbers
import java.io.Closeable

/**
 * Created in 23/08/18 15:32.
 *
 * @author Diogo Oliveira.
 */
class QueryCursor(private val cursor: Cursor) : Closeable
{
    fun getCount(): Int
    {
        return cursor.count
    }

    fun getPosition(): Int
    {
        return cursor.position
    }

    fun move(offset: Int): Boolean
    {
        return cursor.move(offset)
    }

    fun moveToFirst(): Boolean
    {
        return cursor.moveToFirst()
    }

    fun moveToLast(): Boolean
    {
        return cursor.moveToLast()
    }

    fun moveToNext(): Boolean
    {
        return cursor.moveToNext()
    }

    fun moveToPrevious(): Boolean
    {
        return cursor.moveToPrevious()
    }

    fun moveToPosition(position: Int): Boolean
    {
        return cursor.moveToPosition(position)
    }

    fun isFirst(): Boolean
    {
        return cursor.isFirst
    }

    fun isLast(): Boolean
    {
        return cursor.isLast
    }

    fun isBeforeFirst(): Boolean
    {
        return cursor.isBeforeFirst
    }

    fun isAfterLast(): Boolean
    {
        return cursor.isAfterLast
    }

    fun getColumnIndex(column: String): Int
    {
        return cursor.getColumnIndex(column)
    }

    @Throws(IllegalArgumentException::class)
    fun getColumnIndexOrThrow(column: String): Int
    {
        return cursor.getColumnIndexOrThrow(column)
    }

    fun getColumnName(index: Int): String
    {
        return cursor.getColumnName(index)
    }

    fun getColumnNames(): Array<String>
    {
        return cursor.columnNames
    }

    fun getColumnCount(): Int
    {
        return cursor.columnCount
    }

    fun hasColumn(column: String): Boolean
    {
        return !Numbers.isNegative(cursor.getColumnIndex(column))
    }

    fun getString(column: String): String
    {
        return cursor.getString(cursor.getColumnIndex(column))
    }

    fun getShort(column: String): Short
    {
        return cursor.getShort(cursor.getColumnIndex(column))
    }

    fun getInt(column: String): Int
    {
        return cursor.getInt(cursor.getColumnIndex(column))
    }

    fun getLong(column: String): Long
    {
        return cursor.getLong(cursor.getColumnIndex(column))
    }

    fun getFloat(column: String): Float
    {
        return cursor.getFloat(cursor.getColumnIndex(column))
    }

    fun getDouble(column: String): Double
    {
        return cursor.getDouble(cursor.getColumnIndex(column))
    }

    fun getBlob(column: String): ByteArray
    {
        return cursor.getBlob(cursor.getColumnIndex(column))
    }

    fun isNull(column: String): Boolean
    {
        return cursor.isNull(cursor.getColumnIndex(column))
    }

    fun isNull(index: Int): Boolean
    {
        return cursor.isNull(index)
    }

    fun getType(column: String): Int
    {
        return cursor.getType(cursor.getColumnIndex(column))
    }

    fun getTryString(column: String): String?
    {
        val position = cursor.getColumnIndex(column)
        return if(isNull(position)) null else cursor.getString(position)
    }

    fun getTryShort(column: String): Short?
    {
        val position = cursor.getColumnIndex(column)
        return if(isNull(position)) null else cursor.getShort(position)
    }

    fun getTryInt(column: String): Int?
    {
        val position = cursor.getColumnIndex(column)
        return if(isNull(position)) null else cursor.getInt(position)
    }

    fun getTryLong(column: String): Long?
    {
        val position = cursor.getColumnIndex(column)
        return if(isNull(position)) null else cursor.getLong(position)
    }

    fun getTryFloat(column: String): Float?
    {
        val position = cursor.getColumnIndex(column)
        return if(isNull(position)) null else cursor.getFloat(position)
    }

    fun getTryDouble(column: String): Double?
    {
        val position = cursor.getColumnIndex(column)
        return if(isNull(position)) null else cursor.getDouble(position)
    }

    fun getTryBlob(column: String): ByteArray?
    {
        val position = cursor.getColumnIndex(column)
        return if(isNull(position)) null else cursor.getBlob(position)
    }


    fun copyStringToBuffer(index: Int, buffer: CharArrayBuffer)
    {
        cursor.copyStringToBuffer(index, buffer)
    }

    override fun close()
    {
        cursor.close()
    }

    fun isClosed(): Boolean
    {
        return cursor.isClosed
    }
}