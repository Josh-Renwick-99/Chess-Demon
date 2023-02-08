package com.chessdemon.Chess.Demon.Service;

import com.chessdemon.Chess.Demon.Configuration.ChessDemonConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.sql.*;

@Service
public class DBService {
    ChessDemonConfigurationProperties config;
    public DBService(ChessDemonConfigurationProperties config){
        this.config = config;
    }
    public void newUser(String discordId, Date date, Integer winCount, Integer gamesPlayed) throws SQLException, ClassNotFoundException {
        try {
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = " INSERT INTO user (discordID, lastPlayed, winCount, gamesPlayed) " + " VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, discordId);
            preparedStatement.setDate(2, date);
            preparedStatement.setInt(3, winCount);
            preparedStatement.setInt(4, gamesPlayed);
            preparedStatement.execute();
            conn.close();
        } catch (Exception e){
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void newGame(String discordId, String moveHistory, String winner){
        try {
            String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = " INSERT INTO games (userId, moveHistory, winner, currentFenPosition) VALUES ((SELECT userId FROM user WHERE discordId = ?), ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, discordId);
            preparedStatement.setString(2, moveHistory);
            preparedStatement.setString(3, winner);
            preparedStatement.setString(4, startingFen);
            preparedStatement.execute();
            preparedStatement = conn.prepareStatement("SELECT last_insert_id() FROM games");
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int lastGameId = Integer.valueOf(rs.getString(1));
            sql = "UPDATE user SET activeGameId = ? WHERE discordId = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, lastGameId);
            preparedStatement.setString(2, discordId);
            preparedStatement.execute();
            conn.close();
        } catch (Exception e){
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void setActiveGame(String discordId, Integer gameId){
        try {
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = "UPDATE user SET activeGameId = ? WHERE discordId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, discordId);
            preparedStatement.execute();
            conn.close();
        } catch (Exception e){
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void updateGame(String discordId, String move, String moveHistory){
        try {
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = "UPDATE games SET lastMove = ?, moveHistory = ? WHERE gameId = (SELECT activeGameId FROM user WHERE discordID = ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, move);
            preparedStatement.setString(2, moveHistory);
            preparedStatement.setString(3, discordId);
            preparedStatement.execute();
            conn.close();
        } catch (Exception e){
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
