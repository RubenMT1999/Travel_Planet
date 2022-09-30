package com.example.booking.repository;

import com.example.booking.models.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroHotelRepository extends JpaRepository<Hotel, Integer> {






}
