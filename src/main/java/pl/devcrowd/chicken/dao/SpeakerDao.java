package pl.devcrowd.chicken.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import pl.devcrowd.chicken.model.Speaker;

public interface SpeakerDao {
	@SqlUpdate("insert into speakers values (:id, :name, :surname, :email, :description, :picture, :teeSize, :origin)")
	void insert(@Bind("id") String id, @Bind("name") String name, @Bind("surname") String surname, @Bind("email") String email,
			@Bind("description") String description, @Bind("picture") String picture, @Bind("teeSize") String teeSize, @Bind("origin") String origin);

	@SqlQuery("select id, name, surname, description, picture from speakers")
	@Mapper(SpeakerMapper.class)
	List<Speaker> getSimpleSpeakers();

	@SqlQuery("select id, name, surname, description, picture from speakers where id = :id")
	@Mapper(SpeakerMapper.class)
	Speaker getSimpleSpeakerById(@Bind("id") String id);

	@SqlQuery("select id, name, surname, description, picture from speakers where " +
			"id = any(select speaker_id from speaker_presentation where presentation_id = :presentationId)")
	@Mapper(SpeakerMapper.class)
	List<Speaker> getSimpleSpeakersForPresentation(@Bind("presentationId") String presentationId);

	@SqlQuery("select id, name, surname, description from speakers where " +
            "id = any(select speaker_id from speaker_presentation where presentation_id = :presentationId)")
    @Mapper(SpeakerWithoutPictureMapper.class)
    List<Speaker> getSimpleWithoutPictureSpeakersForPresentation(@Bind("presentationId") String presentationId);

	@SqlQuery("select id, name, surname, email, description, picture, tee_size, origin from speakers")
	@Mapper(SpeakerWithAllDataMapper.class)
	List<Speaker> getSpeakers();

	@SqlQuery("select id, name, surname, email, description, picture, tee_size, origin from speakers where id = :id")
	@Mapper(SpeakerWithAllDataMapper.class)
	Speaker getSpeakerById(@Bind("id") String id);

	@SqlQuery("select id, name, surname, email, description, picture, tee_size, origin from speakers where " +
			"id = any(select speaker_id from speaker_presentation where presentation_id = :presentationId)")
	@Mapper(SpeakerWithAllDataMapper.class)
	List<Speaker> getSpeakersForPresentation(@Bind("presentationId") String presentationId);
}
