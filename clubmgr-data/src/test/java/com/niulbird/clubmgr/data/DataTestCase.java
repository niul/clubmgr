package com.niulbird.clubmgr.data;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.repository.ClubRepository;
import com.niulbird.clubmgr.db.repository.SeasonRepository;
import com.niulbird.clubmgr.db.repository.TeamRepository;
import com.niulbird.clubmgr.db.repository.TeamSeasonMapRepository;

@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
@ActiveProfiles("test")
@Transactional
public abstract class DataTestCase {
    @Autowired
    protected ClubRepository clubRepository;
    @Autowired
    protected TeamRepository teamRepository;
    @Autowired
    protected SeasonRepository seasonRepository;
    @Autowired
    protected TeamSeasonMapRepository teamSeasonMapRepository;

    @BeforeEach
    public void setupData() {
        Club club = clubRepository.findByClubKey("BOMBASTIC");
        if (club == null) {
            club = new Club();
            club.setClubKey("BOMBASTIC");
            club.setName("Bombastic FC");
            club.setDomain("bombasticfc.com");
            club.setUuid(UUID.randomUUID());
            club = clubRepository.save(club);
        }

        Season winter2024 = createSeason("Winter 2024/2025", "WINTER_2024");
        Season summer2024 = createSeason("Summer 2024", "SUMMER_2024");
        Season winter2025 = createSeason("Winter 2025/2026", "WINTER_2025");

        createTeamSeasonMap(club, "Mens A", "BOMBASTIC_MENS_A", winter2024, "VMSL", "http://vmslsoccer.com/division-1/", "http://vmslsoccer.com/division-1/");
        createTeamSeasonMap(club, "Mens A", "BOMBASTIC_MENS_A", winter2025, "VMSL", "http://vmslsoccer.com/premier-division/", "http://vmslsoccer.com/premier-division/");
        createTeamSeasonMap(club, "Mens B", "BOMBASTIC_MENS_B", winter2024, "VMSL", "http://vmslsoccer.com/division-2/", "http://vmslsoccer.com/division-2/");
        createTeamSeasonMap(club, "Mens Jurassic", "BOMBASTIC_MENS_JURASSIC", winter2024, "VMSL", "http://vmslsoccer.com/masters-over-40s-division-1/", "http://vmslsoccer.com/masters-over-40s-division-1/");
        createTeamSeasonMap(club, "Womens A", "BOMBASTIC_WOMENS_A", winter2024, "MWSL", "https://mwsl.com/webapps/spappz_live/team_info?reg_year=2025&id=3120", "https://mwsl.com/webapps/spappz_live/team_info?reg_year=2025&id=3120");
        createTeamSeasonMap(club, "Mens Masters", "BOMBASTIC_MENS_MASTERS", winter2024, "BCMSL", "http://bcmsl.com/division-1/", "http://bcmsl.com/division-1/");
        createTeamSeasonMap(club, "Mens RRSL", "BOMBASTIC_MENS_RRSL", summer2024, "RRSL", "http://rrsl.com/", "http://rrsl.com/");
        createTeamSeasonMap(club, "Mens CESL", "BOMBASTIC_MENS_CESL", summer2024, "CESL", "http://maxusis.com/cesl/", "http://maxusis.com/cesl/schedule.aspx");
    }

    private Season createSeason(String name, String key) {
        Season season = seasonRepository.findBySeasonKey(key);
        if (season == null) {
            season = new Season();
            season.setName(name);
            season.setSeasonKey(key);
            season = seasonRepository.save(season);
        }
        return season;
    }

    private void createTeamSeasonMap(Club club, String teamName, String teamKey, Season season, String dataKey, String standingsUri, String fixturesUri) {
        Team team = teamRepository.findByTeamKey(teamKey);
        if (team == null) {
            team = new Team();
            team.setName(teamName);
            team.setTeamKey(teamKey);
            team.setClub(club);
            team.setUuid(UUID.randomUUID());
            team = teamRepository.save(team);
        }

        TeamSeasonMap map = teamSeasonMapRepository.findByTeamTeamKeyAndSeasonSeasonKey(teamKey, season.getSeasonKey());
        if (map == null) {
            map = new TeamSeasonMap();
            map.setTeam(team);
            map.setSeason(season);
            map.setDataKey(dataKey);
            map.setStandingsUri(standingsUri);
            map.setFixturesUri(fixturesUri);
            map.setScheduled(true);
            map.setDescription(teamName + " - " + season.getName());
            map.setCreated(new Date());
            map.setNameRegex("Bombastic");
            teamSeasonMapRepository.save(map);
        }
    }
}
