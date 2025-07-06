package com.ecom_server.server.repository;

import com.ecom_server.server.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {
    List<Address> findByUserId(String userId);
}
