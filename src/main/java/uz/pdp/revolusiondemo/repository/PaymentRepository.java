package uz.pdp.revolusiondemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.revolusiondemo.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}