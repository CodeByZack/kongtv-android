package com.zack.kongtv.NetWorkChange;

public interface NetStateChangeObserver {

    void onNetDisconnected();

    void onNetConnected(NetworkType networkType);
}
