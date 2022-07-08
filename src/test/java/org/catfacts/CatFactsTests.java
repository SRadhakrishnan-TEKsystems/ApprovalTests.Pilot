package org.catfacts;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.CustomApproval;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CatFactsTests extends CustomApproval {
    @Test
    public void smoke() {
        ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        String url = "https://cat-fact.herokuapp.com/facts/";
        ResteasyWebTarget target = client.target(url);
        Response response = target.request("application/json").get();
        String existing = response.readEntity(String.class);
        Gson gson = new Gson();
        List<Object> existing1 = gson.fromJson(existing, new TypeToken<List<Object>>() {}.getType());

        System.out.println("===============");
        System.out.println(existing1);
        System.out.println("===============");
        List<Object> actual = new ArrayList<>();
        approve(existing1, actual);
    }
}
