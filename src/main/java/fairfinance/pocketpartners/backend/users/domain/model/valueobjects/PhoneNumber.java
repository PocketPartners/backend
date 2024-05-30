package fairfinance.pocketpartners.backend.users.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public record PhoneNumber(
        @NotBlank
        @Size(min = 10, max = 15) // Ajusta el tamaño según tus requisitos
        @Pattern(regexp = "^\\+?[0-9]*$", message = "Phone number must contain only digits and optionally start with a '+'")
        String number
) {
    public PhoneNumber {
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or blank");
        }
        if (!number.matches("^\\+?[0-9]*$")) {
            throw new IllegalArgumentException("Phone number must contain only digits and optionally start with a '+'");
        }
        if (number.length() < 10 || number.length() > 15) {
            throw new IllegalArgumentException("Phone number must be between 10 and 15 characters long");
        }
    }

    // Método de fábrica para crear una instancia con un valor predeterminado si es necesario
    public static PhoneNumber defaultPhoneNumber() {
        return new PhoneNumber("+0000000000");
    }

    public String getPhoneNumber() {
        return number;
    }
}
