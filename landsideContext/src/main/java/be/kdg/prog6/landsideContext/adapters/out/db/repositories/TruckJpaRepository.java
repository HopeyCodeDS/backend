package be.kdg.prog6.landsideContext.adapters.out.db.repositories;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.TruckJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckJpaRepository extends JpaRepository<TruckJpaEntity, String> {
}
