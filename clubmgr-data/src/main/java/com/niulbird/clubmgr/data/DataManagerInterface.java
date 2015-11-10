package com.niulbird.clubmgr.data;

import java.util.List;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;

public interface DataManagerInterface {

	public List<Fixture> updateFixtures();
	public List<Standing> updateStandings();
}
