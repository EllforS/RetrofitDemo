package com.ellfors.dagger2.base;

import com.ellfors.dagger2.http.utils.RxException;

/**
 * View基类
 */
public interface BaseView
{
    void showError(RxException e);
}
