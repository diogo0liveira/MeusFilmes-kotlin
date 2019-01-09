package com.dao.mobile.artifact.common.network;

import com.dao.mobile.artifact.common.network.NetworkManager.State;

/**
 * Created on 18/12/2015 14:34.
 *
 * @author Diogo Oliveira.
 */
public interface OnNetworkStateStartedTracking
{
    void onStateStartedTracking(State state);
}
