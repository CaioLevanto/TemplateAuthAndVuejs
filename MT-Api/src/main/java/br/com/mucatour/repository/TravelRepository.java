package br.com.mucatour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mucatour.model.travel.entity.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
    @Query("select tr from Travel as tr, User as us where tr.id = us.id and us.id = :userId and tr.status in (2, 3, 4) and tr.startDate >= :startDate and tr.backDate <= :backDate")
    Boolean existsTravelByUser(@Param("userId") Long userId, @Param("startDate") Long startDate,
            @Param("backDate") Long backDate);
}
