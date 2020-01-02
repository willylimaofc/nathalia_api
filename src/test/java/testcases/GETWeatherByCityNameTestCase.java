package testcases;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GETWeatherByCityNameTestCase {

    @Test
    public void getWithSuccess() {
        Response response =
                given().get("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=" +
                        "b6907d289e10d714a6e88b30761fae22");

        System.out.println(response.getStatusLine());
        System.out.println(response.getBody().prettyPrint());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getWithSuccessParametrized01() {
        Response response =
                given().baseUri("https://samples.openweathermap.org/data/2.5").
                        basePath("weather").
                        param("q", "London").
                        param("appid", "b6907d289e10d714a6e88b30761fae22").
                        when().
                        get();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getWithSuccessParametrized02() {
        ValidatableResponse response = given().baseUri("https://samples.openweathermap.org/data/2.5").
                basePath("weather").
                param("q", "London").
                param("appid", "b6907d289e10d714a6e88b30761fae22").
                when().
                get().
                then().
                assertThat().statusCode(200);
    }

    @Test
    public void getWithSuccessParametrized03() {
        int id = given().baseUri("https://samples.openweathermap.org/data/2.5").
                basePath("weather").
                param("q", "London").
                param("appid", "b6907d289e10d714a6e88b30761fae22").
                when().
                get().
                then().
                extract().
                path("weather[0].id");

        System.out.println(id);

        Assert.assertEquals(id, 300);
    }

    @Test
    public void moreThanOneAttribute01() {
        ResponseBodyExtractionOptions body =
                given().baseUri("https://samples.openweathermap.org/data/2.5").
                        basePath("weather").
                        param("q", "London").
                        param("appid", "b6907d289e10d714a6e88b30761fae22").
                        when().
                        get().
                        then().
                        extract().
                        body();

        int id = body.path("weather[0].id");
        String description = body.path("weather[0].description");

        Assert.assertEquals(id, 300);
        Assert.assertEquals(description, "light intensity drizzle");
    }

    @Test
    public void moreThanOneAttribute02() {
        Response response =
                given().baseUri("https://samples.openweathermap.org/data/2.5").
                        basePath("weather").
                        param("q", "London").
                        param("appid", "b6907d289e10d714a6e88b30761fae22").
                        when().
                        get();

        int id =
                response.
                        then().
                        contentType(ContentType.JSON).
                        extract().
                        path("weather[0].id");

        String description =
                response.
                        then().
                        contentType(ContentType.JSON).
                        extract().
                        path("weather[0].description");

        Assert.assertEquals(id, 300);
        Assert.assertEquals(description, "light intensity drizzle");
    }
}