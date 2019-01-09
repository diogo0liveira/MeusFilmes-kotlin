package com.dao.mobile.artifact.common.data

import com.dao.mobile.artifact.common.Numbers

/**
 * Created in 22/08/18 16:40.
 *
 * @author Diogo Oliveira.
 */
class ResultDatabase(private val action: Action)
{
    private var success: Boolean = false
    private var rows: MutableList<Long> = arrayListOf()
    private var row: Long = -1
    private var count: Int = 0

    /**
     * Id do objeto inserido ou "-1" quando update.
     *
     * @return Id do objeto.
     */
    fun getRow(): Long
    {
        return row
    }

    /**
     * Quantidade de linhas afetadas após o delete.
     *
     * @return Quantidade de linhas.
     */
    fun getCount(): Int
    {
        return count
    }

    /**
     * Lista de rows quando utilizado statementInsert.
     *
     * @return Id do objeto.
     */
    fun getRows(): List<Long>
    {
        return rows
    }

    /**
     * Resultado da operação
     *
     * @return TRUE para operação bem sucedida.
     */
    fun isSuccessful(): Boolean
    {
        return success
    }

    fun getAction(): Action
    {
        return action
    }

    fun setResult(success: Boolean)
    {
        this.success = success
    }

    fun forInsert(row: Long)
    {
        this.success = !Numbers.isEmpty(row)
        this.row = row
    }

    fun forUpdate(row: Int)
    {
        this.success = !Numbers.isNegative(row)
        this.row = row.toLong()
    }

    fun forDelete(count: Int)
    {
        this.success = !Numbers.isNegative(count)
        this.count = count
    }

    fun forStmInsert(id: Long)
    {
        rows.add(id)
        this.success = !Numbers.isNegative(id)
    }

    fun forStmUpdate(row: Int)
    {
        this.count += row
        this.success = !Numbers.isNegative(row)
    }

    fun forStmDelete(row: Int)
    {
        this.count += row
        this.success = !Numbers.isNegative(row)
    }

    /**
     * Controle para identificar se a operação executado no banco de dados foi um INSERT
     *
     * @return true caso a operação executada seja INSERT
     */
    fun isInsert(): Boolean
    {
        return action == Action.INSERT
    }

    /**
     * Controle para identificar se a operação executado no banco de dados foi um UPDATE
     *
     * @return true caso a operação executada seja UPDATE
     */
    fun isUpdate(): Boolean
    {
        return action == Action.UPDATE
    }

    /**
     * Controle para identificar se a operação executado no banco de dados foi um DELETE
     *
     * @return true caso a operação executada seja DELETE
     */
    fun isDelete(): Boolean
    {
        return action == Action.DELETE
    }
}