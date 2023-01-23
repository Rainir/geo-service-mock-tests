package ru.netology.geo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

class GeoServiceTests {
    @Test
    void byCoordinatesTest() {
        GeoService geoService = new GeoServiceImpl();

        assertThrowsExactly(RuntimeException.class, () -> geoService.byCoordinates(1 ,2));
    }

    @MethodSource("source")
    @ParameterizedTest
    void byIpParamTest(String ip, Country country) {
        GeoService geoService = new GeoServiceImpl();

        Country ipCountry = geoService.byIp(ip).getCountry();

        assertEquals(ipCountry, country);
    }

    static Stream<Arguments> source() {
        return Stream.of(Arguments.of("127.0.0.1", null),
                                Arguments.of(GeoServiceImpl.MOSCOW_IP, Country.RUSSIA),
                                Arguments.of(GeoServiceImpl.NEW_YORK_IP, Country.USA),
                                Arguments.of("172.", Country.RUSSIA),
                                Arguments.of("96.", Country.USA));
    }
}
