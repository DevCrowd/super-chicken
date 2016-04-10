package pl.devcrowd.chicken.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import pl.devcrowd.chicken.configuration.SuperChickenConfiguration;
import pl.devcrowd.chicken.dao.ParticipantDao;
import pl.devcrowd.chicken.dao.PresentationDao;
import pl.devcrowd.chicken.model.Participant;
import pl.devcrowd.chicken.model.Participant.Meal;

public class ParticipantService {
    private static final int SINGLE_VOTE = 1;
    @Inject
    private PresentationDao dao;
    @Inject
    private ParticipantDao participantDao;
    @Inject
    private MailService mailService;
    @Inject
    private SuperChickenConfiguration configuration;

    public Participant addParticipant(Participant participant) {
        String id = UUID.randomUUID().toString();

        participantDao.insert(id, participant.getFirstname(), participant.getLastname(), participant.getEmail(), participant.getTeeSize().value(),
                participant.getOrigin(), participant.hasVoted(), participant.hasAttended());

        participant.setId(id);

        sendRegistrationMail(participant.getFirstname(), participant.getEmail());

        return participant;
    }

    public void vote(String id, List<String> presentationsIds) {
        if (presentationsIds == null || presentationsIds.size() > configuration.getMaxVotes()) {
            throw new IllegalArgumentException("Wrong number of votes");
        }

        Participant participant = participantDao.getParticipant(id);
        if (participant == null) {
            throw new IllegalStateException("Participant must exist");
        }

        if (participant.hasVoted()) {
            throw new IllegalStateException("Participant has already voted");
        }

        presentationsIds.forEach(p -> dao.vote(p, SINGLE_VOTE));

        participantDao.setVoted(id);
    }

    public List<Participant> getParticipants() {
        return participantDao.getParticipants();
    }

    public void setAttended(String id) {
        participantDao.setAttended(id);
    }

    public void confirm(String id, Meal meal) {
        participantDao.setConfirmed(id, meal.value());
    }

    public void requestConfirmation() {
        participantDao.getParticipants().forEach(p -> sendConfirmationMail(p.getId(), p.getFirstname(), p.getEmail()));
    }

    private void sendRegistrationMail(String name, String email) {
        mailService.sendMail(email, configuration.getMail().getParticipantRegistrationMailSubject(),
                String.format(configuration.getMail().getParticipantRegistrationMailTemplate(), name),
                configuration.getMail().getParticipantRegistrationFromAddress());
    }

    private void sendConfirmationMail(String id, String name, String email) {
        mailService.sendMail(email, configuration.getMail().getParticipantConfirmationMailSubject(),
                String.format(configuration.getMail().getParticipantConfirmationMailTemplate(), name, id),
                configuration.getMail().getParticipantRegistrationFromAddress());
    }
}
