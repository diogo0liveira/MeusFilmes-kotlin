package com.dao.mobile.artifact.common.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;


/**
 * Created on 18/12/2015 12:17.
 *
 * @author Diogo Oliveira.
 */
class NetworkChangeBroadcast implements ObservableOnSubscribe<Intent>
{
    private final Context context;
    private final IntentFilter intentFilter;

    public NetworkChangeBroadcast(Context context, IntentFilter intentFilter)
    {
        this.context = context;
        this.intentFilter = intentFilter;
    }

    @Override
    public void subscribe(final ObservableEmitter<Intent> emitter) throws Exception
    {
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                emitter.onNext(intent);
            }
        };

        emitter.setDisposable(new Disposable()
        {
            @Override
            public void dispose()
            {
                context.unregisterReceiver(broadcastReceiver);
            }

            @Override
            public boolean isDisposed()
            {
                return false;
            }
        });

        context.registerReceiver(broadcastReceiver, intentFilter, null, null);
    }


    //    @Override
//    public void call(final Subscriber<? super Intent> subscriber)
//    {
//        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
//        {
//            @Override
//            public void onReceive(Context context, Intent intent)
//            {
//                subscriber.onNext(intent);
//            }
//        };
//
//        final Subscription subscription = Subscriptions.create(new Action0()
//        {
//            @Override
//            public void call()
//            {
//                context.unregisterReceiver(broadcastReceiver);
//            }
//        });
//
//        subscriber.add(subscription);
//        context.registerReceiver(broadcastReceiver, intentFilter, null, null);
//    }
}
