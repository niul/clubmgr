package com.niulbird.clubmgr.db.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.dto.PlayerStatisticDTO;


@Service
public class StatsServiceImpl implements StatsService {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public List<PlayerStatisticDTO> getTeamSeasonStats(String teamKey, String seasonKey) {
		List<PlayerStatisticDTO> playerStatisticList = new ArrayList<PlayerStatisticDTO>(); 
		Query query = em.createQuery(
		        "SELECT	P.firstName, "
		        		+ "P.lastName, "
		        		+ "COUNT(CASE WHEN PFI.started IS TRUE THEN 1 END), "
		        		+ "COUNT(CASE WHEN PFI.substitute IS TRUE THEN 1 END), "
		        		+ "COUNT(CASE WHEN PFI.yellowCard IS TRUE THEN 1 END), "
		        		+ "COUNT(CASE WHEN PFI.redCard IS TRUE THEN 1 END), "
		        		+ "SUM(CASE WHEN PFI.assists > 0 THEN PFI.assists ELSE 0 END), "
		        		+ "SUM(CASE WHEN PFI.goals > 0 THEN PFI.goals ELSE 0 END) "
						+ "FROM	PlayerFixtureInfo PFI "
						+ "JOIN PFI.player P "
						+ "JOIN PFI.fixture F "
						+ "JOIN F.team T "
						+ "JOIN F.season S "
						+ "WHERE S.seasonKey = :seasonKey "
						+ "AND T.teamKey = :teamKey "
						+ "GROUP BY P.firstName, P.lastName "
						+ "ORDER BY P.firstName, P.lastName");
		
		@SuppressWarnings("rawtypes")
		List l = query.setParameter("seasonKey", seasonKey).setParameter("teamKey", teamKey).getResultList();
		
		for (Object o : l) {
			PlayerStatisticDTO playerStatisticDTO = getPlayerStatisticDTO(o);
			if (playerStatisticDTO != null) {
				playerStatisticList.add(playerStatisticDTO);
			}
		}
		return playerStatisticList;
	}
	
	private PlayerStatisticDTO getPlayerStatisticDTO(Object result) {
		if (result instanceof Object[]) {
			PlayerStatisticDTO playerStatisticDTO = new PlayerStatisticDTO();
			Object[] row = (Object[]) result;
			
			playerStatisticDTO.setFirstName((String) row[0]);
			playerStatisticDTO.setLastName((String) row[1]);
			playerStatisticDTO.setStarted(((Long)row[2]).intValue());
			playerStatisticDTO.setSubstitute(((Long)row[3]).intValue());
			playerStatisticDTO.setYellowCards(((Long)row[4]).intValue());
			playerStatisticDTO.setRedCards(((Long)row[5]).intValue());
			playerStatisticDTO.setAssists(((Long)row[6]).intValue());
			playerStatisticDTO.setGoals(((Long)row[7]).intValue());
			
			return playerStatisticDTO; 
		} else {
			return null;
		}
	}

}