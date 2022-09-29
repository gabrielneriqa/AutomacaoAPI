package br.com.southrocklab.resources;

import br.com.southrocklab.ApplicationTests;
import io.restassured.http.ContentType;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.assertj.core.api.Java6BDDAssertions.then;
import static org.hamcrest.Matchers.*;

public class CustomerResourceTest extends ApplicationTests {

    @Test
    public void deve_salvar_novo_customer_no_sistema() {
        baseURI = "http://localhost";
        port = 9090;

        given()
                .body("{\n" +
                        "  \"birthDate\": \"1964-02-22\",\n" +
                        "  \"lastName\": \"da Costa\",\n" +
                        "  \"name\": \"Arlene\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/customer");

    }

    @Test
    public void deve_retornar_status_400_quando_salvar_customer_ja_salvo() {
        baseURI = "http://localhost";
        port = 9090;

        given()
                .body("{\n" +
                        "  \"birthDate\": \"1964-02-22\",\n" +
                        "  \"lastName\": \"da Costa\",\n" +
                        "  \"name\": \"Arlene\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("/customer")
        .then()
                .assertThat()
                        .statusCode(400);



    }

    @Test
    public void deve_retornar_status_400_quando_salvar_customer_com_birth_date_maior_que_hoje() {
        baseURI = "http://localhost";
        port = 9090;

        given()
                .body("{\n" +
                        "  \"birthDate\": \"2100-08-30\",\n" +
                        "  \"lastName\": \"Neri\",\n" +
                        "  \"name\": \"Gabriel\"\n" +
                        "}")
                .contentType(ContentType.JSON)

        .when()
                .post("/customer")
        .then()
            .assertThat()
                    .statusCode(400);


    }

    @Test
    public void deve_procurar_customer_pelo_name_e_last_name() {
        baseURI = "http://localhost";
        port = 9090;

        get("/customer/Arlne/da Costa");

    }

    @Test
    public void deve_retornar_status_404_quando_buscar_customer_por_name_e_last_name_inexistente() {
        baseURI = "http://localhost";
        port = 9090;

        get("/customer/josefino/Soares")
                .then()
                        .assertThat()
                                .statusCode(404);

    }

    @Test
    public void deve_atualizar_o_last_name_de_um_customer() {
        baseURI = "http://localhost";
        port = 9090;

        given()
                .body("{\n" +
                        "  \"birthDate\": \"1964-02-22\",\n" +
                        "  \"lastName\": \"de Souza\",\n" +
                        "  \"name\": \"Arlene\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                    .put("/customer/1");
    }

    @Test
    public void deve_retornar_status_404_ao_atualizar_um_customer_com_id_nao_salvo_no_sistema() {
        baseURI = "http://localhost";
        port = 9090;

        given()
                .body("{\n" +
                        "  \"birthDate\": \"1964-02-22\",\n" +
                        "  \"lastName\": \"de Souza\",\n" +
                        "  \"name\": \"José\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .put("/customer/10")
        .then()
                .assertThat()
                        .statusCode(404);

    }

    @Test
    public void deve_deletar_um_customer_salvo_no_sistema() {
        baseURI = "http://localhost";
        port = 9090;

        delete("/customer/1");


    }

    @Test
    public void deve_retornar_status_404_ao_deletar_um_customer_com_id_nao_salvo_no_sistema() {
        baseURI = "http://localhost";
        port = 9090;

        delete("/customer/5")
                .then()
                        .assertThat()
                                .statusCode(404);

    }
}
