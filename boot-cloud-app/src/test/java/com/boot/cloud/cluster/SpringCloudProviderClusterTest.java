package com.boot.cloud.cluster;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * com.boot.cloud.cluster.SpringCloudProviderClusterTest
 *
 * @author lipeng
 * @dateTime 2018/11/15 下午3:48
 */
public class SpringCloudProviderClusterTest {

    @Test
    public void testProviderCluster() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://localhost:8083/persons/id/10001");

        for (int i = 0; i < 6; i++) {
            HttpResponse response = client.execute(httpGet);

            System.out.println(EntityUtils.toString(response.getEntity()));
            System.out.println("========================================");
        }

    }
}
