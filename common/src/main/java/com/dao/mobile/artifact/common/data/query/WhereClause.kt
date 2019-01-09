package com.dao.mobile.artifact.common.data.query

/**
 * Created in 23/08/18 14:22.
 *
 * @author Diogo Oliveira.
 */
abstract class WhereClause
{
    internal abstract fun clause(): Clause

    internal abstract fun clause(clause: Clause)
}