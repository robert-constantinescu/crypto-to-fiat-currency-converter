package com.cryptocurrencies.converter.repository;

import com.cryptocurrencies.converter.model.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Long> {

    List<Cryptocurrency> findAll();
}
