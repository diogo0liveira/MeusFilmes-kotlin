package com.dao.mobile.artifact.common.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import io.reactivex.Observable;


/**
 * Created on 18/12/2015 12:30.
 *
 * @author Diogo Oliveira.
 */
final class NetworkBroadcastObservable
{
    public static Observable<Intent> fromBroadcast(Context context, IntentFilter filter)
    {
        return Observable.create(new NetworkChangeBroadcast(context, filter));
    }
}
