package com.naren.hotel.services;

import java.util.List;

import com.naren.hotel.entites.Hotel;

public interface HotelService {
	
	Hotel create(Hotel hotel);
	
	List<Hotel> getAll();
	
	Hotel get(String hotelId);

}
