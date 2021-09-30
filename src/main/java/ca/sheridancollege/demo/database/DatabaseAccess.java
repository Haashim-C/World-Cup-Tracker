package ca.sheridancollege.demo.database;

import ca.sheridancollege.demo.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseAccess {

    @Autowired
    NamedParameterJdbcTemplate jdbc;

    public void insertTeam(Team team){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO team(teamName, teamContinent, gamesPlayed, gamesWon, gamesDrawn, gamesLost) VALUES(:names, :continents, :played, :won, :drawn, :lost) ";
        namedParameters.addValue("names", team.getTeamName());
        namedParameters.addValue("continents", team.getTeamContinent());
        namedParameters.addValue("played", team.getGamesPlayed());
        namedParameters.addValue("won", team.getGamesWon());
        namedParameters.addValue("drawn", team.getGamesDrawn());
        namedParameters.addValue("lost", team.getGamesLost());

        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0)
            System.out.println("Team record was added successfully");
    }

    public  void deleteTeamById(Long id){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM team WHERE teamId = :id";
        namedParameters.addValue("id", id);
        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0)
            System.out.println("Team record was deleted successfully");
    }

    public void editTeamById(Team team){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "UPDATE team SET teamName =:name, teamContinent =:continent,gamesPlayed =:playedGame,gamesWon =:wonGame,gamesDrawn =:drawnGame,gamesLost =:lostGame WHERE teamId = :id ";
        namedParameters.addValue("id",team.getTeamId());
        namedParameters.addValue("name", team.getTeamName());
        namedParameters.addValue("continent", team.getTeamContinent());
        namedParameters.addValue("playedGame", team.getGamesPlayed());
        namedParameters.addValue("wonGame", team.getGamesWon());
        namedParameters.addValue("drawnGame", team.getGamesDrawn());
        namedParameters.addValue("lostGame", team.getGamesLost());

        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0)
            System.out.println("Team record was updated successfully");
    }

    public List<Team> getTeamById(Long id){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM team WHERE teamId = :id";
        namedParameters.addValue("id" , id);
        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));

    }

    public List<Team> getTeams(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM team";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));
    }

    public List<Team> getSortTeams(String methodSort){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query;
        if(methodSort.equals("points")){
            query = "SELECT * FROM team ORDER BY (gamesWon * 3 + gamesDrawn) DESC";
        }
        else if (methodSort.equals("name")){
            query = "SELECT * FROM team ORDER BY teamName ASC";
        }
        else if (methodSort.equals("continent")){
            query = "SELECT * FROM team ORDER BY teamContinent ASC";
        }
        else query = "SELECT * FROM team";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));
    }

    public List<Team> getSearchTeam(String search){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM team WHERE teamName LIKE :search OR teamContinent LIKE :search";
        namedParameters.addValue("search","%" + search + "%");
        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));
    }
}
