package ncu.cc.digger.controllers;

import ncu.cc.commons.webdev.annotations.MenuItem;
import ncu.cc.digger.constants.*;
import ncu.cc.digger.entities.CountryRankingEntity;
import ncu.cc.digger.entities.RankingEventEntity;
import ncu.cc.digger.repositories.CountryRankingRepository;
import ncu.cc.digger.repositories.RankingEventRepository;
import ncu.cc.digger.services.CountryCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.function.Function;

@Controller
@RequestMapping(Routes.STATISTICS)
public class RankingController {
    private final RankingEventRepository rankingEventsRepository;
    //    private final CountryRankingRepository countryRankingRepository;
    private final CountryCodeService countryCodeService;
    private final Map<String, Function<Integer, List<CountryRankingEntity>>> functionMap;

    public RankingController(RankingEventRepository rankingEventsRepository, CountryRankingRepository countryRankingRepository, CountryCodeService countryCodeService) {
        this.rankingEventsRepository = rankingEventsRepository;
//        this.countryRankingRepository = countryRankingRepository;
        this.countryCodeService = countryCodeService;

        functionMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<>(Constants.RANKING_SCORE, countryRankingRepository::findByEventIdOrderByRankAsc),
                new AbstractMap.SimpleEntry<>(Constants.RANKING_IPV6, countryRankingRepository::findByEventIdOrderByIpv6DescScoreDesc),
                new AbstractMap.SimpleEntry<>(Constants.RANKING_DNSSEC, countryRankingRepository::findByEventIdOrderByDnssecDescScoreDesc),
                new AbstractMap.SimpleEntry<>(Constants.RANKING_EDNS, countryRankingRepository::findByEventIdOrderByEdnsAscScoreAsc),
                new AbstractMap.SimpleEntry<>(Constants.RANKING_AXFR, countryRankingRepository::findByEventIdOrderByAxfrAscRecursionAscScoreDesc),
                new AbstractMap.SimpleEntry<>(Constants.RANKING_RECURSION, countryRankingRepository::findByEventIdOrderByRecursionAscAxfrAscScoreDesc),
                new AbstractMap.SimpleEntry<>(Constants.RANKING_SEVERITY, countryRankingRepository::findByEventIdOrderByNormalDescInfoDescLowDescMediumDescHighDescUrgentDesc)
        );
    }

    @GetMapping(Routes.STATISTICS_RANKING)
    @MenuItem(value = MenuPaths.STATISTICS_RANKING, order = 3)
    public String ranking() {
        return rankingEventsRepository.findFirstByOrderByIdDesc()
                .map(rankingEventEntity ->
                        Routes.redirect(Routes.STATISTICS, Routes.STATISTICS_RANKING, "/" + rankingEventEntity.getTag()))
                .orElseGet(() ->
                        Routes.redirect(Routes.STATISTICS,Routes.STATISTICS_RANKING, Routes.STATISTICS_RANKING_LIST));
    }

    @GetMapping(Routes.STATISTICS_RANKING + "/{tag}")
    public String rankingOn(@PathVariable("tag") String tag, Model model) {
        if (Routes.STATISTICS_RANKING_LIST.equals("/" + tag)) {
            model.addAttribute(FormVariables.RANKING_EVENTS, rankingEventsRepository.findAllByOrderByIdDesc());
            return Views.STATISTICS_RANKING_LIST;
        } else {
            return Routes.redirect(Routes.STATISTICS, Routes.STATISTICS_RANKING, "/" + tag, "/" + Constants.RANKING_SCORE);
        }
    }

    @GetMapping(Routes.STATISTICS_RANKING + "/{tag}/{order}")
    public String rankingOrderBy(@PathVariable("tag") String tag, @PathVariable("order") String order, Model model) {
        Optional<RankingEventEntity> target = rankingEventsRepository.findByTag(tag);

        if (target.isPresent()) {
            model.addAttribute(FormVariables.RANKING_ORDER, order);
            model.addAttribute(FormVariables.RANKING_TARGET, target.get());
            model.addAttribute(FormVariables.RANKING_EVENTS, rankingEventsRepository.findTop10ByOrderByIdDesc());

            return Views.STATISTICS_RANKING;
        } else {
            return Routes.redirect(Routes.STATISTICS + Routes.STATISTICS_RANKING + Routes.STATISTICS_RANKING_LIST);
        }
    }

    @GetMapping(Routes.STATISTICS_BACKEND + Routes.STATISTICS_BACKEND_RANKING + "/{id}/{order}")
    @ResponseBody
    public List<CountryRankingEntity> rankingForEventOrderBy(@PathVariable("id") Integer id, @PathVariable("order") String orderBy) {
        var entry = functionMap.get(orderBy);

        if (entry != null) {
            var countryRank = entry.apply(id);
            countryRank.forEach(entity -> entity.setCountryName(countryCodeService.getCountryByCode(entity.getCountryCode())));
            return countryRank;
        } else {
            return Collections.emptyList();
        }
    }
}
