package com.example.fastpick.domain.performance.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="performances")
public class Performance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(nullable = false)
	private String title;

	@Column
	private String venue;

	@Column
	private String description;

	@Column(nullable = false)
	private int price;

	private Performance(Category category, String title, String venue, String description, int price) {
		this.category = category;
		this.title = title;
		this.venue = venue;
		this.description = description;
		this.price = price;
	}

	// 정적 팩토리 메서드
	public static Performance create(Category category, String title, String venue, String description, int price) {
		return new Performance(category, title, venue, description, price);
	}

}
