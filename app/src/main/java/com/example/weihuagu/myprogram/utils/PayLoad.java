package com.example.weihuagu.myprogram.utils;

import android.util.Log;

import io.reactivex.functions.Function;

public class PayLoad<T> implements Function<BaseResponse<T>> {

    @Override
    public T call(BaseResponse<T> tBaseResponse) {//获取数据失败时，包装一个Fault 抛给上层处理错误
        if (!tBaseResponse.isSuccess()) {

            try {

            } catch (Exception e) {
                Log.e("ASdafas", tBaseResponse.status + tBaseResponse.message);
            }


//            throw new Fault(tBaseResponse.status,tBaseResponse.message);
        }
        return tBaseResponse.data;
    }

    @Override
    public Object apply(Object o) throws Exception {


    }
}
