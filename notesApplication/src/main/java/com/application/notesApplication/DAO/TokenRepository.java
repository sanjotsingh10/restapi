package com.application.notesApplication.DAO;

import com.application.notesApplication.Model.Token;
import com.mongodb.client.MongoIterable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, Integer> {

    @Query("{ 'user.id': ?0, 'expired': false, 'revoked': false }")
    List<Token> findActiveTokensByUserId(String userId);
    Optional<Token> findByToken(String token);
}
