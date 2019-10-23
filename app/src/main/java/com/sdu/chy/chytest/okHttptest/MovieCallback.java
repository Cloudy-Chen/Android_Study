package com.sdu.chy.chytest.okHttptest;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.sdu.chy.chytest.Utils.Convert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by danding on 2019/4/8.
 */

public abstract class MovieCallback<T> extends AbsCallback<T> {

    /**
     * 这里的数据解析是根据 http://api.m.mtime.cn/PageSubArea/TrailerList.api 返回的数据来写的
     */

    @Override
    public T convertResponse(Response response) throws Throwable {
        //以下代码是通过泛型解析实际参数,泛型必须传
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");

        JsonReader jsonReader = new JsonReader(response.body().charStream());
        Type rawType = ((ParameterizedType) type).getRawType();
        if (rawType == MovieResponse.class) {
            MovieResponse movieResponse = Convert.fromJson(jsonReader, type);
                response.close();
                //noinspection unchecked
                return (T) movieResponse;
        } else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }
}
