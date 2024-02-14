package nz.ac.wgtn.swen301.restappender.server;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStatsXLS {

    @Test
    public void testGetStatsXLS() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        StatsCSVServlet statsCSVServlet = new StatsCSVServlet();
        statsCSVServlet.doGet(request, response);

        assertEquals(200, response.getStatus(), "Expected HTTP 200 status");

        File file = new File("log_levels.xls");
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        fileReader.readLine();
        String line;
        String name;
        int all = 0, trace = 0, debug = 0, info = 0, warn = 0, error = 0, fatal = 0, off = 0;
        while ((line = fileReader.readLine()) != null) {
                String input = Arrays.toString(line.split("\t"));
                input = input.substring(1, input.length() - 1);
                String[] substrings = input.split(", ");
                name = substrings[0];
                all += Integer.parseInt(substrings[1]);
                trace += Integer.parseInt(substrings[2]);
                debug += Integer.parseInt(substrings[3]);
                info += Integer.parseInt(substrings[4]);
                warn += Integer.parseInt(substrings[5]);
                error += Integer.parseInt(substrings[6]);
                fatal += Integer.parseInt(substrings[7]);
                off += Integer.parseInt(substrings[8]);

        }
        assertEquals(0,all);
        assertEquals(7,trace);
        assertEquals(1,debug);
        assertEquals(2,info);
        assertEquals(0,warn);
        assertEquals(1,error);
        assertEquals(1,fatal);
        assertEquals(0,off);
        fileReader.close();

    }
}
