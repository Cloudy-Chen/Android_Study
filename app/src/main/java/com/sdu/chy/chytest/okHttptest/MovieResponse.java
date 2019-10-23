package com.sdu.chy.chytest.okHttptest;

import java.io.Serializable;

/**
 * Created by danding on 2019/4/8.
 */

public class MovieResponse<T> implements Serializable {

//    使用缓存前，必须让涉及到缓存javaBean对象实现Serializable接口，否者会报NotSerializableException。
//    因为缓存的原理是将对象序列化后直接写入数据库中，如果不实现Serializable接口，会导致对象无法序列化，进而无法写入到数据库中，也就达不到缓存的效果。

    private static final long serialVersionUID = -686453405647539973L;

    public T movieList;
}