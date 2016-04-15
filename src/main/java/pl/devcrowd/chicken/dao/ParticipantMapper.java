package pl.devcrowd.chicken.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import pl.devcrowd.chicken.model.Participant;
import pl.devcrowd.chicken.model.Participant.Meal;
import pl.devcrowd.chicken.model.TeeSize;

public class ParticipantMapper implements ResultSetMapper<Participant> {
	@Override
	public Participant map(int index, ResultSet r, StatementContext ctx) throws SQLException {
	    Participant result = new Participant();

		result.setId(r.getString("id"));
		result.setFirstname(r.getString("name"));
        result.setLastname(r.getString("surname"));
        result.setEmail(r.getString("email"));
        result.setOrigin(r.getString("origin"));
        result.setTeeSize(TeeSize.valueOf(r.getString("tee_size")));
        result.setAttended(r.getBoolean("attended"));
        result.setVoted(r.getBoolean("voted"));
        result.setConfirmed(r.getBoolean("confirmed"));
        String meal = r.getString("meal");

        if (meal != null) {
            result.setMeal(Meal.valueOf(meal));
        }

		return result;
	}
}
