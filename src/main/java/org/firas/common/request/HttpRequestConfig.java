package org.firas.common.request;

/**
 * Created by lc114 on 2017/2/4.
 */
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.core.task.AsyncListenableTaskExecutor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;

public class HttpRequestConfig {

    public static  SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();

    public AsyncClientHttpRequest createAsyncRequest (URI uri, HttpMethod httpMethod) {
        try {
            return simpleClientHttpRequestFactory.createAsyncRequest(uri, httpMethod);
        } catch(IOException e) {

        }
        return null;
    }

//    public ClientHttpRequest createHttpRequest(URI uri, HttpMethod httpMethod) {
//        return simpleClientHttpRequestFactory.createHttpRequest(uri, httpMethod);
//    }

//    protected HttpURLConnection openConnection (URL url, Proxy proxy) {
//        return simpleClientHttpRequestFactory.openConnection(url, proxy);
//    }

//    protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
//        simpleClientHttpRequestFactory.prepareConnection(connection, httpMethod);
//    }

    public void setBufferRequestBody (boolean bufferRequestBody) {
        simpleClientHttpRequestFactory.setBufferRequestBody(bufferRequestBody);
    }

    //Sets the number of bytes to write in each chunk when not buffering request bodies locally.
    public void setChunkSize(int chunkSize) {
        simpleClientHttpRequestFactory.setChunkSize(chunkSize);
    }

    public void setConnectTimeout(int connectTimeout){
        simpleClientHttpRequestFactory.setConnectTimeout(connectTimeout);
    }

    //Set if the underlying URLConnection can be set to 'output streaming' mode.
    public void setOutputStreaming(boolean outputStreaming) {
        simpleClientHttpRequestFactory.setOutputStreaming(outputStreaming);
    }

    public void setProxy(Proxy proxy) {
        simpleClientHttpRequestFactory.setProxy(proxy);
    }

    //Set the underlying URLConnection's read timeout (in milliseconds)
//    public void setReadTimeOut(int readTimeOut) {
//        simpleClientHttpRequestFactory.setReadTimeOut(readTimeOut);
//    }

    //Set the task executor for this request factory.
    public void setTaskExecutor(AsyncListenableTaskExecutor taskExecutor) {
        simpleClientHttpRequestFactory.setTaskExecutor(taskExecutor);
    }





}
