package com.dao.mobile.artifact.common.exceptions;

/**
 * Created on 17/03/17 16:38.
 *
 * @author Diogo Oliveira.
 */
public class NullBitmapException extends Exception
{
    private final static String MESSAGE = "Bitmap não pode ser nulo.";

    public NullBitmapException()
    {
        super(MESSAGE);
    }
}
