import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;

public class testeBasic {

    public String lerJson(String caminhoArquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
    }
    // Define um método de teste
    @Test
    public void testGetBooking() {
// Configura a URL base para as requisições da API
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

// Configura e executa a requisição GET para o endpoint "/booking/"
        given() // Define as configurações da requisição (headers, parâmetros, etc.)
                .header("Accept", "*/*") //adiciona o header accept
        .when() // Indica o início da execução da requisição
                .get("/booking/") // Especifica o endpoint a ser chamado
         .then() // Define as validações da resposta
                .statusCode(200) // Verifica se o status code da resposta é 200 (OK)
                .log().all(); // Loga no console todos os detalhes da resposta (body, headers, etc.)
    }

    @Test
    public void testeWithId(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        given()
                .header("Accept", "application/json")
                .when()
                .get("booking/492")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("C"))
                .body("lastname", equalTo("Talmo"))
                .body("totalprice", equalTo(7777))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2025-01-01"))
                .body("bookingdates.checkout", equalTo("2025-01-11"))
                .body("additionalneeds", equalTo("Breakfast"));

    }

    @Test
    public void cadastrarReserva() throws IOException{
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String jsonBody = lerJson("src/test/resources/payloads/reserva.json");

        given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .body("booking.firstname", equalTo("C"))
                .body("booking.lastname", equalTo("Talmo"))
                .body("booking.totalprice", equalTo(7777))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2025-01-01"))
                .body("booking.bookingdates.checkout", equalTo("2025-01-11"))
                .body("booking.additionalneeds", equalTo("Breakfast"));
    }


}
