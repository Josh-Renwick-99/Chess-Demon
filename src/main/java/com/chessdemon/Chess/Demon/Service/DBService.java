package com.chessdemon.Chess.Demon.Service;

import com.chessdemon.Chess.Demon.Configuration.ChessDemonConfigurationProperties;
import com.chessdemon.Chess.Demon.Model.Crud.GameCrud;
import com.chessdemon.Chess.Demon.Model.GameView;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBService {
    ChessDemonConfigurationProperties config;

    public DBService(ChessDemonConfigurationProperties config) {
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
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void newGame(String discordId, String moveHistory, String winner) {
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
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void newGame(String discordId) {
        try {
            String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = " INSERT INTO games (userId, currentFenPosition) VALUES ((SELECT userId FROM user WHERE discordId = ?), ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, discordId);
            preparedStatement.setString(2, startingFen);
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
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void setActiveGame(String discordId, Integer gameId) {
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
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void removeActiveGame(String discordId) {
        try {
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = "UPDATE user SET activeGameId = null WHERE discordId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, discordId);
            preparedStatement.execute();
            conn.close();
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void updateGame(String discordId, String move, String moveHistory, String currentFenPosition) {
        try {
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = "UPDATE games SET lastMove = ?, moveHistory = ?, currentFenPosition = ? WHERE gameId = (SELECT activeGameId FROM user WHERE discordID = ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, move);
            preparedStatement.setString(2, moveHistory);
            preparedStatement.setString(3, discordId);
            preparedStatement.setString(4, currentFenPosition);
            preparedStatement.execute();
            conn.close();
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void checkMateGame(String discordId, String move, String moveHistory, String currentFenPosition, String turn) {
        try {
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = "UPDATE games SET lastMove = ?, moveHistory = ?, currentFenPosition = ?, turn = ?, mated = 1, active = 0 WHERE gameId = (SELECT activeGameId FROM user WHERE discordID = ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, move);
            preparedStatement.setString(2, moveHistory);
            preparedStatement.setString(3, discordId);
            preparedStatement.setString(4, currentFenPosition);
            preparedStatement.setString(5, turn);
            preparedStatement.execute();
            conn.close();
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void updatePosition(String discordId, String currentFen, String san){
        try {
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = "UPDATE games SET currentFenPosition = ?, san = ? WHERE gameId = (SELECT activeGameId FROM user WHERE discordID = ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, currentFen);
            preparedStatement.setString(2, san);
            preparedStatement.setString(3, discordId);
            preparedStatement.execute();
            conn.close();
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public List<GameView> findGames(String discordId) {
        try {
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = "SELECT gameId, currentFenPosition, san, datePlayed FROM games WHERE userId = (SELECT userId FROM user WHERE discordId = ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            List<GameView> games = new ArrayList<>();
            preparedStatement.setString(1, discordId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                games.add(new GameView(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4)));
            }
            conn.close();
            return games;
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
    }

    public GameCrud findActiveGame(String discordId) {
        try {
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());

            String sql = "SELECT * FROM games WHERE gameId = (SELECT activeGameId FROM user WHERE discordId = ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, discordId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                GameCrud gameCrud = new GameCrud(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                conn.close();
                return gameCrud;
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
    }

    public boolean userExist(String discordId){
        try {
            Connection conn = DriverManager.getConnection(config.getDatabaseSource(), config.getDbUser(), config.getDbPass());
            Class.forName(config.getDatabaseDriver());
            String sql = "SELECT * FROM user WHERE discordId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, discordId);
            ResultSet rs = preparedStatement.executeQuery();
            boolean exists = rs.isBeforeFirst();
            conn.close();
            return !exists;
        } catch (Exception e) {
            System.out.println("Exception on method");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }
}
