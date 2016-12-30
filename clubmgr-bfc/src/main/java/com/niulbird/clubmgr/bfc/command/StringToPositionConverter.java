package com.niulbird.clubmgr.bfc.command;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.niulbird.clubmgr.db.model.Position;
import com.niulbird.clubmgr.db.service.PositionService;

final public class StringToPositionConverter implements Converter<String, Position> {
	@Autowired
	PositionService positionService;
	
	@Override
	public Position convert(String key) {
		return positionService.findByKey(key);
	}
}