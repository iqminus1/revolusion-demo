package uz.pdp.revolusiondemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.revolusiondemo.model.Rate;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {
    Optional<Rate> findByUserIdAndRoomId(Integer id, Integer roomId);
}