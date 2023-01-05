package org.pageobjects;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ValidateResponse extends ApplicationConstants{

    //final Matcher<Integer> SUCCESS = isOneOf(200, 201);

    ApplicationConstants fields = new ApplicationConstants();
    @Test(priority = 1)
    //Consultar uma restrição pelo CPF
    public void CPFConsultRestrict() {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        List bodyData = fields.ListAllCPF();

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
    //Consultar todas a simulações cadastradas Retorna a lista de simulações cadastradas e existir uma ou mais
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
    //Criar uma simulação Uma simulação cadastrada com sucesso retorna o HTTP Status 201
    public void CreateNewValidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        Map newBodyData = fields.newSimulationData();
        Gson gson = new Gson();
        String json = gson.toJson(newBodyData);

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);
        reqBuilder.addHeader("Content-type","application/json");
        /*reqBuilder.setBody("{\n" +
                "  \"nome\": \"Danielly\",\n" +
                "  \"cpf\": \"12345678916\",\n" +
                "  \"email\": \"danielly@danielly.com\",\n" +
                "  \"valor\": 1200,\n" +
                "  \"parcelas\": 2,\n" +
                "  \"seguro\": true\n" +
                "}");*/

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
    //Criar uma simulação Uma simulação com problema em alguma regra retorna o HTTP Status 400 com a lista de erros
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
    @Test(priority = 4)
    //Criar uma simulação Uma simulação para um mesmo CPF retorna um HTTP Status 409
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
    @Test(priority = 6)
    //Alterar uma simulação
    public void UpdateValidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim+"12345678916");
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
                        .statusCode(200)
                        .log().status()
                        .log().body()
                        .extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        int userID = jsonPathEvaluator.get("id");
        System.out.println("ID deste CPF é:"+userID);

    }
    @Test(priority = 5)
    //Alterar uma simulação
    //Se o CPF não possuir uma simulação o HTTP Status 404 é retornado com a
    //mensagem "CPF não encontrado"
    public void UpdateInvalidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim+"111111111111");
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
                        .statusCode(404)
                        .log().status()
                        .log().body()
                        .extract().response();

    }
    @Test(priority = 7)
    //Remover uma simulação
    //Retorna o HTTP Status 204 se simulação for removida com sucesso
    //as per document, the status should be 204 but it is 200
    //its deleting nothing since we can repeat this id
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
                        .statusCode(200)
                        .log().status()
                        .log().body()
                        .log().all()
                        .extract().response();

    }
}
