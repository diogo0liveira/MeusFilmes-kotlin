package com.dao.mobile.artifact.common.network;

import com.dao.mobile.artifact.common.network.NetworkManager.State;

/**
 * Created on 18/12/2015 14:26.
 *
 * @author Diogo Oliveira.
 */
public interface OnNetworkStateChange
{
    void onStateChange(State state);
}
