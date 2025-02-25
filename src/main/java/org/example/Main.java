package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        Path filePath = Path.of("src/main/resources/files/family.json");

        try {
            String content = Files.readString(filePath);
            JSONObject jsonObj = new JSONObject(content);
            JSONArray familie = jsonObj.getJSONArray("members");

            IntStream.range(0, familie.length())
                    .mapToObj(i -> familie.getJSONObject(i))
                    .forEach(mitglied -> {
                        String vorname = mitglied.getString("firstname");
                        String nachname = mitglied.getString("lastname");
                        System.out.println("Vorname: " + vorname + ", Nachname: " + nachname);
                    });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        /*
        ===============================
         */
        String jsonString = "{\n" +
                "  \"familyName\": \"Muster\",\n" +
                "  \"members\": [\n" +
                "    {\n" +
                "      \"firstName\": \"Peter\",\n" +
                "      \"lastName\": \"Muster\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Maria\",\n" +
                "      \"lastName\": \"Muster\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Gson gson = new Gson();

        Family family = gson.fromJson(jsonString, Family.class);

        System.out.println("Familienname: " + family.getFamilyName());
        for (Person member : family.getMembers()) {
            System.out.println("Vorname: " + member.getFirstName() + ", Nachname: " + member.getLastName());
        }

        /*
        ======================================
         */

        try {

            URL url = new URL("https://transport.opendata.ch/v1/connections?from=Winterthur&to=Zürich");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // JSON-Daten aus der Antwort lesen
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            Gson gson1 = new Gson();
            JsonObject jsonResponse = gson1.fromJson(reader, JsonObject.class);
            JsonArray connections = jsonResponse.getAsJsonArray("connections");

            // Ausgabe der ersten zwei Verbindungen
            for (int i = 0; i < 2 && i < connections.size(); i++) {
                JsonObject connectionData = connections.get(i).getAsJsonObject();
                String departure = connectionData.getAsJsonObject("from").get("departure").getAsString();
                String arrival = connectionData.getAsJsonObject("to").get("arrival").getAsString();
                String platform = connectionData.getAsJsonObject("from").get("platform").getAsString();

                System.out.println("Abfahrt: " + departure);
                System.out.println("Ankunft: " + arrival);
                System.out.println("Bahnsteignummer: " + platform);
                System.out.println("-------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
