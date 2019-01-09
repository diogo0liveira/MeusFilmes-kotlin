package com.dao.mobile.artifact.common.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.webkit.URLUtil;

import com.dao.mobile.artifact.common.ApplicationController;
import com.dao.mobile.artifact.common.R;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Representa informações de conexão com a internet.
 *
 * @author Diogo Oliveira.
 */
public class NetworkManager
{
    private final static String TAG = "NetworkManager";

    public enum State
    {
        NOT_CONNECTED, MOBILE, WIFI
    }

    /**
     * Recupera de "ConnectivityManager".
     *
     * @return (instancia de ConnectivityManager)
     */
    public static ConnectivityManager getConnectivityManager()
    {
        return (ConnectivityManager)ApplicationController.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * Recupera de "NetworkInfo".
     *
     * @return (instancia de NetworkInfo)
     */
    public static NetworkInfo getNetworkInfo()
    {
        return getConnectivityManager().getActiveNetworkInfo();
    }

    /**
     * Recupera de "State".
     *
     * @return (instancia de ConnectivityManager)
     */
    public static State getState()
    {
        NetworkInfo networkInfo = getNetworkInfo();

        if(networkInfo != null)
        {
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            {
                return State.WIFI;
            }

            if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                return State.MOBILE;
            }
        }

        return State.NOT_CONNECTED;
    }

    /**
     * Verifica se o dispositivo esta conectado.
     *
     * @return (true se conectado)
     */
    public static boolean isConnected()
    {
        NetworkInfo networkInfo = getNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Verifica se existe conexão com a internet, caso não exibe uma mensagem.
     *
     * @param context "context atual".
     *
     * @return (true se conectado)
     */
    public static boolean isConnectedWarning(@NonNull Context context)
    {
        boolean success = isConnected();

        if(!success)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setIcon(R.drawable.vd_cmon_wifi_off_24dp);
            alertDialog.setTitle(R.string.network_cmon_dialog_title);
            alertDialog.setMessage(R.string.network_cmon_dialog_no_connection);
            alertDialog.setPositiveButton(android.R.string.ok, null).show();
        }

        return success;
    }

    /**
     * Verifica se existe conexão com a internet, caso não exibe uma mensagem.
     *
     * @param context "context atual".
     *
     * @return (true se conectado)
     */
    public static boolean isAvailableWarning(@NonNull Context context)
    {
        boolean success = isConnectedWarning(context);

        if(success)
        {
            success = available();

            if(!success)
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setIcon(R.drawable.vd_cmon_wifi_off_24dp);
                alertDialog.setTitle(R.string.network_cmon_dialog_title);
                alertDialog.setMessage(R.string.network_cmon_dialog_error_connection);
                alertDialog.setPositiveButton(android.R.string.ok, null).show();
            }
        }

