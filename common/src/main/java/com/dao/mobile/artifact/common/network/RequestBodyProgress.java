package com.dao.mobile.artifact.common.network;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created on 12/05/2016 11:53.
 *
 * @author Diogo Oliveira.
 */
public class RequestBodyProgress extends RequestBody
{
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private int previus = -1;

    private final RequestBodyProgressListener listener;
    private final File file;

    public RequestBodyProgress(File file, RequestBodyProgressListener listener)
    {
        this.file = file;
        this.previus = -1;
        this.listener = listener;
    }

    @Override
    public void writeTo(@NotNull BufferedSink sink) throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long length = file.length();
        long uploaded = 0;

        try
        {
            int read = 0;
            Handler handler = new Handler(Looper.getMainLooper());

            do
            {
                uploaded += read;
                handler.post(new Updater(uploaded, length));
                sink.write(buffer, 0, read);
            } while((read = fileInputStream.read(buffer)) != -1);
        }
        finally
        {
            try
            {
                fileInputStream.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public okhttp3.MediaType contentType()
    {
        return okhttp3.MediaType.parse(MediaType.MULTIPART_FORM_DATA);
    }

    private class Updater implements Runnable
    {
        private final long accumulated;
        private final long amount;
        private int percentage;

        Updater(long accumulated, long amount)
        {
            this.accumulated = accumulated;
            this.amount = amount;
            this.percentage = 0;
        }

        @Override
        public void run()
        {
            percentage = ((int)(100 * accumulated / amount));

            if(previus < percentage)
            {
                //Log.d(Extras.TAG, String.valueOf(percentage));
                listener.onProgress(percentage, accumulated, amount);
            }

            previus = percentage;
        }
    }

    public interface RequestBodyProgressListener
    {
        void onProgress(int percentage, long accumulated, long amount);
    }
}
