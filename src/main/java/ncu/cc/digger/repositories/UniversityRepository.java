package ncu.cc.digger.repositories;

import ncu.cc.digger.entities.UniversitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<UniversitiesEntity,String> {
}
