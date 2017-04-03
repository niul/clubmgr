package com.niulbird.clubmgr.db.model;

import java.util.Comparator;

public class PlayerFixtureInfoComparator implements Comparator<PlayerFixtureInfo> {

	@Override
	public int compare(PlayerFixtureInfo playerInfo1, PlayerFixtureInfo playerInfo2) {
		int retVal = playerInfo1.getPlayer().getFirstName().compareTo(playerInfo2.getPlayer().getFirstName());
		
		if (retVal == 0) {
			retVal = playerInfo1.getPlayer().getLastName().compareTo(playerInfo2.getPlayer().getLastName());
		}
		return retVal;
	}
}
