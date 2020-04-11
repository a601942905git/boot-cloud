package com.boot.cloud.proxy;

import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * com.boot.cloud.proxy.HttpCallProxy
 *
 * @author lipeng
 * @date 2020/4/4 10:12 AM
 */
public class HttpCallProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args.length > 2) {
            throw new IllegalArgumentException("method param length more than 2");
        }

        // 指定请求体数据类型
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        // 如果只有一个url参数，则使用get请求；如果存在2个参数，则使用post请求
        Request request = args.length == 1 ?
                new Request.Builder()
                        .url(String.valueOf(args[0]))
                        .build() :
                new Request.Builder()
                        .post(RequestBody.create(JSONObject.toJSONString(args[1]), JSON))
                        .url(String.valueOf(args[0]))
                        .build();
        // 发送http请求
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return null;
            }

            ResponseBody responseBody = response.body();
            if (Objects.isNull(responseBody)) {
                return null;
            }

            String responseContent = responseBody.string();
            // 解析返回结果
            return JSONObject.parseObject(responseContent, method.getReturnType());
        } catch (IOException e) {
            return null;
        }
    }
}
