@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.dao.mobile.artifact.common.data.query

/**
 * Created in 23/08/18 13:03.
 *
 * @author Diogo Oliveira.
 */
class Clause
{
    private var where: StringBuilder = StringBuilder(1)
    private var args: MutableList<Pair<String, Any>> = arrayListOf()

    fun raw(clause: String): Clause
    {
        this.where.append(clause)
        return this
    }

    fun equal(arg: Pair<String, Any>): Clause
    {
        return equal(Predicate.AND, arg)
    }

    fun equal(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        build(predicate, EQUAL, arg)
        return this
    }

    fun equalsTrim(arg: Pair<String, Any>): Clause
    {
        return equalsTrim(Predicate.AND, arg)
    }

    fun equalsTrim(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        build(predicate, TRIM_EQUALS_PARAM, arg)
        return this
    }

    fun equalsLower(arg: Pair<String, Any>): Clause
    {
        return equalsLower(Predicate.AND, arg)
    }

    fun equalsLower(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        build(predicate, LOWER_EQUALS_PARAM, arg)
        return this
    }

    fun diff(arg: Pair<String, Any>): Clause
    {
        return diff(Predicate.AND, arg)
    }

    fun diff(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        build(predicate, DIFFERENT, arg)
        return this
    }

    fun diffTrim(arg: Pair<String, Any>): Clause
    {
        return diffTrim(Predicate.AND, arg)
    }

    fun diffTrim(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        build(predicate, TRIM_DIFF_PARAM, arg)
        return this
    }

    fun diffLower(arg: Pair<String, Any>): Clause
    {
        return diffLower(Predicate.AND, arg)
    }

    fun diffLower(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        build(predicate, LOWER_DIFF_PARAM, arg)
        return this
    }

    fun notNull(arg: Pair<String, Any>): Clause
    {
        return notNull(Predicate.AND, arg)
    }

    fun notNull(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        build(predicate, NOT_NULL, arg)
        return this
    }

    fun isNull(arg: Pair<String, Any>): Clause
    {
        return isNull(Predicate.AND, arg)
    }

    fun isNull(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        build(predicate, IS_NULL, arg)
        return this
    }

    fun notEmpty(arg: Pair<String, Any>): Clause
    {
        return notEmpty(Predicate.AND, arg)
    }

    fun notEmpty(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        build(predicate, NOT_EMPTY, arg)
        return this
    }

    fun notNullOrEmpty(arg: Pair<String, Any>): Clause
    {
        return notNullOrEmpty(Predicate.AND, arg)
    }

    fun notNullOrEmpty(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        val restriction = NOT_NULL + SPACE + Predicate.AND + NOT_EMPTY
        build(predicate, restriction, arg)
        return this
    }

    fun isNullOrEmpty(arg: Pair<String, Any>): Clause
    {
        return isNullOrEmpty(Predicate.AND, arg)
    }

    fun isNullOrEmpty(predicate: Predicate, arg: Pair<String, Any>): Clause
    {
        val restriction = IS_NULL + SPACE + Predicate.AND + IS_EMPTY
        return this
    }

    fun where(): String
    {
        return where.toString().trim { it <= ' ' }
    }

    fun args(): Array<Pair<String, Any>>
    {
        return args.toTypedArray()
    }

    fun argsToString(): Array<String>
    {
        return args.flatMap { (column, _) -> listOf(column) }.toTypedArray()
    }

    private fun build(predicate: Predicate, restriction: String, arg: Pair<String, Any>)
    {
        if(where.isNotEmpty())
        {
            where.append(predicate.value)
        }

        where.append(String.format(restriction, arg.first, arg.first))
        args.add(arg)
    }

    enum class Predicate constructor(val value: String)
    {
        AND(" AND"), OR(" OR")
    }
}