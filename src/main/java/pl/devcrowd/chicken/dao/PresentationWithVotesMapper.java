package pl.devcrowd.chicken.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;

import pl.devcrowd.chicken.model.Presentation;

public class PresentationWithVotesMapper extends PresentationMapper {
	@Override
	public Presentation map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Presentation result = super.map(index, r, ctx);

		result.setVotes(r.getInt("votes"));

		return result;
	}
}
