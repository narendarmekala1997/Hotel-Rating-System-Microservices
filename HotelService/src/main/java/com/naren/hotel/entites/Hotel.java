package com.naren.hotel.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name="hotels")
public class Hotel {
	
	@Id
	private String id;
	private String name;
	private String location;
	private String about;

	
}
