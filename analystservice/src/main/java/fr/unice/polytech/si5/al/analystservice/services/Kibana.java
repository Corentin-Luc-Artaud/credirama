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

public class Kibana {
    private static final Logger logger = LogManager.getLogger(Kibana.class);

    public void addVisualisation(String visualisation) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://kibana:5601/api/saved_objects/visualization");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("kbn-xsrf", "true");
        Response response = invocationBuilder.post(Entity.entity(visualisation, MediaType.APPLICATION_JSON));
        logger.info(response.getEntity().toString());
    }
}