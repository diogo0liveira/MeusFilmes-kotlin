package com.dao.mobile.artifact.common.network;

/**
 * Created on 10/03/17 14:30.
 *
 * @author Diogo Oliveira.
 */
public class MediaType
{
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_ZIP = "application/zip";

    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_JPEG = "image/jpeg";

    public static final okhttp3.MediaType MULTIPART_FORM_DATA_TYPE = parse(MULTIPART_FORM_DATA);

    public static okhttp3.MediaType parse(String type)
    {
        return okhttp3.MediaType.parse(type);
    }
}
