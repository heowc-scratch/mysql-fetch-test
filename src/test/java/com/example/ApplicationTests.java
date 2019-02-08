package com.example;

import static org.assertj.core.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.util.StopWatch;

@RunWith(Parameterized.class)
public class ApplicationTests {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceBuilder.create()
			.url("jdbc:mysql://localhost:3306/test?useCursorFetch=true&sslMode=DISABLED")
			.username("root")
			.password("root")
			.build());

	private RowMapper<PushMessage> pushMessageRowMapper = (rs, rowNum) -> {
		try {
			final long id = rs.getLong("id");
			final String content = rs.getString("content");
			final String status = rs.getString("status");
			final String createdBy = rs.getString("created_by");
			final LocalDateTime createdAt = rs.getTimestamp("created_at").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
			final LocalDateTime lastModifiedAt = rs.getTimestamp("last_modified_at").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();

			return new PushMessage(id, content, status, createdBy, createdAt, lastModifiedAt);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	};

	private static final String SELECT_ALL_SQL = "SELECT `id`, `content`, `status`, `created_by`, `created_at`, `last_modified_at` FROM push_message";

	private final int fetchSize;

	public ApplicationTests(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

	@BeforeClass
	public static void init() throws InterruptedException {
		Thread.sleep(5_000L);
	}

	@Test
	public void fetch() {
		// given
		StopWatch stopWatch = new StopWatch();

		// when
		stopWatch.start();
		final List<PushMessage> pushMessageList = jdbcTemplate.query(
				con -> con.prepareStatement(SELECT_ALL_SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY),
				ps -> ps.setFetchSize(fetchSize),
				new RowMapperResultSetExtractor<>(pushMessageRowMapper));
		stopWatch.stop();

		// then
		assertThat(pushMessageList).size().isEqualTo(100_000);
		System.out.println(stopWatch.prettyPrint());
	}

	@Parameters
	public static Collection<Integer[]> parameters() {
		List<Integer[]> providers = new ArrayList<>();
//		providers.add(new Integer[] { Integer.MIN_VALUE });
		providers.add(new Integer[] { 5 });
//		providers.add(new Integer[] { 10 });
//		providers.add(new Integer[] { 50 });
//		providers.add(new Integer[] { 100 });
//		providers.add(new Integer[] { 500 });
//		providers.add(new Integer[] { 1000 });
//		providers.add(new Integer[] { 2500 });
//		providers.add(new Integer[] { 5000 });
		return providers;
	}
}
