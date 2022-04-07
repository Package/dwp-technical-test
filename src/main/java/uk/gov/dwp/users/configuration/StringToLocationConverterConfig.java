package uk.gov.dwp.users.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.exception.LocationBadRequestException;

import java.util.Locale;

@Configuration
@Slf4j
public class StringToLocationConverterConfig implements Converter<String, Location> {

    @Override
    public Location convert(String source) {
        try {
            return Location.valueOf(source.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            log.error("Error trying to convert {} to Location", source);
            throw new LocationBadRequestException("Location Not Supported: " + source);
        }
    }
}