        return success;
    }

    /**
     * Conexão atual é wifi.
     *
     * @return (true se conectado e Conexão do tipo wifi)
     */
    public static boolean isConnectedWifi()
    {
        return (getState().equals(State.WIFI));
    }

    /**
     * Conexão atual é 3G ou mesma tecnologia.
     *
     * @return (true se conectado conexão do tipo 3G)
     */
    public static boolean isConnectedMobile()
    {
        return ((getState().equals(State.MOBILE)));
    }

    /**
     * Avalia se a conexão com a internet. "Executa um ping no DNS do Google 8.8.8.8".
     *
     * @return (true se verdadeiro).
     */
    public static boolean available()
    {
        Process process = null;

        try
        {
            Runtime runtime = Runtime.getRuntime();
            process = runtime.exec("ping -c 1 8.8.8.8");
            return (process.waitFor() == 0);
        }
        catch(InterruptedException | IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(process != null)
            {
                process.destroy();
            }
        }

        return false;

        //        try
        //        {
        //           return InetAddress.getByName(host).isReachable(1000);
        //        }
        //        catch(IOException e)
        //        {
        //            e.printStackTrace();
        //        }
        //
        //        return false;
    }

    /**
     * Avalia se a comunicação com a url.
     *
     * @param url "url a ser avaliada".
     *
     * @return (true se verdadeiro).
     */
    @WorkerThread
    public static boolean available(String url)
    {
        HttpURLConnection connection = null;

        try
        {
            if(URLUtil.isValidUrl(url))
            {
                URL URL = new URL(url);
                connection = (HttpURLConnection)URL.openConnection();
                connection.setConnectTimeout(500);
                connection.setReadTimeout(500);

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    Log.d(TAG, "URL operando normalmente. (ResponseCode " + connection.getResponseCode() + " " + connection.getResponseMessage() + ")");
                    return true;
                }
            }
            else
            {
                Log.e(TAG, "URL invalida");
            }
        }
        catch(IOException e)
        {
            try
            {
                //noinspection ConstantConditions
                Log.e(TAG, "Erro de conexão com a URL. (ResponseCode " + connection.getResponseCode() + " " + connection.getResponseMessage() + ")", e);
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
        finally
        {
            if(connection != null)
            {
                connection.disconnect();
            }
        }

        return false;
    }

    /**
     * Monitoring pode ouvi o estado atual da conexão wifi ou movel.
     */
    public static class Monitoring
    {
        private static OnNetworkStateStartedTracking onNetworkStateStartedTracking;
        private static OnNetworkStateChange onNetworkStateChange;
        private static Subscription subscription;

        /**
         * Inicia monitoramento da conexão. Certifique se de especificar os listeners.
         *
         * @param context "context atual".
         *
         * @return (uma instancia de Monitoring)
         */
        public static Monitoring start(@NonNull Context context)
        {
            return new Monitoring(context);
        }

        /**
         * Inicia monitoramento da conexão. No caso da activity implementar os respectivos listeners, os mesmo serão chamados.
         *
         * @param activity "activity atual".
         */
        public static void start(@NonNull Activity activity)
        {
            new Monitoring(activity);
        }

        /**
         * Inicia monitoramento da conexão. No caso da activity implementar os respectivos listeners, os mesmo serão chamados.
         *
         * @param fragment "fragment atual".
         */
        public static void start(@NonNull Fragment fragment)
        {
            new Monitoring(fragment);
        }

        public static void stop()
        {
            if(subscription != null)
            {
                subscription.cancel();
            }
        }

        private Monitoring(@NonNull final Context context)
        {
            final IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

            Observable<State> monitoring = NetworkBroadcastObservable.fromBroadcast(context, filter).map(new Function<Intent, State>()
            {
                @Override
                public State apply(Intent intent) throws Exception
                {
                    if(onNetworkStateStartedTracking != null)
                    {
                        onNetworkStateStartedTracking.onStateStartedTracking(getState());
                    }

                    return getState();
                }
            }).startWith(getState()).observeOn(AndroidSchedulers.mainThread());

            subscription = (Subscription)monitoring.subscribe(new Consumer<State>()
            {
                @Override
                public void accept(State state) throws Exception
                {
                    if(onNetworkStateChange != null)
                    {
                        onNetworkStateChange.onStateChange(state);
                    }
                }
            });
        }

        private Monitoring(@NonNull Activity activity)
        {
            if(activity instanceof OnNetworkStateStartedTracking)
            {
                setOnNetworkStateStartedTracking(((OnNetworkStateStartedTracking)activity));
            }

            if(activity instanceof OnNetworkStateChange)
            {
                setOnNetworkStateChange(((OnNetworkStateChange)activity));
            }

            start((Context)activity);
        }

        private Monitoring(@NonNull Fragment fragment)
        {
            if(fragment instanceof OnNetworkStateStartedTracking)
            {
                setOnNetworkStateStartedTracking(((OnNetworkStateStartedTracking)fragment));
            }

            if(fragment instanceof OnNetworkStateChange)
            {
                setOnNetworkStateChange(((OnNetworkStateChange)fragment));
            }

            start(fragment.getContext());
        }

        public void setOnNetworkStateStartedTracking(OnNetworkStateStartedTracking onStateStartedTracking)
        {
            Monitoring.onNetworkStateStartedTracking = onStateStartedTracking;
        }

        public void setOnNetworkStateChange(OnNetworkStateChange onStateChange)
        {
            Monitoring.onNetworkStateChange = onStateChange;
        }
    }
}
