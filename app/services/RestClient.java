package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

@Singleton
public class RestClient {
  @SuppressWarnings("unchecked")
  public <T> List<T> get(String url, Class<T> klass) throws IOException {
    String jsonResponse = Request.Get(url).execute().returnContent().asString();
    return (List<T>) new Gson().fromJson(jsonResponse, TypeToken.getParameterized(List.class, klass));
  }
}
