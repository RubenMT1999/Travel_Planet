package com.example.booking.repository;


import com.example.booking.models.Hotel;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface PageRepository extends PagingAndSortingRepository<Hotel,Long> {
}
