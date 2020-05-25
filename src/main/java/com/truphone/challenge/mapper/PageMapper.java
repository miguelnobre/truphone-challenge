package com.truphone.challenge.mapper;

import com.truphone.challenge.dto.PageDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper
public interface PageMapper {

    default <T, R> PageDto<T> toDto(Page<R> page, Function<R, T> mapper) {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setOffset(page.getNumber());
        pageDto.setLimit(page.getSize());
        pageDto.setHasNext(page.hasNext());

        pageDto.setElements(page.get()
                .map(mapper)
                .collect(Collectors.toList()));

        return pageDto;
    }
}
