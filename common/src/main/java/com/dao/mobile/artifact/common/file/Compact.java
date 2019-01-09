package com.dao.mobile.artifact.common.file;

import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import android.util.Log;

import com.dao.mobile.artifact.common.AppEnvironment;
import com.dao.mobile.artifact.common.exceptions.DirectoryNotFoundException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.dao.mobile.artifact.common.file.Files.BUFFER;
import static com.dao.mobile.artifact.common.file.Files.BUFFER_SIZE;
import static com.dao.mobile.artifact.common.file.Files.EOF;

/**
 * Compacta arquivos.
 *
 * @author Diogo Oliveira.
 */
public class Compact
{
    /**
     * Compacta uma lista de arquivos para um arquivo ".zip" de forma sincrona no diretório de cache da aplicação.
     *
     * @param files "Lista de de arquivos que serão compactados".
     * @return (arquivo .zip gerado)
     */
    @WorkerThread
    public static File zip(HashSet<File> files) throws DirectoryNotFoundException, ZipException
    {
        return zip(Files.toName(Files.ZIP), files);
    }

    /**
     * Compacta uma lista de arquivos para um arquivo ".zip" de forma sincrona no diretório de cache da aplicação.
     *
     * @param name  "nome para o arquivo .zip".
     * @param files "Lista de de arquivos que serão compactados".
     * @return (arquivo .zip gerado)
     */
    @WorkerThread
    public static File zip(@NonNull String name, HashSet<File> files) throws DirectoryNotFoundException, ZipException
    {
        return zip(name, files, AppEnvironment.directoryCache());
    }

