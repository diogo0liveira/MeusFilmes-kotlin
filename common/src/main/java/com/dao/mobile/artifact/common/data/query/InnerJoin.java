package com.dao.mobile.artifact.common.data.query;

import java.util.ArrayList;

/**
 * Created on 24/06/2016 15:21.
 *
 * @author Diogo Oliveira.
 */
public class InnerJoin
{
    private final String tableIn;
    private final ArrayList<String> tableTo;
    private final ArrayList<String> columnLeft;
    private final ArrayList<String> columnRight;
    private Clause clause;

    public InnerJoin(String table)
    {
        this.tableIn = table;
        this.tableTo = new ArrayList<>(1);
        this.columnLeft = new ArrayList<>(1);
        this.columnRight = new ArrayList<>(1);
    }

    public InnerJoin table(String table)
    {
        this.tableTo.add(table);
        return this;
    }

    public InnerJoin on(String column)
    {
        this.columnLeft.add(column);
        return this;
    }

    public InnerJoin equal(String column)
    {
        this.columnRight.add(column);
        return this;
    }

    public InnerJoin where(Clause clause)
    {
        this.clause = clause;
        return this;
    }

    public String build()
    {
        String query = tableIn.concat(" INNER JOIN ");

        for(String table : tableTo)
        {
            query = query.concat(table);

            for(String left : columnLeft)
            {
                query = query.concat(" ON ").concat(left);

                for(String right : columnRight)
                {
                    query = query.concat(" = ").concat(right);
                }
            }
        }

        if(clause != null)
        {
            query = query.concat(" WHERE ").concat(clause.where());
        }

        return query;
    }
}
