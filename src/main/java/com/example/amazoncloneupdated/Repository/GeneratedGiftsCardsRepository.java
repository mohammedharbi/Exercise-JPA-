package com.example.amazoncloneupdated.Repository;

import com.example.amazoncloneupdated.Model.GeneratedGiftsCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratedGiftsCardsRepository extends JpaRepository<GeneratedGiftsCards, Integer> {
}
