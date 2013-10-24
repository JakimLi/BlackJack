package blackjack.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static blackjack.controller.Table.startGame;
import static blackjack.util.GsonUtil.writeToJson;

public class StartServlet extends HttpServlet {
    private final static Logger logger = LoggerFactory.getLogger(StartServlet.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StartActionResponse startActionResponse = startGame();
        writeToJson(resp, startActionResponse);
    }
}
