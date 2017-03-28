package hello.filters.pre;

import javax.servlet.http.HttpServletRequest;

import com.google.common.io.CharStreams;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleFilter extends ZuulFilter {

  private static Logger log = LoggerFactory.getLogger(SimpleFilter.class);

  @Override
  public String filterType() {
    return "post";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    RestTemplate restTemplate = new RestTemplate();
    JSONParser jsonParser = new JSONParser();
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
            "http://localhost:8070/get-name", String.class
    );
    String body = responseEntity.getBody();

    System.out.println(ctx.getResponseDataStream());
    try {

      final String responseData = CharStreams.toString(new InputStreamReader(ctx.getResponseDataStream(), "UTF-8"));

      Object objFromBook = jsonParser.parse(responseData);
      JSONObject jsonFromBook=(JSONObject) objFromBook;

      Object objFromAuther= jsonParser.parse(body);
      JSONObject jsonFromAuther=(JSONObject) objFromAuther;

      jsonFromAuther.putAll(jsonFromBook);


      System.out.println(jsonFromAuther);

      ctx.setResponseBody(jsonFromAuther.toJSONString());

    } catch (IOException e) {
      e.printStackTrace();

    } catch (ParseException e) {
      e.printStackTrace();
    }

    log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

    return null;

  }

}
