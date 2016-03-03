package pl.devcrowd.chicken.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.skife.jdbi.v2.TransactionIsolationLevel;
import org.skife.jdbi.v2.sqlobject.Transaction;

import pl.devcrowd.chicken.configuration.MailConfiguration;
import pl.devcrowd.chicken.dao.PresentationDao;
import pl.devcrowd.chicken.dao.ProposalDao;
import pl.devcrowd.chicken.dao.SpeakerDao;
import pl.devcrowd.chicken.model.Presentation;
import pl.devcrowd.chicken.model.Proposal;
import pl.devcrowd.chicken.model.Speaker;

public class ProposalService {
    private static final String SPEAKER = "Speaker:";
    private static final String TEE_SIZE = "Tee Size: ";
    private static final String ORIGIN = "Origin: ";
    private static final String EMAIL = "Email: ";
    private static final String LASTNAME = "Lastname: ";
    private static final String FIRSTNAME = "Firstname: ";
    private static final String PRESENTATION = "Presentation:";
    private static final String LANG = "Lang: ";
    private static final String DESC = "Desc: ";
    private static final String TITLE = "Title: ";
    private static final String MAIL_NEW_LINE = "<br>";
    private static final String MAIL_ITEM_SEPARATOR = "======================================================";
    private static final String PROPOSAL_COPY_MAIL_SUBJECT = "Proposal Copy";

    @Inject
    private SpeakerDao speakerDao;
    @Inject
    private PresentationDao presentationDao;
    @Inject
    private ProposalDao proposalDao;
    @Inject
    private MailService mailService;

    private String registrationMailTemplate;
    private String registrationMailSubject;
    private String copyAddress;

    @Inject
    public ProposalService(MailConfiguration mailConfiguration) {
        this.registrationMailTemplate = mailConfiguration.getRegistrationMailTemplate();
        this.registrationMailSubject = mailConfiguration.getRegistrationMailSubject();
        this.copyAddress = mailConfiguration.getFromAddress();
    }

    @Transaction
    public Proposal addProposal(Proposal proposal) {
        final String proposalId = UUID.randomUUID().toString();
        List<Speaker> speakers = proposal.getSpeakers().stream().map(this::addSpeaker).collect(Collectors.toList());
        List<Presentation> presentations = proposal.getPresentations().stream()
                .map((p) -> addPresentation(p, speakers, proposalId)).collect(Collectors.toList());

        sendMailToSpeakers(speakers);
        sendCopyInMail(proposal);

        return new Proposal(speakers, presentations);
    }

    @Transaction(TransactionIsolationLevel.REPEATABLE_READ)
    public List<Proposal> getProposals() {
        return proposalDao.getProposalsIds().stream()
                .map(presentationDao::getPresentationsByProposalId)
                .collect(Collectors.toList()).stream()
                .map(p -> new Proposal(speakerDao.getSimpleSpeakersForPresentation(p.get(0).getId()), p))
                .collect(Collectors.toList());
    }

    @Transaction(TransactionIsolationLevel.REPEATABLE_READ)
    public List<Proposal> getFullProposals() {
        return proposalDao.getProposalsIds().stream()
                .map(presentationDao::getPresentationsByProposalId)
                .collect(Collectors.toList()).stream()
                .map(p -> new Proposal(speakerDao.getSpeakersForPresentation(p.get(0).getId()), p))
                .collect(Collectors.toList());
    }

    @Transaction(TransactionIsolationLevel.REPEATABLE_READ)
    public Proposal getRandomProposal() {
        Presentation randomPresentation = presentationDao.getRandomPresentation();
        List<Speaker> speakers = speakerDao.getSimpleSpeakersForPresentation(randomPresentation.getId());

        return new Proposal(speakers, Arrays.asList(randomPresentation));
    }

