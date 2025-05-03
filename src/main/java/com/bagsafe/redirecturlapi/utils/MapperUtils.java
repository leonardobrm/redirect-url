package com.bagsafe.redirecturlapi.utils;


import com.bagsafe.redirecturlapi.infra.config.LocalDateTimeAdapter;
import com.bagsafe.redirecturlapi.infra.config.LocalTimeAdapter;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class MapperUtils {

    private MapperUtils() {
    }

    public static <S, T> T map(final S source, final Class<T> classTarget) {
        final var gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
        final var tmp = gson.toJson(source);

        return gson.fromJson(tmp, classTarget);
    }

    public static <S, T> Optional<T> mapOptional(final Optional<S> optionalSource, final Class<T> classTarget) {
        return optionalSource.map(element -> map(element, classTarget));
    }

    public static <S, T> List<T> mapList(final List<S> inputList, final Class<T> classTarget) {
        final var listResponse = new ArrayList<T>(List.of());
        inputList.forEach(item -> listResponse.add(map(item, classTarget)));
        return new ArrayList<>(listResponse);
    }
}