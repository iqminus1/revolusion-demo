package uz.pdp.revolusiondemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.revolusiondemo.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}