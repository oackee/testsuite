package api_test;

import io.restassured.RestAssured;
import org.junit.*;
import javax.xml.bind.DatatypeConverter;


public class BaseTest {

    protected final static String API_URI = "http://test-api.d6.dev.devcaz.com";
    protected final static String AUTH_METHOD = "/v2/oauth2/token";
    protected final static String MANAGE_PLAYERS_METHOD = "/v2/players";
    protected final static String basicAuthUsername = "front_2d6b0a8391742f5d789d7d915755e09e";


    protected String encodePassword(String passPhrase) {
        String str = passPhrase;
        String encodedPassword = DatatypeConverter.printBase64Binary(str.getBytes());
        return encodedPassword;
    }

}
