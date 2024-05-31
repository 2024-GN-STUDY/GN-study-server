package GN.study.shorten.repository;

import GN.study.shorten.entity.ShortenUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<ShortenUrl, Long> {

    Optional<ShortenUrl> findByShortenUrl(String shortenUrl);

    Optional<ShortenUrl> findByOriginUrl(String originUrl);
}
