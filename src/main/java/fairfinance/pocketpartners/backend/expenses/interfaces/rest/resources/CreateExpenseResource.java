package fairfinance.pocketpartners.backend.expenses.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateExpenseResource(@NotNull
                                    String name,
                                    @NotNull
                                    BigDecimal amount,
                                    @NotNull
                                    Long userId) {
}
