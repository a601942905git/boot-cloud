package com.boot.cloud.client;

import com.boot.cloud.entity.Person;
import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * com.boot.cloud.client.PersonClient
 *
 * @author lipeng
 * @date 2021/7/16 4:22 PM
 */
@RetrofitClient("spring-cloud-provider-application")
public interface PersonClient {

    @GET("/persons/")
    Call<List<Person>> list();
}
