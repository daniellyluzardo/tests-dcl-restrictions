package org.pageobjects;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ValidateResponse extends ApplicationConstants{

    ApplicationConstants fields = new ApplicationConstants();
    @Test(priority = 1)
    public void CPFConsultRestrict() {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        List bodyData = fields.listAllCPF();

        reqBuilder.setBaseUri(BASEURI);

        for (int i = 0; i < bodyData.size(); i++){
            reqBuilder.setBasePath(BASEPATHrestr + bodyData.get(i));

            RequestSpecification reqSpec = reqBuilder.build();
            Response response =
                    given(reqSpec).
                            log().all()
                            .when()
                            .get()
                            .then()
                            .assertThat()
                            .statusCode(200)
                            .log().status()
                            .log().body()
                            .extract().response();
        }

    }
    @Test(priority = 0)
    public void CPFALLSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);

        RequestSpecification reqSpec = reqBuilder.build();
        Response response =
                given(reqSpec).
                        log().all()
                        .when()
                        .get()
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().status()
                        .log().all()
                        .extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        List userID = jsonPathEvaluator.get("id");
        System.out.println("IDs da lista de CPF cadastrados são:"+userID);

    }

    @Test(priority = 3)
    public void createNewValidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        Map newBodyData = fields.simulationData();
        Gson gson = new Gson();
        String json = gson.toJson(newBodyData);

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody(json);

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

        JsonPath jsonPathEvaluator = response.jsonPath();
        int userID = jsonPathEvaluator.get("id");
        System.out.println("ID deste CPF é:"+userID);
    }

    @Test(priority = 2)
    public void createNewInvalidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        Map newBodyData = fields.simulationData();
        newBodyData.replace(CPF, "12345678917");
        newBodyData.replace(PARCELAS, 1);
        Gson gson = new Gson();
        String json = gson.toJson(newBodyData);

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody(json);

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
    @Test(priority = 4)
    public void createNewDuplicatedSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        Map newBodyData = fields.simulationData();
        Gson gson = new Gson();
        String json = gson.toJson(newBodyData);

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody(json);

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
    @Test(priority = 6)
    public void updateValidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        Map newBodyData = fields.simulationData();
        newBodyData.replace(EMAIL, "cardoso@danielly.com");
        Gson gson = new Gson();
        String json = gson.toJson(newBodyData);

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim+"12345678916");
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody(json);


        RequestSpecification reqSpec = reqBuilder.build();
        Response response =
                given(reqSpec)
                        .relaxedHTTPSValidation()
                        .log().uri()
                        .when()
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().status()
                        .log().body()
                        .extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        int userID = jsonPathEvaluator.get("id");
        System.out.println("ID deste CPF é:"+userID);

    }
    @Test(priority = 5)
    public void updateInvalidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        Map newBodyData = fields.simulationData();
        Gson gson = new Gson();
        String json = gson.toJson(newBodyData);

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim+"111111111111");
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody(json);


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
                        .statusCode(404)
                        .log().status()
                        .log().body()
                        .extract().response();

    }
    @Test(priority = 7)
    public void deleteEmptySimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim+"11");
        reqBuilder.addHeader("Content-type","application/json");

        RequestSpecification reqSpec = reqBuilder.build();
        Response response =
                given(reqSpec)
                        .relaxedHTTPSValidation()
                        .log().all()
                        .log().uri()
                        .when()
                        .delete()
                        .then()
                        .assertThat()
                        .statusCode(204)
                        .log().status()
                        .log().body()
                        .log().all()
                        .extract().response();

    }
}
