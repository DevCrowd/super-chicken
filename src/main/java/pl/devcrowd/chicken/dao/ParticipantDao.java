package pl.devcrowd.chicken.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import pl.devcrowd.chicken.model.Participant;

public interface ParticipantDao {
    @SqlUpdate("insert into participants values (:id, :name, :surname, :email, :voted, :attended, :teeSize, :origin)")
    void insert(@Bind("id") String id, @Bind("name") String name, @Bind("surname") String surname, @Bind("email") String email,
            @Bind("teeSize") String teeSize, @Bind("origin") String origin, @Bind("voted") boolean voted, @Bind("attended") boolean attended);

	@SqlQuery("select id, name, surname, email, tee_size, origin, attended, voted, confirmed, meal from participants order by surname")
	@Mapper(ParticipantMapper.class)
	List<Participant> getParticipants();

	@SqlQuery("select id, name, surname, email, tee_size, origin, attended, voted, confirmed, meal from participants where id = :id")
    @Mapper(ParticipantMapper.class)
    Participant getParticipant(@Bind("id") String id);

	@SqlQuery("select voted from participants where id = :id")
	boolean hasParticipantVoted(@Bind("id") String id);

	@SqlQuery("select attended from participants where id = :id")
    boolean hasParticipantAttended(@Bind("id") String id);

	@SqlUpdate("update participants set voted = 't' where id = :id")
    void setVoted(@Bind("id") String id);

	@SqlUpdate("update participants set attended = 't' where id = :id")
    void setAttended(@Bind("id") String id);

	@SqlUpdate("update participants set confirmed = 't', meal = :meal where id = :id")
    void setConfirmed(@Bind("id") String id, @Bind("meal") String meal);
}
