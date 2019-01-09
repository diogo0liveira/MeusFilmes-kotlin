package com.dao.mobile.artifact.common.data.query

/**
 * Created in 23/08/18 14:38.
 *
 * @author Diogo Oliveira.
 */
internal const val TAG = "QUERY-BUILDER"

internal const val SPACE = "\u0020"
internal const val EQUAL = " %s = {%s}"
internal const val DIFFERENT = " %s != {%s}"
internal const val TRIM_DIFF_PARAM = " TRIM(%s) != TRIM({%s})"
internal const val LOWER_DIFF_PARAM = " LOWER(TRIM(%s)) != LOWER(TRIM({%s}))"
internal const val TRIM_EQUALS_PARAM = " TRIM(%s) = TRIM(?)"
internal const val LOWER_EQUALS_PARAM = " LOWER(TRIM(%s)) = LOWER(TRIM({%s}))"
internal const val NOT_NULL = " %s IS NOT NULL"
internal const val IS_NULL = " %s IS NULL"
internal const val IS_EMPTY = " TRIM(%s) = \"\""
internal const val NOT_EMPTY = " TRIM(%s) != \"\""

internal const val COLUMN = "value"
internal const val EXISTS = "EXISTS(SELECT 1 FROM %s) AS $COLUMN"
internal const val EXISTS_ARGS = "EXISTS(SELECT 1 FROM %s WHERE %s) AS $COLUMN"
