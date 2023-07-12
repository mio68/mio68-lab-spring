### How to store timestamps in DB using JDBC directly or JdbcTemplate

java.sql.Timestamp is just time **instant** that stores milliseconds from 1970-01-01 00:00 UTC.
It's not a date-time!

SQL TIMESTAMP (WITHOUT TIMEZONE) is **date-tame** not an instant.
To store java.sql.Timestamp to SQL TIMESTAMP a time offset or time zone must be used.
Use preparedStatement.setTimestamp(1, timestamp, utcCalendar);

It's more useful just to use java.time.LocalDateTime with Jdbc or jdbcTemplate

```
preparedStatement.setObject(1, localDateTime);
preparedStatement.executeUpdate();

jdbcTemplate.update("insert into tm(ts) values(?)", localDateTime);
localDateTime = jdbcTemplate.queryForObject("select ts from tm", LocalDateTime.class);
```

Spring data JDBC supports java.util.Date, java.time.LocalDate, java.time.LocalDateTime, and java.time.LocalTime
