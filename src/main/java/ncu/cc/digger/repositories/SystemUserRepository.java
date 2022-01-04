package ncu.cc.digger.repositories;

import ncu.cc.digger.entities.SystemUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemUserRepository extends JpaRepository<SystemUserEntity,String> {
}
