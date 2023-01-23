package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.entity.Country.*;

import java.util.stream.Stream;

class LocalizationServiceTests {

    @ParameterizedTest
    @MethodSource("source")
    void localeTest(Country country, String text) {
        LocalizationService ls = new LocalizationServiceImpl();
        String greeting = ls.locale(country);
        assertEquals(greeting, text);
    }

    static Stream<Arguments> source() {
        return Stream.of(Arguments.of(RUSSIA, "Добро пожаловать"),
                         Arguments.of(USA, "Welcome"),
                         Arguments.of(BRAZIL, "Welcome"),
                         Arguments.of(GERMANY, "Welcome"));
    }

}
