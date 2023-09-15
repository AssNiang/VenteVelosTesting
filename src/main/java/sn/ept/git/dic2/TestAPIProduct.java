package sn.ept.git.dic2;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAPIProduct {

    private static final String BASE_URL = "http://localhost:8080/VenteVelos-1.0-SNAPSHOT"; // Remplacez 8080 par le port que vous avez spécifié pour le serveur embarqué

    @BeforeClass
    public static void setUp() throws Exception {
        // Démarrez le serveur embarqué avant d'exécuter les tests
        JettyServerTestBase.startServer();
    }

    @AfterClass
    public static void setDown() throws Exception {
        // Eteindre le serveur embarqué après l'exécution des tests
        JettyServerTestBase.stopServer();
    }

    @Test
    @Order(1)
    public void testAddProductsEndpoint() throws Exception {
        String endpoint = "/api/v1/produits";
        String requestBody = "{\n" +
                                "  \"annee_model\": 2023,\n" +
                                "  \"categorie\": {\n" +
                                "    \"id\": 1,\n" +
                                "    \"nom\": \"Children Bicycles\"\n" +
                                "  },\n" +
                                "  \"id\": 1000,\n" +
                                "  \"marque\": {\n" +
                                "    \"id\": 1,\n" +
                                "    \"nom\": \"Haro\"\n" +
                                "  },\n" +
                                "  \"nom\": \"nom produit\",\n" +
                                "  \"prix_depart\": 10.0\n" +
                                "}";

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(BASE_URL + endpoint);

        // Set the request body
        StringEntity requestEntity = new StringEntity(requestBody);
        httpPost.setEntity(requestEntity);
        httpPost.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpPost);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        // Check the status code of the response
        assertEquals(201, statusCode);
        // Check if the response contains a success message
        assertTrue(responseBody.contains("nom produit was created successfully."));

    }

    @Test
    @Order(2)
    public void testGetProductsEndpoint() throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(BASE_URL + "/api/v1/produits"); // Spécifiez l'URL de l'endpoint à tester

        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        assertEquals(200, statusCode); // Vérifiez le code de réponse HTTP attendu
        // Vérifiez le contenu de la réponse ou d'autres détails selon vos besoins
        assertTrue(responseBody.contains("\"nom\":\"nom produit\""));

    }

    @Test
    @Order(3)
    public void testGetProductByIdEndpoint() throws Exception {
        String endpoint = "/api/v1/produits"; // The API endpoint
        String param1Name = "number";
        String param1Value = "1000";

        // Create a URI builder and add query parameters
        URIBuilder uriBuilder = new URIBuilder(BASE_URL + endpoint);
        uriBuilder.setParameter(param1Name, param1Value);


        // Create a URI from the builder
        URI uri = uriBuilder.build();

        // Create an HTTP client
        HttpClient httpClient = HttpClients.createDefault();

        // Create an HTTP GET request with the URI
        HttpGet request = new HttpGet(uri);

        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        assertEquals(200, statusCode); // Vérifiez le code de réponse HTTP attendu
        assertTrue(responseBody.contains("\"nom\":\"nom produit\""));

    }


    @Test
    @Order(4)
    public void testDeleteProductByIdEndpoint() throws Exception {
        String endpoint = "/api/v1/produits/1000";

        // Create an HTTP client
        HttpClient httpClient = HttpClients.createDefault();

        // Execute the DELETE request and get the response
        HttpDelete httpDelete = new HttpDelete(BASE_URL + endpoint);

        HttpResponse response = httpClient.execute(httpDelete);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        assertEquals(200, statusCode);
        assertTrue(responseBody.contains("nom produit was deleted successfully."));

    }
}
