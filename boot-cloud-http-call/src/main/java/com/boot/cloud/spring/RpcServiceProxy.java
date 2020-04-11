package com.boot.cloud.spring;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * com.boot.cloud.spring.RpcServiceProxy
 *
 * @author lipeng
 * @date 2020/4/4 10:30 AM
 */
@Slf4j
public class RpcServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        GetMapping getMappingAnnotation = AnnotationUtils.findAnnotation(method, GetMapping.class);
        PostMapping postMappingAnnotation = AnnotationUtils.findAnnotation(method, PostMapping.class);

        // 指定请求体数据类型
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        // 如果存在GetMapping注解，那么就是get请求，否则当作post请求处理
        Request request = Objects.nonNull(getMappingAnnotation) ?
                new Request.Builder()
                        .url(getMappingAnnotation.value()[0])
                        .build() :
                new Request.Builder()
                        .post(RequestBody.create(JSONObject.toJSONString(args[0]), JSON))
                        .url(postMappingAnnotation.value()[0])
                        .build();
        // 发送http请求
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("请求接口响应异常");
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
