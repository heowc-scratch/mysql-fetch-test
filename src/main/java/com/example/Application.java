package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	//	private static final String INSERT_SQL = "INSERT INTO push_message (`content`, `status`, `created_by`, `created_at`, `last_modified_at`) VALUES (?, ?, ?, ?, ?)";

	//	@Autowired
	//	private JdbcTemplate jdbcTemplate;
	//
	//	@Bean
	//	public ApplicationRunner runner() {
	//		return args -> {
	//			int insertCount = 100_000;
	//			List<PushMessage> batchList = IntStream.range(0, insertCount)
	//					.mapToObj(i -> new PushMessage("content" + i, "wait", "heowc", LocalDateTime.now(), LocalDateTime.now()))
	//					.collect(Collectors.toList());
	//
	//			int batchSize = 100;
	//			Lists.partition(batchList, batchSize)
	//					.forEach(list -> {
	//						jdbcTemplate.batchUpdate(INSERT_SQL, list, batchSize, (ps, arg) -> {
	//							ps.setString(1, arg.getContent());
	//							ps.setString(2, arg.getStatus());
	//							ps.setString(3, arg.getCreatedBy());
	//							ps.setTimestamp(4, Timestamp.valueOf(arg.getCreatedAt()));
	//							ps.setTimestamp(5, Timestamp.valueOf(arg.getLastModifiedAt()));
	//						});
	//					});
	//		};
	//	}
}
