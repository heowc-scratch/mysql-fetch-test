package com.example;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PushMessage {

	private Long id;
	private String content;
	private String status;

	private String createdBy;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;

	public PushMessage(String content, String status, String createdBy, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
		this.content = content;
		this.status = status;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;
	}
}