package com.example.oauth2ResourseServer.service;

import java.util.List;

public interface BaseService<T,R> {
    void delete(Integer id);
    R update(Integer id,T t);
    void add(T t);
    R get(Integer id);
    List<R> list();
}
