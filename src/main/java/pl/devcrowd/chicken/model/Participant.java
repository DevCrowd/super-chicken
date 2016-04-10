package pl.devcrowd.chicken.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class Participant {
    private String id;
    @NotBlank
    @Size(max=30)
    private String firstname;
    @NotBlank
    @Size(max=30)
    private String lastname;
    @NotBlank
    @Email
    @Size(max=50)
    private String email;
    @Size(max=500)
    private TeeSize teeSize;
    @Size(max=100)
    private String origin;
    private boolean voted;
    private boolean attended;
    private boolean confirmed;
    private Meal meal;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public TeeSize getTeeSize() {
        return teeSize;
    }

    public void setTeeSize(TeeSize teeSize) {
        this.teeSize = teeSize;
    }

    public boolean hasVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public boolean hasAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public static enum Meal {
        NORMAL("NORMAL"),
        VEG("VEG"),
        NONE("NONE");
        private String value;
        private Meal(String value) {
            this.value = value;
        }
        public String value() {
            return value;
        }
    }
}
