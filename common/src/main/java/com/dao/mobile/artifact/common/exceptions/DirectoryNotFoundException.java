package com.dao.mobile.artifact.common.exceptions;

/**
 * Created on 17/03/17 17:18.
 *
 * @author Diogo Oliveira.
 */
public class DirectoryNotFoundException extends Exception
{
    private final static String MESSAGE = "Armazenamento compartilhado não disponível no momento.";

    public DirectoryNotFoundException()
    {
        super(MESSAGE);
    }
}
