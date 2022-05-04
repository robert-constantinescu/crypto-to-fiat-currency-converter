package com.cryptocurrencies.converter.repository;

import com.cryptocurrencies.converter.model.mongo.CryptocurrencyMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptocurrencyMongoRepository extends MongoRepository<CryptocurrencyMongo, String> {
}
