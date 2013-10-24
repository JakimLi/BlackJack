package blackjack.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static blackjack.controller.Table.hit;
import static blackjack.util.GsonUtil.writeToJson;

public class HitServlet extends HttpServlet {
    private final static Logger logger = LoggerFactory.getLogger(HitServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HitActionResponse response = hit();
        writeToJson(resp, response);
    }
}
