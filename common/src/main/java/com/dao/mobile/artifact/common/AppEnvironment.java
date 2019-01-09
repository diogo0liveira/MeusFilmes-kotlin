package com.dao.mobile.artifact.common;

import android.os.Environment;

import com.dao.mobile.artifact.common.exceptions.DirectoryNotFoundException;
import com.dao.mobile.artifact.common.file.Files;

import java.io.File;

/**
 * Auxiliar para informações de diretórios.
 * <p/>
 * <dl>
 * <b>OBS: Esta classe depende da "Controller", classe que sobre escreve a classe "Application".</b>
 * </dl>
 *
 * @author Diogo Oliveira.
 */
public class AppEnvironment
{
    /**
     * Diretório de documentos interno da aplicação.
     *
     * @return (diretório de documentos)
     */
    public static File directoryDocuments() throws DirectoryNotFoundException
    {
        File directory = ApplicationController.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        return result(directory);
    }

    /**
     * Diretório de imagem interno da aplicação.
     *
     * @return (diretório de imagens)
     */
    public static File directoryPictures() throws DirectoryNotFoundException
    {
        File directory = ApplicationController.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return result(directory);
    }

    /**
     * Diretório de videos interno da aplicação.
     *
     * @return (diretório de videos)
     */
    public static File directoryMovies() throws DirectoryNotFoundException
    {
        File directory = ApplicationController.getInstance().getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        return result(directory);
    }

    /**
     * Diretório de musica interno da aplicação.
     *
     * @return (diretório de musica)
     */
    public static File directoryMusic() throws DirectoryNotFoundException
    {
        File directory = ApplicationController.getInstance().getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        return result(directory);
    }

    /**
     * Diretório de downloads interno da aplicação.
     *
     * @return (diretório de downloads)
     */
    public static File directoryDownloads() throws DirectoryNotFoundException
    {
        File directory = ApplicationController.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        return result(directory);
    }

    /**
     * Diretório de cache interno da aplicação.
     *
     * @return (diretório de cache)
     */
    public static File directoryCache() throws DirectoryNotFoundException
    {
        File directory = ApplicationController.getInstance().getExternalCacheDir();
        return result(directory);
    }

    /**
     * Diretório externo de acordo com o tipo.
     *
     * @param type tipo do diretório.
     *             <p/>
     *             Environment#DIRECTORY_MUSIC,
     *             Environment#DIRECTORY_PODCASTS,
     *             Environment#DIRECTORY_RINGTONES,
     *             Environment#DIRECTORY_ALARMS,
     *             Environment#DIRECTORY_NOTIFICATIONS,
     *             Environment#DIRECTORY_PICTURES,
     *             Environment#DIRECTORY_MOVIES
     *
     * @return (diretório correspondente)
     */
    public static File directoryExternal(String type)
    {
        if(Strings.isEmpty(type))
        {
            return null;
        }
        else
        {
            File directory = Environment.getExternalStoragePublicDirectory(type);

            if(!Files.exists(directory))
            {
                //noinspection ResultOfMethodCallIgnored
                directory.mkdirs();
            }

            return directory;
        }
    }

    /**
     * Caminho "path" para o diretório externo de imagens.
     *
     * @return (caminho para o diretório)
     */
    public static String pathPictures() throws DirectoryNotFoundException
    {
        return path(directoryPictures());
    }

    /**
     * Caminho "path" para o diretório externo de cache.
     *
     * @return (caminho para o diretório)
     */
    public static String pathCache() throws DirectoryNotFoundException
    {
        return path(directoryCache());
    }

    /**
     * Caminho "path" para o arquivo dentro do diretório externo de imagens.
     *
     * @param name "nome do arquivo"
     *
     * @return (caminho para o arquivo)
     */
    public static String absolutePathPicture(String name) throws DirectoryNotFoundException
    {
        if(Strings.isEmpty(name))
        {
            return null;
        }
        else
        {
            return path(directoryPictures(), name);
        }
    }

    /**
     * Caminho "path" para o arquivo dentro do diretório externo de cache.
     *
     * @param name "nome do arquivo"
     *
     * @return (caminho para o arquivo)
     */
    public static String absoluteCachePath(String name) throws DirectoryNotFoundException
    {
        if(Strings.isEmpty(name))
        {
            return null;
        }
        else
        {
            return path(directoryCache(), name);
        }
    }

    private static File result(File directory) throws DirectoryNotFoundException
    {
        if(Files.exists(directory) || directory.mkdirs())
        {
            return directory;
        }
        else
        {
            throw new DirectoryNotFoundException();
        }
    }

    private static String path(File directory)
    {
        /* caminho diretório */
        return (Files.exists(directory) ? directory.getPath() : null);
    }

    private static String path(File directory, String file)
    {
        /* caminho arquivo */
        return (Files.exists(directory) ? directory.getPath().concat(File.separator).concat(file) : null);
    }
}
