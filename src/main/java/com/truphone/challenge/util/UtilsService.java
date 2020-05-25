package com.truphone.challenge.util;

import com.google.common.base.Preconditions;
import com.truphone.challenge.domain.AbstractEntity;
import com.truphone.challenge.dto.AbstractIdentifiableDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UtilsService {

    private static final String ID_INCONSISTENCY_ERROR_MESSAGE = "Id sent in the PATH must be the same as sent in the payload!";
    private static final String ISO_COUNTRY_CODE_IS_NOT_VALID_MESSAGE = "ISO COuntry Code is not valid!";

    private static final Set<String> ISO_COUNTRIES = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3);

    public static <T extends AbstractIdentifiableDto> void checkIdConsistency(Long id, T entity) {
        Preconditions.checkArgument(id == entity.getId(), ID_INCONSISTENCY_ERROR_MESSAGE);
    }

    public static void checkIsoCountryCodeIsValid(String countryCode) {
        Preconditions.checkArgument(ISO_COUNTRIES.contains(countryCode), ISO_COUNTRY_CODE_IS_NOT_VALID_MESSAGE);
    }

    public static <T extends AbstractEntity> void attachEntity(AbstractIdentifiableDto identifiableDto, Function<Long, T> fetchEntityMethod, Consumer<T> setMethod) {
        Optional.ofNullable(identifiableDto)
                .map(AbstractIdentifiableDto::getId)
                .map(fetchEntityMethod)
                .ifPresent(setMethod);
    }
}
