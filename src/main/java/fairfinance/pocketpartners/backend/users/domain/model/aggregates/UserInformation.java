package fairfinance.pocketpartners.backend.users.domain.model.aggregates;

import fairfinance.pocketpartners.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import fairfinance.pocketpartners.backend.users.domain.model.commands.CreateUserInformationCommand;
import fairfinance.pocketpartners.backend.users.domain.model.entities.Role;
import fairfinance.pocketpartners.backend.users.domain.model.valueobjects.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class UserInformation extends AuditableAbstractAggregateRoot<UserInformation> {

    @Getter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserInformation userInformation;

    @NotBlank
    @Embedded
    private PersonName name;

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
    private Photo photo;

    @NotBlank
    @Embedded
    EmailAddress email;


    public UserInformation(String firstName, String lastName, String phoneNumber, String photo, String email) {
        this.name = new PersonName(firstName, lastName);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.photo = new Photo(photo);
        this.email = new EmailAddress(email);
    }

    public UserInformation(CreateUserInformationCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.phoneNumber = new PhoneNumber(command.phoneNumber());
        this.photo = new Photo(command.photo());
        this.email = new EmailAddress(command.email());
    }

    public UserInformation() {
    }



    public void updateName(String firstName, String lastName) {
        this.name = new PersonName(firstName, lastName);
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public void updatePhoto(String photo) {
        this.photo = new Photo(photo);
    }

    public void updateEmail(String email) {
        this.email = new EmailAddress(email);
    }

    //Getters
    public String getFullName() {
        return name.getFullName();
    }

    public String getEmailAddress() {
        return email.email();
    }

    public String getPhoneNumber() {
        return phoneNumber.getPhoneNumber();
    }

    public String getPhoto() {
        return photo.getPhoto();
    }


    // Setter methods
    public void setName(PersonName name) {
        this.name = name;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }
}
