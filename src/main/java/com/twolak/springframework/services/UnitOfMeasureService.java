package com.twolak.springframework.services;

import java.util.Set;

import com.twolak.springframework.commands.UnitOfMeasureCommand;

/**
 * @author twolak
 *
 */
public interface UnitOfMeasureService {
	Set<UnitOfMeasureCommand> findAll();
}
