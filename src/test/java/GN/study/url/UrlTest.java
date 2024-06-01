package GN.study.url;

import GN.study.shorten.dto.RequestUrl;
import GN.study.shorten.dto.ResponseUrl;
import GN.study.shorten.entity.ShortenUrl;
import GN.study.shorten.repository.UrlRepository;
import GN.study.shorten.service.UrlService;
import io.seruco.encoding.base62.Base62;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)  // JUnit 5 부터 ExtendWith 사용
public class UrlTest {

    @Autowired
    private UrlService urlService;

    private final Base62 base62 = Base62.createInstance();

    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void encodeTest() throws Exception{
        // given
        String url = "www.google.com";

        // when
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        byte[] hashed =  messageDigest.digest(url.getBytes(StandardCharsets.UTF_8));

        byte[] shortedHashed = Arrays.copyOfRange(hashed, 0, 8);

        // then
        System.out.println(new String(base62.encode(shortedHashed), StandardCharsets.UTF_8));
    }

    @Test
    public void shortedUrlServiceTest() throws Exception{

        // given
        RequestUrl requestUrl = RequestUrl.builder()
                .originUrl("https://www.google.com")
                .build();
        // when
        ResponseUrl responseUrl = urlService.createShortenUrl(requestUrl);


        Optional<ShortenUrl> shortenUrl = urlRepository.findByShortenUrl(responseUrl.getShortedUrl());

        ShortenUrl shortendUrl = null;

        if(shortenUrl.isEmpty()){
            throw new RuntimeException("Not found shorten url");
        } else{
            shortendUrl = shortenUrl.get();
        }

        String generatedUrl = urlService.generatedShortCode(requestUrl.getOriginUrl());

        // then
        Assertions.assertEquals(responseUrl.getShortedUrl(), shortendUrl.getShortenUrl());
        Assertions.assertEquals(requestUrl.getOriginUrl(), shortendUrl.getOriginUrl());
        Assertions.assertEquals(shortendUrl.getShortenUrl(), generatedUrl);
    }

    @Test
    public void shortedUrlTest() throws Exception{

        // given
        RequestUrl requestUrl = RequestUrl.builder()
                .originUrl("https://www.google.com")
                .build();

        // when
        ResponseUrl responseUrl = urlService.createShortenUrl(requestUrl);

        List<ShortenUrl> s = urlRepository.findAll();

        ShortenUrl shortenUrl = urlRepository.findById(1L).get();
        ShortenUrl shortenUrl1 = urlRepository.findByShortenUrl(responseUrl.getShortedUrl()).orElseThrow(RuntimeException::new);

        // then
        Assertions.assertEquals(shortenUrl.getShortenUrl(), shortenUrl1.getShortenUrl());
    }
    @Test
    public void shortedTest3000000() throws Exception{

        for(int i=1; i<3000000; i++){

            // given
            RequestUrl requestUrl = RequestUrl.builder()
                    .originUrl("https://www.google.com/"+i)
                    .build();

            // when
            ResponseUrl responseUrl = urlService.createShortenUrl(requestUrl);

            ShortenUrl shortenUrl = urlRepository.findById((long)i).get();
            ShortenUrl shortenUrl1 = urlRepository.findByShortenUrl(responseUrl.getShortedUrl()).orElseThrow(RuntimeException::new);

            // then
            System.out.print("i = " + i);
            Assertions.assertEquals(shortenUrl.getShortenUrl(), shortenUrl1.getShortenUrl());
        }
    }


}
