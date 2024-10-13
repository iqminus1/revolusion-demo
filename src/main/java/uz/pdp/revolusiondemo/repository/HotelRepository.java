package uz.pdp.revolusiondemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.revolusiondemo.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
}