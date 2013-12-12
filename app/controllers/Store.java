package controllers;

import com.google.gson.*;
import org.lightcouch.CouchDbClient;
import play.mvc.Controller;


public class Store extends Controller {

    public static void save(String id, String jsonArray) {
        String debugmessage = "nothing happened";

        // Save to couchdb
        try {
            CouchDbClient dbClient = new CouchDbClient();

            JsonObject json = new JsonObject();

            JsonParser parser = new JsonParser();
            JsonElement stepsElement = parser.parse(jsonArray.toString());
            JsonArray gsonArray = stepsElement.getAsJsonArray();

            json.addProperty("_id", id);
            json.add("items", gsonArray);

            /*
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("_id", id);
            map.put("steps", steps);
            */

            if (dbClient.contains(id)) {
                json = dbClient.find(JsonObject.class, id);
                if (json != null) {
                    String rev = json.get("_rev").toString().replaceAll("\"", "");
                    json.addProperty("_rev", rev);
                    json.addProperty("test", "testar");
                    json.add("items", gsonArray);
                    dbClient.update(json);
                }
            }
            else {
                dbClient.save(json);
            }

            dbClient.shutdown();
            debugmessage = "it seemed to work";
        }
        catch (Exception ex) {
            debugmessage = ex.getMessage();
        }

        renderJSON("{\"debuginfo\": \"" + debugmessage + "\"}");
    }

    public static void load(String id) {
        JsonObject json = null;
        CouchDbClient dbClient = new CouchDbClient();
        try {
            json = dbClient.find(JsonObject.class, id);
        }
        catch (Exception ex) {

        }

        renderJSON(json);
    }

}
