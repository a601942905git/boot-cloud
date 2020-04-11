package com.boot.cloud;

import com.alibaba.fastjson.JSONObject;
import com.boot.cloud.constants.RequestUrlConstant;
import com.boot.cloud.proxy.SavePersonRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * com.boot.cloud.PersonHttpCallService
 *
 * @author lipeng
 * @date 2020/4/3 8:16 PM
 */
@Service
public class PersonHttpCallServiceImpl implements PersonHttpCallService {

    public List<Person> listPerson() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(RequestUrlConstant.LIST_PERSON_URL).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return null;
            }
            String responseBody = response.body().string();
            return JSONObject.parseArray(responseBody, Person.class);
        } catch (IOException e) {
            return null;
        }
    }

    public void savePerson(SavePersonRequest savePersonRequest) {
        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .post(RequestBody.create(JSONObject.toJSONString(savePersonRequest), JSON))
                .url(RequestUrlConstant.SAVE_PERSON_URL)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {

            }
        } catch (IOException e) {

        }
    }
}
