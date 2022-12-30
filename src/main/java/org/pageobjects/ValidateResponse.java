package org.pageobjects;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ValidateResponse extends ApplicationConstants{

    //final Matcher<Integer> SUCCESS = isOneOf(200, 201);

    ApplicationConstants fields = new ApplicationConstants();
    @Test
    public void CPFConsultRestrict() {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHrestr+"97093236014");

        RequestSpecification reqSpec = reqBuilder.build();
        Response response =
                given(reqSpec).
                        log().all()
                        .log().uri()
                        .when()
                        .get()
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().status()
                        .log().body()
                        .extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();

    }
    @Test
    public void CPFALLSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);

        RequestSpecification reqSpec = reqBuilder.build();
        Response response =
                given(reqSpec).
                        log().all()
                        .log().uri()
                        .when()
                        .get()
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().status()
                        .log().body()
                        .extract().response();

    }
    @Test
    public void CreateNewValidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        //Map newBodyData = fields.newSimulationData();

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody("{\n" +
                "  \"nome\": \"Danielly\",\n" +
                "  \"cpf\": \"12345678916\",\n" +
                "  \"email\": \"danielly@danielly.com\",\n" +
                "  \"valor\": 1200,\n" +
                "  \"parcelas\": 2,\n" +
                "  \"seguro\": true\n" +
                "}");
        //reqBuilder.setBody(newBodyData);

        RequestSpecification reqSpec = reqBuilder.build();
        Response response =
                given(reqSpec)
                        .relaxedHTTPSValidation()
                        .log().all()
                        .log().uri()
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(201)
                        .log().status()
                        .log().body()
                        .extract().response();
    }

    @Test
    public void CreateNewInvalidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody("{\n" +
                "  \"nome\": \"Danielly\",\n" +
                "  \"cpf\": \"12345678917\",\n" +
                "  \"email\": \"danielly@danielly.com\",\n" +
                "  \"valor\": 1200,\n" +
                "  \"parcelas\": 1,\n" +
                "  \"seguro\": true\n" +
                "}");

        RequestSpecification reqSpec = reqBuilder.build();
        Response response =
                given(reqSpec)
                        .relaxedHTTPSValidation()
                        .log().all()
                        .log().uri()
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(400)
                        .log().status()
                        .log().body()
                        .extract().response();

    }
    @Test
    public void CreateNewDuplicatedSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody("{\n" +
                "  \"nome\": \"Danielly\",\n" +
                "  \"cpf\": \"12345678916\",\n" +
                "  \"email\": \"danielly@danielly.com\",\n" +
                "  \"valor\": 1200,\n" +
                "  \"parcelas\": 2,\n" +
                "  \"seguro\": true\n" +
                "}");

        RequestSpecification reqSpec = reqBuilder.build();
        Response response =
                given(reqSpec)
                        .relaxedHTTPSValidation()
                        .log().all()
                        .log().uri()
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(409)
                        .log().status()
                        .log().body()
                        .extract().response();

    }
    @Test
    public void UpdateSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody("{\n" +
                "  \"nome\": \"Danielly\",\n" +
                "  \"cpf\": \"12345678916\",\n" +
                "  \"email\": \"cardoso@danielly.com\",\n" +
                "  \"valor\": 1200,\n" +
                "  \"parcelas\": 2,\n" +
                "  \"seguro\": true\n" +
                "}");

        RequestSpecification reqSpec = reqBuilder.build();
        Response response =
                given(reqSpec)
                        .relaxedHTTPSValidation()
                        .log().all()
                        .log().uri()
                        .when()
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(409)
                        .log().status()
                        .log().body()
                        .extract().response();

    }
}
