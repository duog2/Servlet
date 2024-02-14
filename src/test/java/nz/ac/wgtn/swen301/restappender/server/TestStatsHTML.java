package nz.ac.wgtn.swen301.restappender.server;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestStatsHTML {

    @Test
    public void testGetStatsHTML() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        StatsHTMLServlet statsHTMLServlet = new StatsHTMLServlet();
        statsHTMLServlet.doGet(request,response);

        assertEquals(200, response.getStatus(), "Expected HTTP 200 status");
        try {
            File htmlFile = new File("log_levels.html");
            Document document = Jsoup.parse(htmlFile, "UTF-8");

            String title = document.title();
            assertEquals("Log Levels", title);
            String name;
            int all = 0, trace = 0, debug = 0, info = 0, warn = 0, error = 0, fatal = 0, off = 0;

            Element table = document.select("table").first();
            assertNotNull(table);

            Elements rows = table.select("tr");
            for (Element row : rows) {
                Elements cells = row.select("td");

                if (cells.size() == 9) {
                    name = cells.get(0).text();
                    all += Integer.parseInt(cells.get(1).text());
                    trace += Integer.parseInt(cells.get(2).text());
                    debug += Integer.parseInt(cells.get(3).text());
                    info += Integer.parseInt(cells.get(4).text());
                    warn += Integer.parseInt(cells.get(5).text());
                    error += Integer.parseInt(cells.get(6).text());
                    fatal += Integer.parseInt(cells.get(7).text());
                    off += Integer.parseInt(cells.get(8).text());
                } else {
                    System.out.println("Invalid row: " + row.html());
                }
            }
            assertEquals(0,all);
            assertEquals(1,debug);
            assertEquals(7,trace);
            assertEquals(2,info);
            assertEquals(0,warn);
            assertEquals(1,error);
            assertEquals(1,fatal);
            assertEquals(0,off);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
