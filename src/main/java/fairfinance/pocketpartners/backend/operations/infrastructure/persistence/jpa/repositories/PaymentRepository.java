package fairfinance.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories;

import fairfinance.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByUserId(Long userId);
    List<Payment> findAllByExpenseId(Long expenseId);
    Optional<Payment> findByUserIdAndExpenseId(Long userId, Long expenseId);
}