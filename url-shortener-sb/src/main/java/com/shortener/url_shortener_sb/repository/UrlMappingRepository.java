package com.shortener.url_shortener_sb.repository;

import com.shortener.url_shortener_sb.models.UrlMapping;
import com.shortener.url_shortener_sb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    //Optional<User> findBYUsername(String username);
    UrlMapping findByShortUrl(String shortUrl);
    List<UrlMapping> findByUser(User user);

    @Query("""
  select um
  from UrlMapping um
  join fetch um.user
  where um.user.id = :userId
  order by um.createdDate desc
""")
    List<UrlMapping> findAllByUserIdWithUser(@Param("userId") Long userId);
}
