package com.dao.mobile.artifact.common.file;

import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;

import com.dao.mobile.artifact.common.AppEnvironment;
import com.dao.mobile.artifact.common.ApplicationController;
import com.dao.mobile.artifact.common.Strings;
import com.dao.mobile.artifact.common.exceptions.DirectoryNotFoundException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.nio.channels.FileChannel;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.annotation.WorkerThread;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created on 02/05/2016 17:34.
 *
 * @author Diogo Oliveira.
 */
public class Files
{
    public final static String JPG = ".jpg";
    public final static String ZIP = ".zip";
    public final static String PNG = ".png";
    public final static String DB = ".db";

    public final static int CACHE = 0;
    public final static int LOCAL = 1;

    public final static int EOF = -1;
    public final static int BUFFER_SIZE = 1024 * 4;
    public final static byte[] BUFFER = new byte[BUFFER_SIZE];

    @Retention(SOURCE)
    @IntDef({LOCAL, CACHE})
    @interface DirType
    {
    }

    @Retention(SOURCE)
    @StringDef({JPG, PNG, ZIP, DB})
    @interface Extension
    {
    }

    /**
     * Cria um novo arquivo.
     *
     * @param path "caminho do arquivo".
     *
     * @return (novo arquivo).
     */
    public static File create(String path)
    {
        if(!Strings.isEmpty(path))
        {
            try
            {
                File file = new File(path);

                if(file.exists() || file.createNewFile())
                {
                    return file;
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Cria um novo arquivo no diretório de cache da aplicação.
     *
     * @param name "nome para o arquivo".
     *
     * @return (novo arquivo).
     */
    public static File createCache(String name)
    {
        try
        {
            return create(AppEnvironment.pathCache().concat(File.separator).concat(name));
        }
        catch(DirectoryNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Extrai nome do arquivo de um caminho.
     *
     * @param path "caminho do arquivo".
     *
     * @return (nome do arquivo sem extensão).
     */
    public static String getName(String path)
    {
        if(!Strings.isEmpty(path) && (path.lastIndexOf(File.separator) > -1))
        {
            return path.substring(path.lastIndexOf(File.separator) + 1);
        }
        else
        {
            return path;
        }
    }

    public static String formatSize(long size)
    {
        return Formatter.formatShortFileSize(ApplicationController.getInstance().getContext(), size);
    }

    public static String subExtension(File file)
    {
        if(notNull(file))
        {
            return file.getName().substring(0, file.getName().lastIndexOf("."));
        }

        return null;
    }

    @WorkerThread
    public static void write(@NonNull InputStream input, @NonNull File destination) throws IOException
    {
        if(isFile(destination))
        {
            FileOutputStream output = new FileOutputStream(destination);
            input = new BufferedInputStream(input);
            int data;

            try
            {
                while(EOF != (data = input.read(BUFFER)))
                {
                    output.write(BUFFER, 0, data);
                }
            }
            finally
            {
                input.close();
                output.flush();
                output.close();
            }
        }
        else
        {
            throw new IOException("File de destino não é um arquivo.");
        }
    }

    @WorkerThread
    public static File write(@NonNull String name, @NonNull InputStream input, @NonNull File destination) throws IOException
    {
        if(hasDirectory(destination))
        {
            File file = new File(destination, name);
            FileOutputStream output = new FileOutputStream(file);
            input = new BufferedInputStream(input);
            int data;

            try
            {
                while(EOF != (data = input.read(BUFFER)))
                {
                    output.write(BUFFER, 0, data);
                }
            }
            finally
            {
                input.close();
                output.flush();
                output.close();
            }

            return file;
        }
        else
        {
            throw new IOException("File de destino não é um diretório.");
        }
    }

    /**
     * Escreve um InputStream para um arquivo.
     *
     * @param name        "nome para o arquivo".
     * @param input       "InputStream de origem".
     * @param destination "diretório de destino".
     * @param listener    "ouvinte para a operação".
     */
    public static void write(@NonNull String name, @NonNull InputStream input, @NonNull File destination, OnFileWriteListener listener)
    {
        new Write(name, destination, listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, input);
    }

    public static boolean rename(File file, @NonNull String name)
    {
        return ((exists(file) && !Strings.isEmpty(name))) && file.renameTo(new File(file.getParentFile(), name));
    }

    public static boolean replace(File to, File from)
    {
        if(exists(to))
        {
            if(exists(from))
            {
                return to.renameTo(from);
            }
        }

        return false;
    }

    public static boolean delete(@NonNull String path)
    {
        return (!Strings.isEmpty(path) && delete(new File(path)));
    }

    public static boolean delete(File file)
    {
        return exists(file) && file.delete();
    }

    public static boolean exists(File file)
    {
        return (notNull(file) && file.exists());
    }

    /**
     * Copia um arquivo para um diretório ou um arquivo de origem.
     *
     * @param origin      "arquivo a ser copiado".
     * @param destination "local ou arquivo de destino".
     *
     * @return (true ser arquivo copiado).
     */
    public static boolean copy(File origin, File destination)
    {
        if(exists(origin) && (exists(destination) || hasDirectory(destination)))
        {
            try
            {
                if(destination.isDirectory())
                {
                    destination = new File(destination, origin.getName());
                }

                FileInputStream inStream = new FileInputStream(origin);
                FileOutputStream outStream = new FileOutputStream(destination);
                FileChannel inChannel = inStream.getChannel();
                FileChannel outChannel = outStream.getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inStream.close();
                outStream.close();
                return true;
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean isFile(File file)
    {
        return (exists(file) && file.isFile());
    }

    public static boolean notNull(File file)
    {
        return (file != null);
    }

    public static boolean hasDirectory(File file)
    {
        return (exists(file) && file.isDirectory());
    }

    /**
     * Gera nome para o arquivo utilizando timestamp atual.
     *
     * @return (nome para o arquivo).
     */
    public static String toName()
    {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * Gera nome para o arquivo utilizando timestamp atual.
     *
     * @param ext "extensão para o arquivo".
     *
     * @return (nome para o arquivo).
     */
    public static String toName(@Extension String ext)
    {
        return toName().concat(ext);
    }

    private static void error(String method, Throwable e)
    {
        Log.e("Files", method, e);
    }

    public interface OnFileWriteListener
    {
        void onFileWritten(File file);
    }

    private static class Write extends AsyncTask<InputStream, Integer, File>
    {
        private final String name;
        private final File destination;
        private final OnFileWriteListener listener;

        Write(String name, File destination, OnFileWriteListener listener)
        {
            this.name = name;
            this.listener = listener;
            this.destination = destination;
        }

        @Override
        protected final File doInBackground(InputStream... params)
        {
            try
            {
                return write(name, params[0], destination);
            }
            catch(IOException e)
            {
                error("write", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(File file)
        {
            if(listener != null)
            {
                listener.onFileWritten(file);
            }
        }
    }
}