    @Transaction(TransactionIsolationLevel.REPEATABLE_READ)
    public List<Proposal> getSelectedProposals(int count) {
        return presentationDao.getSelectedPresentations(count).stream()
                .map(p -> new Proposal(speakerDao.getSpeakersForPresentation(p.getId()), Arrays.asList(p)))
                .collect(Collectors.toList());
    }

    @Transaction(TransactionIsolationLevel.REPEATABLE_READ)
    public Proposal getProposalByPropositionId(String id) {
        Presentation presentation = presentationDao.getPresentationById(id);
        List<Speaker> speakers = speakerDao.getSimpleSpeakersForPresentation(presentation.getId());

        return new Proposal(speakers, Arrays.asList(presentation));
    }

    @Transaction(TransactionIsolationLevel.REPEATABLE_READ)
    public Proposal getSimpleProposalByPropositionId(String id) {
        Presentation presentation = presentationDao.getPresentationById(id);
        List<Speaker> speakers = speakerDao.getSimpleWithoutPictureSpeakersForPresentation(presentation.getId());

        return new Proposal(speakers, Arrays.asList(presentation));
    }

    private Speaker addSpeaker(Speaker speaker) {
        speaker.setId(UUID.randomUUID().toString());

        speakerDao.insert(speaker.getId(), speaker.getFirstname(), speaker.getLastname(), speaker.getEmail(),
                speaker.getDescription(), speaker.getPicture(), speaker.getTeeSize().value(), speaker.getOrigin());

        return speaker;
    }

    private Presentation addPresentation(Presentation presentation, List<Speaker> speakers, String proposalId) {
        String id = UUID.randomUUID().toString();
        presentation.setId(id);

        presentationDao.insert(id, presentation.getTitle(), presentation.getDescription(),
                presentation.getLanguage().value());
        speakers.forEach(s -> presentationDao.insertSpeakerRelation(proposalId, id, s.getId()));

        return presentation;
    }

    private void sendMailToSpeakers(List<Speaker> speakers) {
        speakers.forEach(s -> sendRegistrationMail(s.getFirstname(), s.getEmail()));
    }

    private void sendRegistrationMail(String name, String email) {
        mailService.sendMail(email, registrationMailSubject, String.format(registrationMailTemplate, name));
    }

    private void sendCopyInMail(Proposal proposal) {
        StringBuilder mail = new StringBuilder();

        proposal.getSpeakers().forEach(s -> addSpeakerToMailCopy(mail, s));
        proposal.getPresentations().forEach(p -> addPresentationToMailCopy(mail, p));

        mailService.sendMail(copyAddress, PROPOSAL_COPY_MAIL_SUBJECT, mail.toString());
    }

    private void addPresentationToMailCopy(StringBuilder mail, Presentation p) {
        mail.append(PRESENTATION).append(MAIL_NEW_LINE).append(TITLE).append(p.getTitle()).append(MAIL_NEW_LINE)
                .append(DESC).append(p.getDescription()).append(MAIL_NEW_LINE).append(LANG)
                .append(p.getLanguage() != null ? p.getLanguage().value() : "").append(MAIL_NEW_LINE)
                .append(MAIL_ITEM_SEPARATOR).append(MAIL_NEW_LINE);
    }

    private void addSpeakerToMailCopy(StringBuilder mail, Speaker s) {
        mail.append(SPEAKER).append(MAIL_NEW_LINE).append(FIRSTNAME).append(s.getFirstname()).append(MAIL_NEW_LINE)
                .append(LASTNAME).append(s.getLastname()).append(MAIL_NEW_LINE).append(EMAIL).append(s.getEmail())
                .append(MAIL_NEW_LINE).append(DESC).append(s.getDescription()).append(MAIL_NEW_LINE).append(ORIGIN)
                .append(s.getOrigin()).append(MAIL_NEW_LINE).append(TEE_SIZE)
                .append(s.getTeeSize() != null ? s.getTeeSize().value() : "").append(MAIL_NEW_LINE)
                .append(MAIL_ITEM_SEPARATOR).append(MAIL_NEW_LINE);
    }
}
