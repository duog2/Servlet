package nz.ac.wgtn.swen301.restappender.server;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDeleteLogs {

    @Test
    public void testDoDelete() throws Exception {
        LogsServlet logsServlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        logsServlet.doDelete(request, response);

        assertEquals(200, response.getStatus());

    }
}
