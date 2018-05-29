package com.ellfors.dagger2.http.utils;

/**
 * 统一错误类
 * 2018/4/18 18:22
 */
public class RxException extends Exception
{
    private String url;
    private int errorCode;

    public RxException(String message, int errorCode)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public RxException(String message, int errorCode, String url)
    {
        super(message);
        this.url = url;
        this.errorCode = errorCode;
    }

    public RxException(RxException e, String url)
    {
        super(e.getMessage());
        this.errorCode = e.getErrorCode();
        this.url = url;
    }

    public String getUrl()
    {
        return url == null ? "" : url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }
}
