package org.dcl;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

public class ValidateResponse extends ApplicationConstants{

    ApplicationConstants fields = new ApplicationConstants();
    @Test(priority = 1)
    public void CPFConsultRestrict() {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        List bodyData = fields.listAllCPF();

        reqBuilder.setBaseUri(BASEURI);

        for (int i = 0; i < bodyData.size(); i++){
            reqBuilder.setBasePath(BASEPATHrestr + bodyData.get(i));
            Object cpf = bodyData.get(i);

            RequestSpecification reqSpec = reqBuilder.build();
            String updatedMessage = String.format("O CPF %s tem problema", cpf);

                    given(reqSpec).
                            log().all()
                            .when()
                            .get()
                            .then()
                            .assertThat()
                            .statusCode(200)
                            .body(containsString(updatedMessage))
                            .log().status()
                            .log().body();
        }
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
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(201)
                        .body(NOME, is("Danielly"),
                                CPF, is("12345678916"),
                                EMAIL, is("danielly@danielly.com"),
                                VALOR, is(9999),
                                PARCELAS, is(10),
                                SEGURO, is(true))
                        .log().status()
                        .log().body()
                        .extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        int userID = jsonPathEvaluator.get("id");
        int valor = jsonPathEvaluator.get("valor");
        System.out.println("ID deste CPF é:"+userID);
        System.out.println("ID deste CPF é:"+valor);
    }

    @Test(priority = 2)
    public void createNewInvalidSimulationParcelas(){
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
        String updatedMessage = "Parcelas deve ser igual ou maior que 2";

                given(reqSpec)
                        .relaxedHTTPSValidation()
                        .log().all()
                        .log().uri()
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(400)
                        .body(containsString(updatedMessage))
                        .log().status()
                        .log().body();

    }
    @Test(priority = 2)
    public void createNewInvalidSimulationValorMenor(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        Map newBodyData = fields.simulationData();
        newBodyData.replace(CPF, "12345678917");
        newBodyData.replace(VALOR, 999.99);
        Gson gson = new Gson();
        String json = gson.toJson(newBodyData);

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody(json);

        RequestSpecification reqSpec = reqBuilder.build();
        String updatedMessage = "Valor deve ser maior ou igual a R$ 1.000";

        given(reqSpec)
                .relaxedHTTPSValidation()
                .log().all()
                .log().uri()
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(400)
                .body(containsString(updatedMessage))
                .log().status()
                .log().body();

    }
    @Test(priority = 2)
    public void createNewInvalidSimulationValorMaior(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        Map newBodyData = fields.simulationData();
        newBodyData.replace(CPF, "12345678917");
        newBodyData.replace(VALOR, 40009);
        Gson gson = new Gson();
        String json = gson.toJson(newBodyData);

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody(json);

        RequestSpecification reqSpec = reqBuilder.build();
        String updatedMessage = "Valor deve ser menor ou igual a R$ 40.000";

        given(reqSpec)
                .relaxedHTTPSValidation()
                .log().all()
                .log().uri()
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(400)
                .body(containsString(updatedMessage))
                .log().status()
                .log().body();

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

                given(reqSpec)
                        .relaxedHTTPSValidation()
                        .log().all()
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(409)
                        .body(containsString("CPF já existente"))
                        .log().status()
                        .log().body();
    }
    @Test(priority = 6)
    public void updateValidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        Map newBodyData = fields.simulationData();
        newBodyData.replace(EMAIL, "cardoso@danielly.com");
        String updatedCPF = "12345678916";
        Gson gson = new Gson();
        String json = gson.toJson(newBodyData);

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim+updatedCPF);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody(json);

        RequestSpecification reqSpec = reqBuilder.build();
        Response response =
                given(reqSpec)
                        .relaxedHTTPSValidation()
                        .log().all()
                        .when()
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .body(NOME, is("Danielly"),
                                CPF, is("12345678916"),
                                EMAIL, is("cardoso@danielly.com"),
                                VALOR, is(9999.0F),
                                PARCELAS, is(10),
                                SEGURO, is(true))
                        .log().status()
                        .log().body()
                        .extract().response()
                ;

        JsonPath jsonPathEvaluator = response.jsonPath();
        int userID = jsonPathEvaluator.get("id");
        System.out.println("ID deste CPF é:"+userID);

    }
    @Test(priority = 5)
    public void updateInvalidSimulation(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        String updatedCPF = "111111111111";
        Map newBodyData = fields.simulationData();
        Gson gson = new Gson();
        String json = gson.toJson(newBodyData);

        reqBuilder.setBaseUri(BASEURI);
        reqBuilder.setBasePath(BASEPATHSim+updatedCPF);
        reqBuilder.addHeader("Content-type","application/json");
        reqBuilder.setBody(json);


        RequestSpecification reqSpec = reqBuilder.build();
        String updatedMessage = String.format("CPF %s não encontrado", updatedCPF);

        given(reqSpec)
                        .relaxedHTTPSValidation()
                        .log().all()
                        .log().uri()
                        .when()
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(404)
                        .body(containsString(updatedMessage))
                        .log().status()
                        .log().body();
    }
    
    @Test(priority = 7)
    public void deleteSimulation(){
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
                        .body(containsString("OK"))
                        .log().all()
                            .extract().response();

    do {
        given(reqSpec)
                .relaxedHTTPSValidation()
                .log().all()
                .log().uri()
                .when()
                .delete()
                .then()
                .assertThat()
                .statusCode(404)
                .body(containsString("Simulação não encontrada"))
                .log().all();
    }
    while (response.statusCode() == 200);
}}
