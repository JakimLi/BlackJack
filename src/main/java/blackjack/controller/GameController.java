package blackjack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class GameController {

    @Autowired
    @Qualifier("table")
    private Table table;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public @ResponseBody StartActionResponse start() {
        table.initTable();
        return table.startGame();
    }

    @RequestMapping(value = "/stay", method = RequestMethod.GET)
    public @ResponseBody StayActionResponse stay() {
        return table.stay();
    }

    @RequestMapping(value = "/hit", method = RequestMethod.GET)
    public @ResponseBody HitActionResponse hit() {
        return table.hit();
    }
}
