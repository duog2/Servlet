package nz.ac.wgtn.swen301.restappender.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.exit(1);
        }

        String type = args[0];
        String fileName = args[1];

        String serverURL = "http://localhost:8080/restappender";
        String endpoint = "/stats/" + type;

        HttpClient client = HttpClient.newHttpClient();

        try {
            if (type.equals("excel")) {
                endpoint = "/stats/excel";
            } else if (type.equals("csv")) {
                endpoint = "/stats/csv";
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverURL + endpoint))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() != 200) {
                System.err.println("Failed to fetch data. HTTP Status Code: " + response.statusCode());
                System.exit(1);
            }
            saveToFile(response.body(), fileName);
            System.out.println("Save to file successfully!");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error while connecting to the server: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void saveToFile(byte[] data, String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(data);
        }
    }
}
