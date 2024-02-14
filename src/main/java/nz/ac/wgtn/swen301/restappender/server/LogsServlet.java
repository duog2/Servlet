package nz.ac.wgtn.swen301.restappender.server;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LogsServlet extends HttpServlet {
    public LogsServlet(){}
    private static Persistency persistency = new Persistency();

    public  static Persistency getPersistency(){
        return persistency;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        String level = req.getParameter("level");
        String limit = req.getParameter("limit");
        String logs = req.getParameter("logs");

        if (level == null) {
            response.getWriter().println(persistency.getLogs().toString(2));
            return;
        }
        JSONArray list = persistency.getLogs();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        validateParameters(level, limit);

        JSONArray logsArray = new JSONArray();
        for (int i = 0; i < list.length(); i++) {
            JSONObject log = list.getJSONObject(i);
            if (log.getString("level").equalsIgnoreCase(level)) {
                logsArray.put(log);
            }
        }
        out.println(logsArray);
        out.close();
    }

    private void validateParameters(String levelParam, String limitParam) {
        if (levelParam != null && !isValidLogLevel(levelParam)) {
            throw new IllegalArgumentException("Invalid log level: " + levelParam);
        }

        if (limitParam != null) {
            try {
                int limit = Integer.parseInt(limitParam);
                if (limit <= 0) {
                    throw new IllegalArgumentException("Limit must be a positive, non-zero integer");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid limit parameter: " + limitParam);
            }
        }
        if(!isValidLogLevel(levelParam)){
            throw new IllegalArgumentException("Invalid level");
        }
    }

    private boolean isValidLogLevel(String levelParam) {
        List<String> level = new ArrayList<>();
        level.add("debug");
        level.add("info");
        level.add("info");
        level.add("error");
        level.add("warn");
        level.add("fatal");
        level.add("all");
        level.add("trace");
        level.add("off");
        levelParam = levelParam.toLowerCase();
        if (level.contains(levelParam)){
            return true;
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
            String contentType = req.getContentType();
            if (contentType != null && contentType.contains("application/json")) {
                String requestBody = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
                JSONObject logJson = new JSONObject(requestBody);

                String id = logJson.optString("id");
                String message = logJson.optString("message");
                String timestamp = logJson.optString("timestamp");
                String thread = logJson.optString("thread");
                String logger = logJson.optString("logger");
                String level = logJson.optString("level");
                String errorDetails = logJson.optString("errorDetails");
                isValidLogLevel(level);

                persistency.addLog(id, message, timestamp, thread, logger, level, errorDetails);

                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().println("Log event added successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                response.getWriter().println("Unsupported media type. Expected JSON.");
            }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        persistency.deleteAllLogs();
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("All log events deleted successfully.");
    }
}
