package hello;

import com.netflix.zuul.context.RequestContext;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = GatewayApplication.class)
public class GatewayApplicationTest {

    @Autowired
    private TestRestTemplate rest;


    static ConfigurableApplicationContext bookService;

    static ConfigurableApplicationContext authorService;

    @ClassRule
    public static WireMockClassRule bookMock = new WireMockClassRule(WireMockSpring.options().port(8080));



    @ClassRule
    public static WireMockClassRule authorMock = new WireMockClassRule(WireMockSpring.options().port(8070));

    /*@BeforeClass
    public static void startBookService() {
        bookService = SpringApplication.run(BookService.class,
                "--server.port=8080");
    }

    @BeforeClass
    public static void startAuthorService() {
        authorService = SpringApplication.run(AuthorService.class,
                "--server.port=8070");
    }*/

    /*@AfterClass
    public static void closeBookService() {
        bookService.close();
    }*/

    @Before
    public void setup() {
        RequestContext.testSetCurrentContext(new RequestContext());
    }

    @Test
    public void test() {
        String resp = rest.getForObject("/books/available", String.class);
        //assertThat(resp).isEqualTo("books");
    }

    @Test
    public void contextLoads() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        bookMock.stubFor(get(urlEqualTo("/available"))
                .willReturn(ResponseDefinitionBuilder.responseDefinition().withBody("dd")));
        authorMock.stubFor(get(urlEqualTo("/get-name"))
                .willReturn(aResponse().withBody("{\"author\":\"james\"}")));
        System.out.println(restTemplate.getForObject("http://localhost:8080/books/available", String.class));
        System.out.println("ff");
        //assertThat(restTemplate.getForEntity("http://localhost:8080/books" + "/available", String.class).getBody()).isEqualTo("Hello World!");
    }

    @Configuration
    @EnableAutoConfiguration
    @RestController
    static class BookService {
        @RequestMapping("/available")
        public String getAvailable() {
            return "{\"name\":\"spring\"}";
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @RestController
    static class AuthorService {
        @RequestMapping("/get-name")
        public String getAvailable() {
            return "{\"author\":\"james\"}";
        }
    }
}
