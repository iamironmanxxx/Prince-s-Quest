package com.smartgenlabs.princequest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 25-02-2018.
 */

public class NetworkStateReceiver extends BroadcastReceiver  {
    protected List<NetworkStateReceiverListener> listeners;
    protected boolean connected;
    private ConnectivityManager manager;



    public NetworkStateReceiver(Context context) {
        listeners = new ArrayList<NetworkStateReceiverListener>();
        manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        checkStateChanged();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent==null || intent.getExtras()==null) return;;

        if(checkStateChanged())notifyStateToAll();
    }

    private boolean checkStateChanged(){
        boolean prev = connected;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return prev!=connected;
    }

    private void notifyStateToAll(){
        for(NetworkStateReceiverListener listener : listeners)
            notifyState(listener);
    }

    private void notifyState(NetworkStateReceiverListener listener){
        if(listener!=null){
            if(connected) listener.onNetworkAvailable();
            else listener.onNetworkUnavailable();
        }
    }

    public void addListener(NetworkStateReceiverListener l){
        listeners.add(l);
        notifyState(l);
    }

    public void removeListener(NetworkStateReceiverListener l){
        listeners.remove(l);
    }

    public interface NetworkStateReceiverListener{
        public void onNetworkAvailable();
        public void onNetworkUnavailable();
    }
}
