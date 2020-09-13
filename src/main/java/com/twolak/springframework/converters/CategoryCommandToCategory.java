/**
 * 
 */
package com.twolak.springframework.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.twolak.springframework.commands.CategoryCommand;
import com.twolak.springframework.domain.Category;

import lombok.Synchronized;

/**
 * @author twolak
 *
 */
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand source) {
		if (source == null) {
			return null;
		}
		final Category category = new Category();
		if (StringUtils.isNotBlank(source.getId())) {
			category.setId(source.getId());
		}
		category.setDescription(source.getDescription());
		return category;
	}

}
