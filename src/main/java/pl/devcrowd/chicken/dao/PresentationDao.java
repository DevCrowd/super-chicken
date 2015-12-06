package pl.devcrowd.chicken.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlCall;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface PresentationDao {
	@SqlUpdate("insert into presentaion values (:id, :title, :description, :votes)")
	void insert(@Bind("id") String id, @Bind("title") String title, @Bind("description") String description, @Bind("votes") int votes);

	@SqlUpdate("insert into presentation_speaker_relation values (:presentationId, :speakerId)")
	void insertSpeakerRelation(@Bind("presentationId") String presentationId, @Bind("speakerId") String speakerId);

	@SqlCall("vote(:id, :vote)")
	void vote(long id, String vote);
}
