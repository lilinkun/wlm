package com.wlm.wlm.entity;

/**
 * Created by LG on 2018/11/27.
 */

public class TbjsonBean<T> {
    private T ResultList;

    public T getResultList() {
        return ResultList;
    }

    public void setResultList(T resultList) {
        ResultList = resultList;
    }
}
