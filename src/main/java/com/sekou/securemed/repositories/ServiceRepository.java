package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {

}
