package sn.ept.git.dic2;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAPIOrderItem {

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
    public void testAddOrderItemsEndpoint() throws Exception {
        String endpoint = "/api/v1/articleCommandes";
        String requestBody = "{\n" +
                "  \"commande\": {\n" +
                "    \"client\": {\n" +
                "      \"id\": 25,\n" +
                "      \"nom\": \"DIOP\",\n" +
                "      \"prenom\": \"Ousmane\",\n" +
                "      \"adresse\": \"PA-U26\",\n" +
                "      \"code_zip\": \"SN\",\n" +
                "      \"email\": \"ousmane@ept.sn\",\n" +
                "      \"etat\": \"Senegal\",\n" +
                "      \"telephone\": \"771234567\",\n" +
                "      \"ville\": \"Dakar\",\n" +
                "      \"actif\": 25,\n" +
                "      \"magasin\": {\n" +
                "        \"adresse\": \"PA-U25\",\n" +
                "        \"code_zip\": \"SN\",\n" +
                "        \"email\": \"magasin@ept.sn\",\n" +
                "        \"etat\": \"Senegal\",\n" +
                "        \"id\": 22,\n" +
                "        \"nom\": \"nom-magasin\",\n" +
                "        \"telephone\": \"771112233\",\n" +
                "        \"ville\": \"Dakar\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"date_commande\": \"2023-09-13T00:00:00Z[UTC]\",\n" +
                "    \"date_livraison\": \"2023-09-30T12:00:00Z[UTC]\",\n" +
                "    \"employe\": {\n" +
                "      \"id\": 25,\n" +
                "      \"nom\": \"DIOP\",\n" +
                "      \"prenom\": \"Ousmane\",\n" +
                "      \"adresse\": \"PA-U26\",\n" +
                "      \"code_zip\": \"SN\",\n" +
                "      \"email\": \"ousmane@ept.sn\",\n" +
                "      \"etat\": \"Senegal\",\n" +
                "      \"telephone\": \"771234567\",\n" +
                "      \"ville\": \"Dakar\",\n" +
                "      \"actif\": 25,\n" +
                "      \"magasin\": {\n" +
                "        \"adresse\": \"PA-U25\",\n" +
                "        \"code_zip\": \"SN\",\n" +
                "        \"email\": \"magasin@ept.sn\",\n" +
                "        \"etat\": \"Senegal\",\n" +
                "        \"id\": 22,\n" +
                "        \"nom\": \"nom-magasin\",\n" +
                "        \"telephone\": \"771112233\",\n" +
                "        \"ville\": \"Dakar\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"magasin\": {\n" +
                "      \"adresse\": \"PA-U25\",\n" +
                "      \"code_zip\": \"SN\",\n" +
                "      \"email\": \"magasin@ept.sn\",\n" +
                "      \"etat\": \"Senegal\",\n" +
                "      \"id\": 22,\n" +
                "      \"nom\": \"nom-magasin\",\n" +
                "      \"telephone\": \"771112233\",\n" +
                "      \"ville\": \"Dakar\"\n" +
                "    },\n" +
                "    \"numero\": 1,\n" +
                "    \"statut\": 1\n" +
                "  },\n" +
                "  \"ligne\": 1000,\n" +
                "  \"prix_depart\": 10.0,\n" +
                "  \"produit\": {\n" +
                "    \"annee_model\": 2023,\n" +
                "    \"categorie\": {\n" +
                "      \"id\": 1,\n" +
                "      \"nom\": \"ordered item 1000 of the order number 1\"\n" +
                "    },\n" +
                "    \"id\": 1,\n" +
                "    \"marque\": {\n" +
                "      \"id\": 1,\n" +
                "      \"nom\": \"Haro\"\n" +
                "    },\n" +
                "    \"nom\": \"nom produit\",\n" +
                "    \"prix_depart\": 10.0\n" +
                "  },\n" +
                "  \"quantite\": 10,\n" +
                "  \"remise\": 2.0\n" +
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
        assertTrue(responseBody.contains("1000 was created successfully."));

    }

    @Test
    @Order(2)
    public void testGetOrderItemsEndpoint() throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(BASE_URL + "/api/v1/articleCommandes"); // Spécifiez l'URL de l'endpoint à tester

        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        assertEquals(200, statusCode); // Vérifiez le code de réponse HTTP attendu
        // Vérifiez le contenu de la réponse ou d'autres détails selon vos besoins
        assertTrue(responseBody.contains("\"ligne\":1000"));

    }

    @Test
    @Order(3)
    public void testGetOrderItemByIdEndpoint() throws Exception {
        String endpoint = "/api/v1/articleCommandes"; // The API endpoint
        String param1Name = "ligne";
        String param1Value = "1000";
        String param2Name = "numComm";
        String param2Value = "1";

        // Create a URI builder and add query parameters
        URIBuilder uriBuilder = new URIBuilder(BASE_URL + endpoint);

        // Create a list of parameters
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("ligne", "1000"));
        parameters.add(new BasicNameValuePair("numComm", "1"));

        // Set the parameters
        uriBuilder.setParameters(parameters);

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
        assertTrue(responseBody.contains("\"prix_depart\":10.0"));

    }


    @Test
    @Order(4)
    public void testDeleteOrderItemByIdEndpoint() throws Exception {
        String endpoint = "/api/v1/articleCommandes/1000_1";

        // Create an HTTP client
        HttpClient httpClient = HttpClients.createDefault();

        // Execute the DELETE request and get the response
        HttpDelete httpDelete = new HttpDelete(BASE_URL + endpoint);

        HttpResponse response = httpClient.execute(httpDelete);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        assertEquals(200, statusCode);
        assertTrue(responseBody.contains("ordered item 1000 of the order number 1 was deleted successfully."));

    }
}
