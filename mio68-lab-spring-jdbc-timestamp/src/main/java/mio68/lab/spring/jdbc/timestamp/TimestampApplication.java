package mio68.lab.spring.jdbc.timestamp;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@SpringBootApplication
public class TimestampApplication {

    public static final String YYYY_MM_DD_HH_24_MI_SS = "YYYY-MM-DD HH24:MI:SS";

    public static void main(String[] args) {
        SpringApplication.run(TimestampApplication.class, args);
    }

    @Component
    static class DemoRunner implements ApplicationRunner {

        private final DataSource dataSource;
        private final JdbcTemplate jdbcTemplate;

        DemoRunner(DataSource dataSource, JdbcTemplate jdbcTemplate) {
            this.dataSource = dataSource;
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            prepareDb();
            withPureJdbc();
            withJdbcTemplate();
        }

        private void withJdbcTemplate() throws SQLException {

            // Use LocalDateTime with JdbcTemplate to avoid current time zone dependency when storing to DataBase
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {

                LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 0, 0 , 0);
                System.out.println("Local date time: " + localDateTime);

                TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tomsk"));
                jdbcTemplate.update("insert into tm(ts) values(?)", localDateTime);
                printTsToChar(statement);
                getAsLocalDateTimeWithTemplateAndPrint();
                clearTm(statement);

                TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
                jdbcTemplate.update("insert into tm(ts) values(?)", localDateTime);
                printTsToChar(statement);
                TimeZone.setDefault(TimeZone.getTimeZone("America/Hawaii"));
                getAsLocalDateTimeWithTemplateAndPrint();
                clearTm(statement);
            }

        }

        private void withPureJdbc() throws SQLException {
            // Initial time zone
            TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));

            try(Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                PreparedStatement preparedStatement = connection.prepareStatement("insert into tm(ts) values(?)")) {

                // initial clear
                clearTm(statement);

                System.out.println("Result of preparedStatement.setTimestamp(1, timestamp) depends on current time zone.");
                Timestamp timestamp = Timestamp.valueOf("2023-01-01 00:00:00"); // Assumes current time zone!!!
                TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
                preparedStatement.setTimestamp(1, timestamp);
                preparedStatement.executeUpdate();
                printTsToChar(statement);
                getAsLocalDateTimeWithTemplateAndPrint();
                clearTm(statement);

                // Change time zone
                TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tomsk"));

                preparedStatement.setTimestamp(1, timestamp);
                preparedStatement.executeUpdate();
                printTsToChar(statement);
                getAsLocalDateTimeWithTemplateAndPrint();
                clearTm(statement);

                System.out.println("Avoid dependency from current time zone with preparedStatement.setTimestamp(1, timestamp, calendar)");
                timestamp = Timestamp.from(Instant.parse("2023-01-01T00:00:00Z")); // On UTC.
                Calendar utcCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

                TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
                preparedStatement.setTimestamp(1, timestamp, utcCalendar);
                preparedStatement.executeUpdate();
                printTsToChar(statement);
                getAsLocalDateTimeWithTemplateAndPrint();
                clearTm(statement);

                // Change time zone
                TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tomsk"));
                preparedStatement.setTimestamp(1, timestamp, utcCalendar);
                preparedStatement.executeUpdate();
                printTsToChar(statement);
                getAsLocalDateTimeWithTemplateAndPrint();
                clearTm(statement);


                // OffsetDateTime doesn't depend on current time zone but adds some offset to result
                System.out.println("OffsetDateTime doesn't depend on current time zone but adds some offest to result. preparedStatement.setObject(1, offsetDateTime)");
                OffsetDateTime offsetDateTime = OffsetDateTime.of(
                        LocalDateTime.of(2023, 1, 1, 0, 0 , 0),
                        ZoneOffset.UTC); // don't add any offset to current offset

                System.out.println("Offset date time: " + offsetDateTime);

                // Zoned date time isn't supported by JDBC
//				ZonedDateTime zonedDateTime = ZonedDateTime.of(
//					LocalDateTime.of(2023, 1, 1, 0, 0 , 0),
//					ZoneId.of("UTC"));
//				System.out.println("Zoned date time: " + zonedDateTime);

                TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
                preparedStatement.setObject(1, offsetDateTime);
                preparedStatement.executeUpdate();
                printTsToChar(statement);
                getAsLocalDateTimeWithTemplateAndPrint();
                clearTm(statement);

                TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tomsk"));
                preparedStatement.setObject(1, offsetDateTime);
                preparedStatement.executeUpdate();
                printTsToChar(statement);
                getAsLocalDateTimeWithTemplateAndPrint();
                clearTm(statement);

                System.out.println("Avoid dependency from current time zone with preparedStatement.setObject(1, localDateTime)");
                LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 0, 0 , 0);
                System.out.println("Local date time: " + localDateTime);

                TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
                preparedStatement.setObject(1, localDateTime);
                preparedStatement.executeUpdate();
                printTsToChar(statement);
                getAsLocalDateTimeWithTemplateAndPrint();
                clearTm(statement);

                TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tomsk"));
                preparedStatement.setObject(1, localDateTime);
                preparedStatement.executeUpdate();
                printTsToChar(statement);
                getAsLocalDateTimeWithTemplateAndPrint();
                clearTm(statement);
            }

        }

        private static void printTsToChar(Statement statement) throws SQLException {
            String ts = getTsToChar(statement);
            System.out.printf("Get timestamp with TO_CHAR ts: %s current time zone: %s%n", ts, TimeZone.getDefault().getID());
        }

        private static String getTsToChar(Statement statement) throws SQLException {
            ResultSet resultSet = statement
                    .executeQuery("select to_char(ts,'" + YYYY_MM_DD_HH_24_MI_SS + "') from tm");
            resultSet.next();
            String res = resultSet.getString(1);
            resultSet.close();
            return res;
        }

        private static void clearTm(Statement statement) throws SQLException {
            statement.executeUpdate("delete from tm");
        }

        private void getAsLocalDateTimeWithTemplateAndPrint() {
            LocalDateTime localDateTime = jdbcTemplate.queryForObject("select ts from tm", LocalDateTime.class);
            System.out.println("Get as local date time: " + localDateTime);
        }

        private void prepareDb() {
            jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS tm (
                        ts timestamp,
                        tstz timestamptz)
                    """);
        }

    }


}
