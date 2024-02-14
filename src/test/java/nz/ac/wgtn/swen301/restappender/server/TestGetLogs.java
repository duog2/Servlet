package nz.ac.wgtn.swen301.restappender.server;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetLogs {

    @Test
    public void testDoGet() throws Exception {
        LogsServlet logsServlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("limit", "100");
        request.setParameter("level", "info");

        logsServlet.doGet(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getContentType());

    }
}
