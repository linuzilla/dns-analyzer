package ncu.cc.digger.repositories;

import ncu.cc.digger.entities.CountryRankingEntity;
import ncu.cc.digger.entities.CountryRankingEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRankingRepository extends JpaRepository<CountryRankingEntity, CountryRankingEntityPK> {
    List<CountryRankingEntity> findByEventIdOrderByRankAsc(int eventId);
    List<CountryRankingEntity> findByEventIdOrderByDnssecDescScoreDesc(int eventId);
    List<CountryRankingEntity> findByEventIdOrderByIpv6DescScoreDesc(int eventId);
    List<CountryRankingEntity> findByEventIdOrderByEdnsAscScoreAsc(int eventId);
    List<CountryRankingEntity> findByEventIdOrderByNormalDescInfoDescLowDescMediumDescHighDescUrgentDesc(int eventId);
    List<CountryRankingEntity> findByEventIdOrderByAxfrAscRecursionAscScoreDesc(int eventId);
    List<CountryRankingEntity> findByEventIdOrderByRecursionAscAxfrAscScoreDesc(int eventId);
}
