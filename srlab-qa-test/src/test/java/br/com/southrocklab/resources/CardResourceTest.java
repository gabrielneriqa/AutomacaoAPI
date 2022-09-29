package br.com.southrocklab.resources;

import br.com.southrocklab.ApplicationTests;
import io.restassured.http.ContentType;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.assertj.core.api.Java6BDDAssertions.then;
import static org.hamcrest.Matchers.*;

public class CardResourceTest extends ApplicationTests {

    @Test
    public void deve_salvar_novo_card_no_sistema() {
        baseURI = "http://localhost";
        port = 9090;

        given()
                .body("{\n" +
                        "  \"brand\": \"VISA\",\n" +
                        "  \"customerId\":  1,\n" +
                        "  \"cvc\": \"112\",\n" +
                        "  \"expirationMoth\": 3,\n" +
                        "  \"expirationYear\": 2030,\n" +
                        "  \"holderName\": \"Gabriel Neri\",\n" +
                        "  \"number\": \"1111111111112222\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/card");




    }

    @Test
    public void deve_retornar_status_400_quando_salvar_card_com_cvv_maior_que_999() {
        baseURI = "http://localhost";
        port = 9090;

        given()
                .body("{\n" +
                        "  \"brand\": \"VISA\",\n" +
                        "  \"customerId\":  1,\n" +
                        "  \"cvc\": \"1121\",\n" +
                        "  \"expirationMoth\": 3,\n" +
                        "  \"expirationYear\": 2030,\n" +
                        "  \"holderName\": \"Gabriel Neri\",\n" +
                        "  \"number\": \"1111111111112222\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("/card")
        .then()
                .assertThat()
                        .statusCode(400);


    }

    @Test
    public void deve_retornar_status_400_quando_salvar_card_com_expiration_month_maior_que_12() {
        baseURI = "http://localhost";
        port = 9090;

        given()
                .body("{\n" +
                        "  \"brand\": \"VISA\",\n" +
                        "  \"customerId\":  1,\n" +
                        "  \"cvc\": \"111\",\n" +
                        "  \"expirationMoth\": 13,\n" +
                        "  \"expirationYear\": 2030,\n" +
                        "  \"holderName\": \"Gabriel Neri\",\n" +
                        "  \"number\": \"1111111111112222\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("/card")
        .then()
                .assertThat()
                        .statusCode(400);


    }

    @Test
    public void deve_retornar_status_400_quando_salvar_card_com_expiration_year_menor_que_2022() {
        baseURI = "http://localhost";
        port = 9090;

        given()
                .body("{\n" +
                        "  \"brand\": \"VISA\",\n" +
                        "  \"customerId\":  1,\n" +
                        "  \"cvc\": \"131\",\n" +
                        "  \"expirationMoth\": 3,\n" +
                        "  \"expirationYear\": 2002,\n" +
                        "  \"holderName\": \"Gabriel Neri\",\n" +
                        "  \"number\": \"1111111111112222\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("/card")
        .then()
                .assertThat()
                         .statusCode(400);

    }

    @Test
    public void deve_retornar_status_400_quando_salvar_card_com_number_de_15_digitos() {
        baseURI = "http://localhost";
        port = 9090;

        given()
                .body("{\n" +
                        "  \"brand\": \"VISA\",\n" +
                        "  \"customerId\":  1,\n" +
                        "  \"cvc\": \"141\",\n" +
                        "  \"expirationMoth\": 3,\n" +
                        "  \"expirationYear\": 2030,\n" +
                        "  \"holderName\": \"Gabriel Neri\",\n" +
                        "  \"number\": \"111111111111222\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("/card")
        .then()
                .assertThat()
                        .statusCode(400);


    }

    @Test
    public void deve_deletar_um_card_salvo_no_sistema() {
        baseURI = "http://localhost";
        port = 9090;

        delete("/card/2");



    }

    @Test
    public void deve_retornar_status_404_ao_deletar_um_card_com_id_nao_salvo_no_sistema() {
        baseURI = "http://localhost";
        port = 9090;

        delete("/card/8")

            .then()
                .assertThat()
                    .statusCode(404);



    }
}
