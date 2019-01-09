package com.dao.mobile.artifact.common.network;

/**
 * Created on 14/03/17 16:33.
 *
 * @author Diogo Oliveira.
 */
public interface ContentType
{
    String MULTIPART_FORM_DATA = "Content-Type: " + MediaType.MULTIPART_FORM_DATA;
    String APPLICATION_JSON = "Content-Type: " + MediaType.APPLICATION_JSON + ";charset=UTF-8";
    String APPLICATION_ZIP = "Content-Type: " + MediaType.APPLICATION_ZIP;
}
