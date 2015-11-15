package pl.devcrowd.chicken.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface SpeakerDao {
	@SqlUpdate("insert into speaker (id, name, surname, photo) values (:id, :name, :surname, :description, :photo)")
	void insert(@Bind("id") String id, @Bind("name") String name, @Bind("surname") String surname, @Bind("description") String description, @Bind("photo") byte[] photo);
}
