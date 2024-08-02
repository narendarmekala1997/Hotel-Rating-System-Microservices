package com.naren.hotel.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naren.hotel.entites.Hotel;
import com.naren.hotel.exceptions.ResourceNotFoundException;
import com.naren.hotel.repositories.HotelRepository;


@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	@Override
	public Hotel create(Hotel hotel) {

		String randomId = UUID.randomUUID().toString();
		hotel.setId(randomId);
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel get(String hotelId) {
		return hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundException("Hotel with give id not found"));
	}

}
