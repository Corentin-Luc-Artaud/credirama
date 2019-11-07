package fr.unice.polytech.si5.al.analystservice.services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Kibana {
    private static final Logger logger = LogManager.getLogger(Kibana.class);

    public void addVisualisation(String visualisation, String type) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://kibana:5601/api/saved_objects/visualization"+type);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("kbn-xsrf", "true");
        Response response = invocationBuilder.post(Entity.entity(visualisation, MediaType.APPLICATION_JSON));
        logger.info(response.getEntity().toString());
    }

    public void addIndexPattern(JSONObject requestBody, String indexName) {
        logger.info(requestBody);
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://kibana:5601/api/saved_objects/index-pattern/creditrama");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("kbn-xsrf", "true");
        Response response = invocationBuilder.post(Entity.entity(requestBody.toString(), MediaType.APPLICATION_JSON));
        System.out.println(response);
        logger.info(response.getEntity().toString());
        logger.info(response.toString());
    }
}