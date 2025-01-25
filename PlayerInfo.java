import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerInfo {
    private Integer id;
    private String fullName;
    private String username;
    private String userPw;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    static PlayerInfo findPlayerInfo(String username) throws SQLException {
        try (Connection connection = Main.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from user where login = ?"
            )) {
                preparedStatement.setString(1, username);
                // autoClose of Connection and ResultSet used by try
                try (ResultSet res = preparedStatement.executeQuery()) {
                    if (res.next()) {
                        PlayerInfo playerInfo = new PlayerInfo();
                        playerInfo.fullName = res.getString("fullname");
                        playerInfo.username = res.getString("login");
                        playerInfo.id = res.getInt("id");
                        playerInfo.userPw = res.getString("password");
                        return playerInfo;
                    }
                }
            }
        }

        return null;
    }

}
