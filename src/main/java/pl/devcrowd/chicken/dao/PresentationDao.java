package pl.devcrowd.chicken.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlCall;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import pl.devcrowd.chicken.model.Presentation;

public interface PresentationDao {
	@SqlUpdate("insert into presentaions values (:id, :title, :description, :lang)")
	void insert(@Bind("id") String id, @Bind("title") String title, @Bind("description") String description, @Bind("lang") String lang);

	@SqlUpdate("insert into speaker_presentation values (:speakerId, :presentationId)")
	void insertSpeakerRelation(@Bind("presentationId") String presentationId, @Bind("speakerId") String speakerId);

	@SqlQuery("select id, title, description from presentations")
	@Mapper(PresentationMapper.class)
	List<Presentation> getPresentations();

	@SqlQuery("select id, title, description from presentations where id = :id")
	@Mapper(PresentationMapper.class)
	Presentation getPresentationById(@Bind("id") String id);

	@SqlQuery("select id, title, description from presentations order by random() limit 1")
	@Mapper(PresentationMapper.class)
	Presentation getRandomPresentation();

	@SqlQuery("select p.id as id, p.title as title, p.description as description, p.language as language, v.votes as votes from presentations p " +
			"left outer join votes v on p.id = v.presentation_id order by v.votes limit :count")
	@Mapper(PresentationWithVotesMapper.class)
	List<Presentation> getSelectedPresentations(@Bind("count") int count);

	@SqlCall("vote(:id, :vote)")
	void vote(String id, String vote);
}
