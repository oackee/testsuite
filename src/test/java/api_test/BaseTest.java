package api_test;

import javax.xml.bind.DatatypeConverter;

public class BaseTest {

    // API access data.
    protected final static String API_URI = "http://test-api.d6.dev.devcaz.com";
    protected final static String AUTH_METHOD = "/v2/oauth2/token";
    protected final static String MANAGE_PLAYERS_METHOD = "/v2/players";
    protected final static String BASIC_AUTH_USERNAME = "front_2d6b0a8391742f5d789d7d915755e09e";

    // Passed variables. Needed to transfer data between tests.
    protected static String accessToken;
    protected static String usersAccessToken;
    protected static int userId;

    // Generating user credentials.
    protected static int randomNumber = 1 + (int) (Math.random() * 10000000);
    protected static String username = "kinyaev" + randomNumber;
    protected static String name = "Jason";
    protected static String surname = "Bourne";
    protected static String email = "foma"+ randomNumber + "@cia.com";

    // Helper methods.
    protected String encodePassword(String passPhrase) {
        String str = passPhrase;
        String encodedPassword = DatatypeConverter.printBase64Binary(str.getBytes());
        return encodedPassword;
    }

}
