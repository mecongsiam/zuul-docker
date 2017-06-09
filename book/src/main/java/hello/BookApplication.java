package hello;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BookApplication {

  private final org.slf4j.Logger log = LoggerFactory.getLogger(BookApplication.class);
  @RequestMapping(value = "/available")
  public String available() {
    log.info("SSSSSYYYYPPPEEER");
    log.debug("AAAAAAA");
    return "{\"name\":\"spring\"}";
  }

  @RequestMapping(value = "/checked-out")
  public String checkedOut() {
    return "{\"name\":\"boot\"}";
  }

  public static void main(String[] args) {
    SpringApplication.run(BookApplication.class, args);
  }
}
