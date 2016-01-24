package pl.devcrowd.chicken.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface ProposalDao {
	@SqlQuery("select distinct proposal_id from speaker_presentation")
	List<String> getProposalsIds();
}
