package services;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.*;
import stubs.StoreStub;
import stubs.UserStub;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class SpringService_Test {

  {
    new UserStub();
    new StoreStub();
  }

  @BeforeClass
  public static void startWireMock() {
    configureFor(8080);
  }

  @Test
  public void hello_path_via_stub_wiremock() throws IOException {

    CloseableHttpClient httpClient = HttpClients.createDefault();

    HttpGet request = new HttpGet("http://localhost:8080/user/create");
    HttpResponse httpResponse = httpClient.execute(request);
    String responseString = convertResponseToString(httpResponse);

    verify(getRequestedFor(urlEqualTo("/user/create")));
    assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    assertThat(responseString).isEqualTo("{\"name\":\"Sasha\",\"age\":\"10\"}");
  }

  @Test
  public void hello_path_via_real() throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    HttpGet request = new HttpGet(String.format("%s/hello", System.getProperty("real.service.hostname")));
    HttpResponse httpResponse = httpClient.execute(request);
    String responseString = convertResponseToString(httpResponse);

    assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    assertThat(responseString).isEqualTo("Hello User");
  }

  private String convertResponseToString(HttpResponse response) throws IOException {
    InputStream responseStream = response.getEntity().getContent();
    Scanner scanner = new Scanner(responseStream, "UTF-8");
    String responseString = scanner.useDelimiter("\\Z").next();
    scanner.close();
    return responseString;
  }

}
