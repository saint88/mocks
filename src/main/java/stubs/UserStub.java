package stubs;

import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class UserStub {

  private final String basePath = "/user";

  {
    registerStubHello();
  }

  private void registerStubHello() {
    Map<String, String> map = new HashMap<>();
    map.put("name", "Sasha");
    map.put("age", "10");

    stubFor(get(urlEqualTo(String.format("%s/create", basePath)))
        .willReturn(aResponse()
            .withBody(new JSONObject(map).toJSONString())
            .withStatus(200)));
  }

}
