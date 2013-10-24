package blackjack.util;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class GsonUtil implements Serializable {

    public static void writeToJson(HttpServletResponse resp, Object object) throws IOException {
        String json = new Gson().toJson(object);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }
}