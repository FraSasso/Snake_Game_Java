import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Score {
    private double time;
    private int apples;

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getApples() {
        return apples;
    }

    public void setApples(int apples) {
        this.apples = apples;
    }

    static void saveScore(String appleScore, String timeScore, PlayerInfo pInfo) throws SQLException {
        try (Connection connection = Main.getConnection()) {
            String sql = "insert into games (apples,time,ownerid) values(?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, appleScore);
                preparedStatement.setString(2, timeScore);
                preparedStatement.setInt(3, pInfo.getId());
                preparedStatement.execute();
            }
        }
    }

    static List<Score> orderedTopScores(PlayerInfo pInfo) throws SQLException {
        try (Connection connection = Main.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from games where ownerid = ? order by apples desc, time asc limit 5");
            preparedStatement.setInt(1, pInfo.getId());
            try (ResultSet res = preparedStatement.executeQuery()) {
                ArrayList<Score> list = new ArrayList<>();
                while (res.next()) {
                    Score s = new Score();
                    s.setTime(res.getDouble("time"));
                    s.setApples(res.getInt("apples"));
                    list.add(s);
                }
                return list;
            }
        }
    }

}
