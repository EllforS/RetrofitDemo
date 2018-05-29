package com.ellfors.dagger2.http.model;


/**
 * 统一处理回调结果实体类
 */
public class BaseCallModel<T>
{
    private boolean error;
    private T results;
    //↑ 上面那俩个是实际定义的    下面的是基本上都会包含的
    private String msg;
    private int errorCode;

    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getMsg()
    {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
