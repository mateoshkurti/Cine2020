package ca.sheridancollege.shkurtim.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.shkurtim.beans.Cinema;
import ca.sheridancollege.shkurtim.beans.User;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void addUser(String email, String password) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO sec_user " + "(email, encryptedPassword, enabled)"
				+ " values (:email, :encryptedPassword, 1)";
		parameters.addValue("email", email);
		parameters.addValue("encryptedPassword", passwordEncoder.encode(password));
		jdbc.update(query, parameters);
	}

	public void addRole(Long userId, Long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = " INSERT INTO user_role (userId, roleId) " + "VALUES (:userId, :roleId)";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);
	}

	public User findUserAccount(String email) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user where email=:email";
		parameters.addValue("email", email);
		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}

	public List<String> getRolesById(Long userId) {
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select user_role.userId, sec_role.roleName" + " FROM user_role join sec_role "
				+ "WHERE user_role.roleId=sec_role.roleId" + " AND userId=:userId";
		parameters.addValue("userId", userId);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			roles.add((String) row.get("roleName"));
		}
		return roles;
	}

	public void insertCinema(String cinemaName, String cinemaStage, int availableSeats, String movie) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query="insert into Cinema (cinemaName, cinemaStage, availableSeats, movie)"
				+ "VALUES (:cinemaName, :cinemaStage, :availableSeats, :movie)";
		parameters.addValue("cinemaName", cinemaName);
		parameters.addValue("cinemaStage", cinemaStage);
		parameters.addValue("availableSeats", availableSeats);
		parameters.addValue("movie", movie);
		jdbc.update(query, parameters);
	}
	
	public void properlySanitized(String cinemaName, String cinemaStage, int availableSeats, String movie, String sanitized) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query="UPDATE Cinema	"
				+ "SET properlySanitized = :sanitized "
				+ "WHERE cinemaName=:cinemaName";
		parameters.addValue("cinemaName", cinemaName);
		parameters.addValue("cinemaStage", cinemaStage);
		parameters.addValue("availableSeats", availableSeats);
		parameters.addValue("movie", movie);
		parameters.addValue("sanitized", sanitized);
		jdbc.update(query, parameters);
	}
	
	public void markedSeats(String cinemaName, String cinemaStage, int availableSeats, String movie, String markedSeats) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query="UPDATE Cinema	"
				+ "SET markedSeats = :markedSeats "
				+ "WHERE cinemaName=:cinemaName";
		parameters.addValue("cinemaName", cinemaName);
		parameters.addValue("cinemaStage", cinemaStage);
		parameters.addValue("availableSeats", availableSeats);
		parameters.addValue("movie", movie);
		parameters.addValue("markedSeats", markedSeats);
		jdbc.update(query, parameters);
		
	}
	
	public void markedGround(String cinemaName, String cinemaStage, int availableSeats, String movie, String markedGround) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query="UPDATE Cinema	"
				+ "SET markedGround = :markedGround "
				+ "WHERE cinemaName=:cinemaName";
		parameters.addValue("cinemaName", cinemaName);
		parameters.addValue("cinemaStage", cinemaStage);
		parameters.addValue("availableSeats", availableSeats);
		parameters.addValue("movie", movie);
		parameters.addValue("markedGround", markedGround);
		jdbc.update(query, parameters);
		
	}
	
	public void doorSigns(String cinemaName, String cinemaStage, int availableSeats, String movie, String doorSigns) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query="UPDATE Cinema	"
				+ "SET doorSigns = :doorSigns "
				+ "WHERE cinemaName=:cinemaName";
		parameters.addValue("cinemaName", cinemaName);
		parameters.addValue("cinemaStage", cinemaStage);
		parameters.addValue("availableSeats", availableSeats);
		parameters.addValue("movie", movie);
		parameters.addValue("doorSigns", doorSigns);
		jdbc.update(query, parameters);
	}
	
	public List<Cinema> getCinemaList(){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query="Select * from cinema";
		
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Cinema>(Cinema.class));
	}
	
	public void deleteCinema(Long cinemaID) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM Cinema WHERE cinemaID = :cinemaID";
		namedParameters.addValue("cinemaID", cinemaID);
		jdbc.update(query, namedParameters);

	}
}
