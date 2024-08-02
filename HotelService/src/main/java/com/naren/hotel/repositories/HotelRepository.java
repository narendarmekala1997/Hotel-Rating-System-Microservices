package com.naren.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naren.hotel.entites.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String> {

}
