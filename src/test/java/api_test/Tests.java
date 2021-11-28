package api_test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
    public class Tests extends BaseTest{

        private static String accessToken;
        private static String usersAccessToken;
        private static int userId;
        private static int randomNumber = 1 + (int) (Math.random() * 10000000);


        private static String username = "kinyaev" + randomNumber;
        private static String name = "Jason";
        private static String surname = "Bourne";
        private static String email = "foma"+ randomNumber + "@cia.com";

        @BeforeClass
        public static void start() {

            RestAssured.baseURI = API_URI;

        }


        @Test
        public void test1_getToken() {

            Map<String, Object> body = new HashMap<>();
            body.put("grant_type", "client_credentials");
            body.put("scope", "guest:default");

            String response =
                    given()
                            //.log().all()
                            .auth()
                            .preemptive()
                            .basic(basicAuthUsername, "")
                            .contentType(ContentType.JSON)
                            .body(body)
                            .when().post(AUTH_METHOD)
                            .then()
                            .assertThat()
                            .statusCode(200).and()
                            .body("access_token", notNullValue())
                            .extract().jsonPath().getString("access_token");

            accessToken = "Bearer " + response;

        }


        @Test
        public void test2_createUser() {

            Map<String, Object> body = new HashMap<>();
            body.put("username", username);
            body.put("password_change", encodePassword("newpassword1"));
            body.put("password_repeat", encodePassword("newpassword1"));
            body.put("email", email);
            body.put("name", name);
            body.put("surname", surname);
            body.put("currency", "EUR");

            int response =
                    given()
                            //.log().all()
                            .header("Authorization", accessToken)
                            .contentType(ContentType.JSON)
                            .body(body)
                            .when().post(MANAGE_PLAYERS_METHOD)
                            .then()
                            .assertThat()
                            .body("id", notNullValue()).and()
                            .body("username", equalTo(username)).and()
                            .body("email", equalTo(email)).and()
                            .body("name", equalTo(name)).and()
                            .body("surname", equalTo(surname)).and()
                            .body("country_id", equalTo(null)).and()
                            .body("timezone_id", equalTo(null)).and()
                            .body("gender", equalTo(null)).and()
                            .body("phone_number", equalTo(null)).and()
                            .body("birthdate", equalTo(null)).and()
                            .body("bonuses_allowed", equalTo(true)).and()
                            .body("is_verified", equalTo(false)).and()
                            .statusCode(201)
                            .extract().jsonPath().getInt("id");

            userId = response;

        }


        @Test
        public void test3_loginUser() {

            Map<String, Object> body = new HashMap<>();
            body.put("grant_type", "password");
            body.put("username", username);
            body.put("password", encodePassword("newpassword1"));

            String response =
                    given()
                            //.log().all()
                            .auth()
                            .preemptive()
                            .basic(basicAuthUsername, "")
                            .contentType(ContentType.JSON)
                            .body(body)
                            .when().post(AUTH_METHOD)
                            .then()
                            .assertThat()
                            .statusCode(200).and()
                            .body("access_token", notNullValue()).and()
                            .body("refresh_token", notNullValue())
                            .extract().jsonPath().getString("access_token");

            usersAccessToken = "Bearer " + response;

        }


        @Test
        public void test4_getUserInfo() {

                    given()
                            //.log().all()
                            .header("Authorization", usersAccessToken)
                            .contentType(ContentType.JSON)
                            .when().get(MANAGE_PLAYERS_METHOD + "/" + userId)
                            .then()
                            .assertThat()
                            .body("id", equalTo(userId)).and()
                            .body("username", equalTo(username)).and()
                            .body("email", equalTo(email)).and()
                            .body("name", equalTo(name)).and()
                            .body("surname", equalTo(surname)).and()
                            .body("country_id", equalTo(null)).and()
                            .body("timezone_id", equalTo(null)).and()
                            .body("gender", equalTo(null)).and()
                            .body("phone_number", equalTo(null)).and()
                            .body("birthdate", equalTo(null)).and()
                            .body("bonuses_allowed", equalTo(true)).and()
                            .body("is_verified", equalTo(false)).and()
                            .statusCode(200);

        }


        @Test
        public void test5_getNonExistentUserInfo() {

                    given()
                            //.log().all()
                            .header("Authorization", usersAccessToken)
                            .contentType(ContentType.JSON)
                            .when().get(MANAGE_PLAYERS_METHOD + "/" + (randomNumber))
                            .then()
                            .assertThat()
                            .statusCode(404);

        }


        @AfterClass
        public static void finish() {

        }

 }

