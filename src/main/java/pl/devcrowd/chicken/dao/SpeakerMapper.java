package pl.devcrowd.chicken.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import pl.devcrowd.chicken.model.Speaker;

public class SpeakerMapper implements ResultSetMapper<Speaker> {
	@Override
	public Speaker map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Speaker result = new Speaker();

		result.setId(r.getString("id"));
		result.setFirstname(r.getString("name"));
		result.setLastname(r.getString("surname"));
		result.setDescription(r.getString("description"));
		result.setPicture(r.getString("picture"));

		return result;
	}
}
