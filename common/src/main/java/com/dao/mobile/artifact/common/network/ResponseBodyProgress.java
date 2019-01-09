package com.dao.mobile.artifact.common.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created on 24/05/2016 11:14.
 *
 * @author Diogo Oliveira.
 */
public class ResponseBodyProgress extends ResponseBody
{
    private final ResponseBodyProgressListener listener;
    private final ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private int previus = -1;

    public ResponseBodyProgress(ResponseBody responseBody, ResponseBodyProgressListener listener)
    {
        this.previus = -1;
        this.listener = listener;
        this.responseBody = responseBody;
    }

    @Override
    public MediaType contentType()
    {
        return responseBody.contentType();
    }

    @Override
    public long contentLength()
    {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source()
    {
        if(bufferedSource == null)
        {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source)
    {
        return new ForwardingSource(source)
        {
            final Handler handler = new Handler(Looper.getMainLooper());
            long accumulated = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException
            {
                long read = super.read(sink, byteCount);
                accumulated += (read != -1 ? read : 0);
                handler.post(new Updater(accumulated, responseBody.contentLength(), (read != -1)));
                return read;
            }
        };
    }

    private class Updater implements Runnable
    {
        private final boolean isRead;
        private final long accumulated;
        private final long amount;
        private int percentage;

        Updater(long accumulated, long amount, boolean isRead)
        {
            this.accumulated = accumulated;
            this.isRead = isRead;
            this.amount = amount;
            this.percentage = 0;
        }

        @Override
        public void run()
        {
            if(isRead)
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
    }

    public interface ResponseBodyProgressListener
    {
        void onProgress(int percentage, long accumulated, long amount);
    }
}
