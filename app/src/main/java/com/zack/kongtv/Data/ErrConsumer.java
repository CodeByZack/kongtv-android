package com.zack.kongtv.Data;

import com.zackdk.Utils.LogUtil;

import io.reactivex.functions.Consumer;

public class ErrConsumer implements Consumer<Throwable> {
    @Override
    public void accept(Throwable throwable) throws Exception {
        LogUtil.d(throwable.getLocalizedMessage());
    }
}