    /**
     * Compacta uma lista de arquivos para um arquivo ".zip" de forma sincrona.
     *
     * @param name  "nome para o arquivo .zip".
     * @param files "Lista de de arquivos que serão compactados".
     * @return (arquivo .zip gerado)
     */
    @WorkerThread
    public static File zip(@NonNull String name, HashSet<File> files, File destination) throws ZipException
    {
        if(notNull(files))
        {
            File zip = new File(destination, name.concat(Files.ZIP));
            zip.deleteOnExit();

            try(ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zip))))
            {
                output.setLevel(Deflater.BEST_COMPRESSION);

                for(File file : files)
                {
                    if((file.exists() && !(file.isDirectory() && file.canRead())))
                    {
                        try(BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE))
                        {
                            ZipEntry entry = new ZipEntry(file.getName());
                            output.putNextEntry(entry);
                            int data;

                            while(EOF != (data = input.read(BUFFER, 0, BUFFER_SIZE)))
                            {
                                output.write(BUFFER, 0, data);
                            }
                        }
                    }
                }
            }
            catch(IOException e)
            {
                throw new ZipException(e.getMessage());
            }

            return zip;
        }

        return null;
    }

    /**
     * Compacta uma lista de arquivos para um arquivo ".zip" de forma assincrona.
     *
     * @param name     "nome para o arquivo .zip".
     * @param files    "Lista de caminhos dos arquivos que serão compactados".
     * @param listener "Ouvinte para o resultado da operação".
     */
    public static void zip(@NonNull String name, HashSet<File> files, OnCompactZipListener listener)
    {
        //noinspection unchecked
        new Compressing(name, listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, files);
    }

    /**
     * Descompacta um arquivo ".zip" de forma sincrona no diretório de cache da aplicação.
     *
     * @param zip "arquivo a ser descompactado".
     * @return (true se arquivos foram descompactados)
     */
    @WorkerThread
    public static boolean unzip(File zip) throws DirectoryNotFoundException, ZipException
    {
        return unzip(zip, AppEnvironment.directoryCache());
    }

    /**
     * Descompacta um arquivo ".zip" de forma sincrona.
     *
     * @param zip       "arquivo a ser descompactado".
     * @param directory "diretório de destino para os arquivos descompactados".
     * @return (true se arquivos foram descompactados)
     */
    @WorkerThread
    public static boolean unzip(File zip, File directory) throws ZipException
    {
        if(Files.isFile(zip))
        {
            if(Files.hasDirectory(directory))
            {
                try(ZipInputStream input = new ZipInputStream(new BufferedInputStream(new FileInputStream(zip), BUFFER_SIZE)))
                {
                    ZipEntry entry;

                    while((entry = input.getNextEntry()) != null)
                    {
                        File file = new File(directory, entry.getName());

                        if(entry.isDirectory())
                        {
                            if(!file.isDirectory())
                            {
                                //noinspection ResultOfMethodCallIgnored
                                file.mkdirs();
                            }
                        }
                        else
                        {
                            File parentDir = file.getParentFile();

                            if(null != parentDir)
                            {
                                if(!parentDir.isDirectory())
                                {
                                    //noinspection ResultOfMethodCallIgnored
                                    parentDir.mkdirs();
                                }
                            }

                            try(BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file, false), BUFFER_SIZE))
                            {
                                int data;

                                while(EOF != (data = input.read(BUFFER, 0, BUFFER_SIZE)))
                                {
                                    output.write(BUFFER, 0, data);
                                }

                                input.closeEntry();
                            }
                        }
                    }

                    return true;
                }
                catch(IOException e)
                {
                    error("unzip", e);
                }
            }
            else
            {
                throw new ZipException("Destino não é um diretório.");
            }
        }

        return false;
    }

    /**
     * Descompacta um arquivo ".zip" de forma assincrona.
     *
     * @param file      "arquivo a ser descompactado".
     * @param directory "diretório de destino para os arquivos descompactados".
     * @param listener  "Ouvinte para o resultado da operação".
     */
    public static void unzip(File file, File directory, OnCompactUnzipListener listener)
    {
        //noinspection unchecked
        new Decompressed(directory, listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, file);
    }

    private static void error(String method, Throwable e)
    {
        /* Logger */
        Log.e("Compact", method, e);
    }

    private static boolean notNull(HashSet hashSet)
    {
        return ((hashSet != null) && !hashSet.isEmpty());
    }

    public interface OnCompactZipListener
    {
        void onZipped(File file);

        void onZippedProgress(Compressing compressing, int percentage);
    }

    public interface OnCompactUnzipListener
    {
        void onUnzipped(boolean success);

        void onUnzippedProgress(Decompressed decompressed, int percentage);
    }

    public static class Compressing extends AsyncTask<HashSet<File>, Integer, File>
    {
        private final String name;
        private final OnCompactZipListener listener;

        Compressing(String name, OnCompactZipListener listener)
        {
            this.name = name;
            this.listener = listener;
        }

        @Override
        @SafeVarargs
        protected final File doInBackground(HashSet<File>... params)
        {
            try
            {
                HashSet<File> files = params[0];

                if(notNull(files))
                {
                    File zip = new File(AppEnvironment.directoryCache(), name.concat(Files.ZIP));
                    zip.deleteOnExit();

                    int amount = files.size();
                    int accumulated = 0;
                    int data;

                    publishProgress(0);

                    for(File file : files)
                    {
                        if(isCancelled())
                        {
                            return null;
                        }
                        else
                        {
                            if((file.exists() && !(file.isDirectory() && file.canRead())))
                            {
                                try(ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zip))))
                                {
                                    output.setLevel(Deflater.BEST_COMPRESSION);

                                    try(BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE))
                                    {
                                        ZipEntry entry = new ZipEntry(file.getName());
                                        output.putNextEntry(entry);

                                        while(EOF != (data = input.read(BUFFER, 0, BUFFER_SIZE)))
                                        {
                                            if(isCancelled())
                                            {
                                                input.close();
                                                output.flush();
                                                output.close();
                                                return null;
                                            }
                                            else
                                            {
                                                output.write(BUFFER, 0, data);
                                            }
                                        }

                                        publishProgress(((int)(100 * ++accumulated / amount)));
                                    }
                                }
                                catch(IOException e)
                                {
                                    error("zip", e);
                                }
                            }
                        }
                    }

                    return zip;
                }
            }
            catch(DirectoryNotFoundException e)
            {
                error("zip", e);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            if(listener != null)
            {
                listener.onZippedProgress(this, values[0]);
            }
        }

        @Override
        protected void onPostExecute(File zip)
        {
            if(listener != null)
            {
                listener.onZipped(zip);
            }
        }
    }

    public static class Decompressed extends AsyncTask<File, Integer, Boolean>
    {
        private final File directory;
        private final OnCompactUnzipListener listener;

        Decompressed(File directory, OnCompactUnzipListener listener)
        {
            this.directory = directory;
            this.listener = listener;
        }

        @Override
        @SuppressWarnings("ResultOfMethodCallIgnored")
        protected Boolean doInBackground(File... params)
        {
            try
            {
                File zip = params[0];

                if(Files.isFile(zip))
                {
                    if(Files.hasDirectory(directory))
                    {
                        try(ZipInputStream input = new ZipInputStream(new BufferedInputStream(new FileInputStream(zip), BUFFER_SIZE)))
                        {
                            int amount = Collections.list(new ZipFile(zip).entries()).size();
                            int accumulated = 0;
                            ZipEntry entry;

                            publishProgress(0);

                            while((entry = input.getNextEntry()) != null)
                            {
                                if(isCancelled())
                                {
                                    input.close();
                                    return false;
                                }
                                else
                                {
                                    File file = new File(directory, entry.getName());

                                    if(entry.isDirectory())
                                    {
                                        if(!file.isDirectory())
                                        {
                                            //noinspection ResultOfMethodCallIgnored
                                            file.mkdirs();
                                        }
                                    }
                                    else
                                    {
                                        File parentDir = file.getParentFile();

                                        if(null != parentDir)
                                        {
                                            if(!parentDir.isDirectory())
                                            {
                                                //noinspection ResultOfMethodCallIgnored
                                                parentDir.mkdirs();
                                            }
                                        }

                                        try(BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file, false), BUFFER_SIZE))
                                        {
                                            int data;

                                            while(EOF != (data = input.read(BUFFER, 0, BUFFER_SIZE)))
                                            {
                                                if(isCancelled())
                                                {
                                                    input.close();
                                                    return false;
                                                }
                                                else
                                                {
                                                    output.write(BUFFER, 0, data);
                                                }
                                            }

                                            publishProgress(((int)(100 * ++accumulated / amount)));
                                            input.closeEntry();
                                        }
                                    }
                                }
                            }

                            return true;
                        }
                        catch(IOException e)
                        {
                            error("unzip", e);
                        }
                    }
                    else
                    {
                        throw new ZipException("Destino não é um diretório.");
                    }
                }

                return false;
            }
            catch(IOException e)
            {
                error("unzip", e);
            }

            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            if(listener != null)
            {
                listener.onUnzippedProgress(this, values[0]);
            }
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            if(listener != null)
            {
                listener.onUnzipped(success);
            }
        }
    }
}
