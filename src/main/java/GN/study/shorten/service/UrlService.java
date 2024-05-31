package GN.study.shorten.service;

import GN.study.shorten.dto.RequestUrl;
import GN.study.shorten.dto.ResponseUrl;
import GN.study.shorten.entity.ShortenUrl;
import GN.study.shorten.repository.UrlRepository;
import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    private final Base62 base62 = Base62.createInstance();

    @Transactional
    public ResponseUrl createShortenUrl(RequestUrl requestUrl) throws NoSuchAlgorithmException {
            String origin_url = requestUrl.getOriginUrl();

            Optional<ShortenUrl> shorted = urlRepository.findByOriginUrl(origin_url);

            // 중복된 URL 이면 반환
            if(shorted.isPresent()) {
                return ResponseUrl.builder()
                        .shortedUrl(shorted.get().getShortenUrl())
                        .build();
            }

            String shortedUrl = generatedShortCode(origin_url);

            ShortenUrl shortenUrl = ShortenUrl.builder()
                    .origin_url(origin_url)
                    .shorten_url("http://localhost:8083/" + shortedUrl)
                    .build();

            ShortenUrl shortenUrlOptional = urlRepository.save(shortenUrl);

            return ResponseUrl.builder()
                    .shortedUrl(shortenUrlOptional.getShortenUrl())
                    .build();
    }

    public ResponseUrl searchShortedUrl(String shortedUrl){

        Optional<ShortenUrl> urlInfo = urlRepository.findByShortenUrl(shortedUrl);

        if(urlInfo.isPresent()) {
            return ResponseUrl.builder()
                    .originUrl(urlInfo.get().getOriginUrl())
                    .build();
        }

        throw new NoSuchElementException("ShortenUrl not found");
    }



    public String generatedShortCode(String origin_url) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = messageDigest.digest(origin_url.getBytes(StandardCharsets.UTF_8));
        byte[] shotedHashedBytes = Arrays.copyOfRange(hashedBytes, 0, 8);

        return new String(base62.encode(shotedHashedBytes), StandardCharsets.UTF_8);
    }
}
