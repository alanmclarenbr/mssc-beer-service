package guru.springframework.msscbeerservice.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;

@Component
public class DateMapper {
    public OffsetDateTime asOffsetDateTime(Timestamp ts) {
        if (ts != null) {
            return OffsetDateTime.of(ts.toLocalDateTime().getYear(),
                    ts.toLocalDateTime().getMonthValue(),
                    ts.toLocalDateTime().getDayOfMonth(),
                    ts.toLocalDateTime().getHour(),
                    ts.toLocalDateTime().getMinute(),
                    ts.toLocalDateTime().getSecond(),
                    ts.toLocalDateTime().getNano(),
                    UTC);
        } else {
            return null;
        }
    }

    public Timestamp asTimestamp(OffsetDateTime offsetDateTime) {
        if (offsetDateTime != null) {
            return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(UTC).toLocalDateTime());
        } else {
            return null;
        }
    }
}
