package uk.gov.dwp.users.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.LocationFactory;
import uk.gov.dwp.users.exception.LocationBadRequestException;

import java.util.Optional;

@Configuration
@Slf4j
public class StringToLocationConverterConfig implements Converter<String, Location> {

    @Override
    public Location convert(String source) {
        Optional<Location> location = LocationFactory.fromName(source);

        if (location.isEmpty()) {
            log.error("Error trying to convert {} to Location", source);
            throw new LocationBadRequestException("Location not supported: " + source);
        }

        return location.get();
    }
}
