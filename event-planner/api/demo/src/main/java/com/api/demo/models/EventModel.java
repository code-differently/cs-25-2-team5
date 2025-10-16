package com.api.demo.models;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class EventModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	 public void setId(Long test_ID) {
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }

	private String title;
	 public void setTitle(String test_TITLE) {
		throw new UnsupportedOperationException("Unimplemented method 'setTitle'");
	}

	private String description;
	 public void setDescription(String test_DESCRIPTION) {
        throw new UnsupportedOperationException("Unimplemented method 'setDescription'");
    }

	private LocalDateTime startTime;
	 public void setStartTime(LocalDateTime test_START_TIME) {
		throw new UnsupportedOperationException("Unimplemented method 'setStartTime'");
	}
}

