package pl.devcrowd.chicken.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import pl.devcrowd.chicken.model.Presentation;

public class PresentationMapper implements ResultSetMapper<Presentation> {
	@Override
	public Presentation map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Presentation result = new Presentation();

		result.setId(r.getString("id"));
		result.setTitle(r.getString("title"));
		result.setDescription(r.getString("description"));

		return result;
	}
}
