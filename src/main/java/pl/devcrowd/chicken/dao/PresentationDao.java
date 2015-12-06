package pl.devcrowd.chicken.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlCall;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import pl.devcrowd.chicken.model.Presentation;

public interface PresentationDao {
	@SqlUpdate("insert into presentaions values (:id, :title, :description, :votes)")
	void insert(@Bind("id") String id, @Bind("title") String title, @Bind("description") String description, @Bind("votes") int votes);

	@SqlUpdate("insert into speaker_presentation values (:speakerId, :presentationId)")
	void insertSpeakerRelation(@Bind("presentationId") String presentationId, @Bind("speakerId") String speakerId);

	@SqlQuery("select id, title, description, votes from presentations")
	@Mapper(PresentationMapper.class)
	List<Presentation> getPresentations();

	@SqlQuery("select id, title, description, votes from presentations where id = :id")
	@Mapper(PresentationMapper.class)
	Presentation getPresentationById(@Bind("id") String id);

	@SqlQuery("select id, title, description, votes from presentations order by random() limit 1")
	@Mapper(PresentationMapper.class)
	Presentation getRandomPresentation();

	@SqlCall("vote(:id, :vote)")
	void vote(String id, String vote);
}
