import spark.ModelAndView;
import spark.Request;
import spark.Session;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    static Connection getConnection() throws SQLException {
        System.out.println("yup in here.");
        //return DriverManager.getConnection("jdbc:mysql://localhost/gamedb", "root", "root");
        return DriverManager.getConnection("jdbc:mysql://localhost/SimpleDB", "root", "root");
    }

    public static void get(String path, SecureTemplateViewRoute route) {
        Spark.get(path, route, new HandlebarsTemplateEngine());
    }

    public static void post(String path, SecureTemplateViewRoute route) {
        Spark.post(path, route, new HandlebarsTemplateEngine());
    }


    public static void main(String[] args) {

        Spark.staticFiles.location("/static");

        get("/", Main::loginPage);
        post("/", Main::tryLogin);
        post("/saveGame", Main::saveGame);
        get("/logout", Main::logoutAndRestart);
    }

    static ModelAndView loginPage(Request rq) {
        Map<String, Object> map = new HashMap<>();
        map.put("hasError", false);
        return new ModelAndView(map, "Login.hbs");
    }

    static ModelAndView tryLogin(Request rq) throws Exception {
        String username = rq.queryParams("username");
        String password = rq.queryParams("password");

        PlayerInfo pInfo = PlayerInfo.findPlayerInfo(username);

        if (pInfo != null && pInfo.getUserPw().equals(password)) {
            Session session = rq.session();
            session.attribute("username", pInfo.getUsername());

            List<Score> scores = Score.orderedTopScores(pInfo);

            Map<String, Object> map = new HashMap<>();
            map.put("name", pInfo.getFullName());
            map.put("scores", scores);

            return new ModelAndView(map, "Game.hbs");
        }

        // user not found or wrong password
        System.out.println(username);
        System.out.println(password);
        Map<String, Object> map = new HashMap<>();
        map.put("hasError", true);
        return new ModelAndView(map, "Login.hbs");
    }

    static ModelAndView saveGame(Request rq) throws Exception {
        String appleScore = rq.queryParams("apples");
        String timeScore = rq.queryParams("times");

        String username = rq.session().attribute("username");
        PlayerInfo pInfo = PlayerInfo.findPlayerInfo(username);

        if (pInfo == null) return logoutAndRestart(rq); // Unexpected status!

        Score.saveScore(appleScore, timeScore, pInfo);

        List<Score> scores = Score.orderedTopScores(pInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("name", pInfo.getFullName());
        map.put("scores", scores);
        return new ModelAndView(map, "Game.hbs");
    }

    static ModelAndView logoutAndRestart(Request rq) {
        rq.session().invalidate();
        Map<String, Object> map = new HashMap<>();
        map.put("hasError", false);
        return new ModelAndView(map, "Login.hbs");
    }
}



