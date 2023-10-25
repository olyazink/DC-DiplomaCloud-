package ru.netology.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.entity.TokenEntity;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, String> {
}
