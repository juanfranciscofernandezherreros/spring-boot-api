package com.example.api.infrastructure;

import com.example.api.domain.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Long> {
}
