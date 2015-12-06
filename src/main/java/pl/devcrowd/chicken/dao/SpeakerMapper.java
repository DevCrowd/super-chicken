package pl.devcrowd.chicken.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import pl.devcrowd.chicken.model.Speaker;
import pl.devcrowd.chicken.model.Speaker.TeeSize;

public class SpeakerMapper implements ResultSetMapper<Speaker> {

	@Override
	public Speaker map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Speaker result = new Speaker();

		result.setId(r.getString("id"));
		result.setFirstname(r.getString("name"));
		result.setLastname(r.getString("surname"));
		result.setEmail(r.getString("email"));
		result.setDescription(r.getString("description"));
		result.setOrigin(r.getString("origin"));
		result.setPicture(r.getString("picture"));
		result.setTeeSize(TeeSize.valueOf(r.getString("tee_size")));

		return result;
	}

}
