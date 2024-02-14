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
import java.util.HashMap;
import java.util.Map;

public class StatsExcelServlet extends HttpServlet {
    public StatsExcelServlet(){}
    protected Persistency persistency = LogsServlet.getPersistency();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String csvFilePath = "log_levels.xls";

        Map<String, Map<String, Integer>> loggerLevelSums = new HashMap<>();

        response.setContentType("text/plain");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("Logger\tALL\tTRACE\tDEBUG\tINFO\tWARN\tERROR\tFATAL\tOFF");

            for (int i = 0; i < persistency.getLogs().length(); i++) {
                JSONObject jsonObject = (JSONObject) persistency.getLogs().get(i);
                String logger = (String) jsonObject.get("logger");

                loggerLevelSums.putIfAbsent(logger, new HashMap<>());

                for (String level : new String[]{"ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"}) {
                    int levelSum = loggerLevelSums.get(logger).getOrDefault(level, 0);
                    loggerLevelSums.get(logger).put(level, levelSum + (getLevel((String) jsonObject.get("level"), level) ? 1 : 0));
                }
            }

            for (String logger : loggerLevelSums.keySet()) {
                writer.append(logger).append("\t");
                for (String level : new String[]{"ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"}) {
                    writer.append(String.valueOf(loggerLevelSums.get(logger).getOrDefault(level, 0))).append("\t");
                }
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!Files.exists(Paths.get(csvFilePath)) && !loggerLevelSums.isEmpty()) {
            try (FileWriter csvWriter = new FileWriter(csvFilePath)) {
                csvWriter.append("Logger\tALL\tTRACE\tDEBUG\tINFO\tWARN\tERROR\tFATAL\tOFF\n");

                for (String logger : loggerLevelSums.keySet()) {
                    csvWriter.append(logger).append("\t");
                    for (String level : new String[]{"ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"}) {
                        csvWriter.append(String.valueOf(loggerLevelSums.get(logger).getOrDefault(level, 0))).append("\t");
                    }
                    csvWriter.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    protected boolean getLevel(String level, String JSONLevel){
        if(level.equalsIgnoreCase(JSONLevel)){
            return true;
        }
        return false;
    }
}
