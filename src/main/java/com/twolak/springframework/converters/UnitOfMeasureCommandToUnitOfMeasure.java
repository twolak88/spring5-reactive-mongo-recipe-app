/**
 * 
 */
package com.twolak.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.twolak.springframework.commands.UnitOfMeasureCommand;
import com.twolak.springframework.domain.UnitOfMeasure;

import lombok.Synchronized;

/**
 * @author twolak
 *
 */
@Component
public class UnitOfMeasureCommandToUnitOfMeasure
		implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasure convert(UnitOfMeasureCommand source) {
		if (source == null) {
			return null;
		}
		final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(source.getId());
		unitOfMeasure.setDescription(source.getDescription());
		return unitOfMeasure;
	}

}
