package nz.ac.wgtn.swen301.restappender.server;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;

public class Persistency extends HttpServlet {

    public Persistency(){
        DB = new JSONArray();
        addLog("d290f1ee-6c54-4b01-90e6-d701748f0851",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Mandu",
                "debug",
                "string");
        addLog("Ai love kajun",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "info",
                "string");
        addLog("Ai love mandu",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "info",
                "string");
        addLog("phew",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "fatal",
                "string");
        addLog("ako",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "error",
                "string");
        addLog("nano",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "trace",
                "string");
        addLog("nano",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "trace",
                "string");
        addLog("nano",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "trace",
                "string");
        addLog("nano",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "trace",
                "string");
        addLog("nano",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "trace",
                "string");
        addLog("nano",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "trace",
                "string");
        addLog("nano",
                "application started",
                "04-05-2021 13:30:45",
                "main",
                "Kajun",
                "trace",
                "string");
    }

    private JSONArray DB;

    public JSONArray getLogs(){return DB;}

    public void deleteAllLogs(){
        DB.clear();
    }

    public void addLog(String id, String message, String timestamp, String thread, String logger, String level, String errorDetails) {
        JSONObject log = new JSONObject();
        log.put("id", id);
        log.put("message", message);
        log.put("timestamp", timestamp);
        log.put("thread", thread);
        log.put("logger", logger);
        log.put("level", level);
        log.put("errorDetails", errorDetails);
        DB.put(log);
    }


}
