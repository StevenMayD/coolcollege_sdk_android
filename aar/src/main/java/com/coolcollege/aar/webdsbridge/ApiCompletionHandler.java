package com.coolcollege.aar.webdsbridge;

/**
 * Created by du on 16/12/31.
 */

public interface ApiCompletionHandler<T> {
    void complete(T retValue);
    void complete();
    void setProgressData(T value);
}
