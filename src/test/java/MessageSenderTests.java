import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class MessageSenderTests {

    @MethodSource("source")
    @ParameterizedTest
    void sendByMockito(Country country, String ip, String countryHello, String exception) {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.anyString())).thenReturn(new Location(null, country, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(country)).thenReturn(countryHello);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String test = messageSender.send(headers);
        assertEquals(exception, test);
    }

    static Stream<Arguments> source() {
        return Stream.of(Arguments.of(Country.RUSSIA, "172.0.32.11", "Добро пожаловать", "Добро пожаловать"),
                         Arguments.of(Country.USA, "96.44.183.149", "Welcome", "Welcome"),
                         Arguments.of(Country.GERMANY, "96.", "Welcome", "Welcome"),
                         Arguments.of(Country.BRAZIL, "96.", "Welcome", "Welcome"));
    }
}
