package nz.ac.wgtn.swen301.restappender.server;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;

public class StatsHTMLServlet extends HttpServlet {

    protected Persistency persistency = LogsServlet.getPersistency();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String htmlFilePath = "log_levels.html";
        boolean fileExists = Files.exists(Paths.get(htmlFilePath));

        response.setContentType("text/html");

        // Create a map to store the sums for each logger and level
        Map<String, Map<String, Integer>> loggerLevelSums = new HashMap<>();

        try (PrintWriter writer = response.getWriter()) {
            writer.append("<html>");
            writer.append("<head><title>Log Levels</title></head>");
            writer.append("<body>");
            writer.append("<table border=\"1\">");
            writer.append("<tr>");
            writer.append("<th>Logger</th>");
            writer.append("<th>ALL</th>");
            writer.append("<th>TRACE</th>");
            writer.append("<th>DEBUG</th>");
            writer.append("<th>INFO</th>");
            writer.append("<th>WARN</th>");
            writer.append("<th>ERROR</th>");
            writer.append("<th>FATAL</th>");
            writer.append("<th>OFF</th>");
            writer.append("</tr>");

            for (int i = 0; i < persistency.getLogs().length(); i++) {
                JSONObject jsonObject = (JSONObject) persistency.getLogs().get(i);
                String logger = (String) jsonObject.get("logger");

                loggerLevelSums.putIfAbsent(logger, new HashMap<>());

                for (String level : new String[]{"ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"}) {
                    int levelSum = loggerLevelSums.get(logger).getOrDefault(level, 0);
                    loggerLevelSums.get(logger).put(level, levelSum + (getLevel((String) jsonObject.get("level"), level) ? 1 : 0));
                }
            }

            // Send the content to the response
            for (String logger : loggerLevelSums.keySet()) {
                writer.append("<tr>");
                writer.append("<td>").append(String.valueOf(logger)).append("</td>");
                for (String level : new String[]{"ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"}) {
                    writer.append("<td>").append(String.valueOf(loggerLevelSums.get(logger).getOrDefault(level, 0))).append("</td>");
                }
                writer.append("</tr>");
            }

            writer.append("</table>");
            writer.append("</body>");
            writer.append("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!fileExists) {
            try (FileWriter htmlWriter = new FileWriter(htmlFilePath)) {
                htmlWriter.append("<html>");
                htmlWriter.append("<head><title>Log Levels</title></head>");
                htmlWriter.append("<body>");
                htmlWriter.append("<table border=\"1\">");
                htmlWriter.append("<tr>");
                htmlWriter.append("<th>Logger</th>");
                htmlWriter.append("<th>ALL</th>");
                htmlWriter.append("<th>TRACE</th>");
                htmlWriter.append("<th>DEBUG</th>");
                htmlWriter.append("<th>INFO</th>");
                htmlWriter.append("<th>WARN</th>");
                htmlWriter.append("<th>ERROR</th>");
                htmlWriter.append("<th>FATAL</th>");
                htmlWriter.append("<th>OFF</th>");
                htmlWriter.append("</tr>");

                // Iterate through the loggerLevelSums map to write the sums to the HTML file
                for (String logger : loggerLevelSums.keySet()) {
                    htmlWriter.append("<tr>");
                    htmlWriter.append("<td>").append(String.valueOf(logger)).append("</td>");
                    for (String level : new String[]{"ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"}) {
                        htmlWriter.append("<td>").append(String.valueOf(loggerLevelSums.get(logger).getOrDefault(level, 0))).append("</td>");
                    }
                    htmlWriter.append("</tr>");
                }

                htmlWriter.append("</table>");
                htmlWriter.append("</body>");
                htmlWriter.append("</html>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean getLevel(String level, String JSONLevel) {
        return level.equalsIgnoreCase(JSONLevel);
    }
}
