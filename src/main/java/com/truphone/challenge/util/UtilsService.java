package com.truphone.challenge.util;

import com.google.common.base.Preconditions;
import com.truphone.challenge.dto.AbstractIdentifiableDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UtilsService {

    private static final String ID_INCONSISTENCY_ERROR_MESSAGE = "Id sent in the PATH must be the same as sent in the payload!";

    public static <T extends AbstractIdentifiableDto> void checkIdConsistency(Long id, T entity) {
        Preconditions.checkArgument(id == entity.getId(), ID_INCONSISTENCY_ERROR_MESSAGE);
    }
}
