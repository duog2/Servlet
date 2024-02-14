package nz.ac.wgtn.swen301.restappender.server;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPostLogs {

    @Test
    public void testDoPost() throws Exception {
        LogsServlet logsServlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setContentType("application/json");
        request.setParameter("limit", "100");
        request.setParameter("level", "info");


        JSONObject log = new JSONObject();
        log.put("id", "123");
        log.put("message", "I'm mandu");
        log.put("timestamp", "2023-10-10T12:00:00");
        log.put("thread", "main");
        log.put("logger", "Logger");
        log.put("level", "info");
        log.put("errorDetails", "you are bad");

        request.setContent(log.toString().getBytes());

        logsServlet.doPost(request, response);

        assertEquals(201, response.getStatus());
    }
}
