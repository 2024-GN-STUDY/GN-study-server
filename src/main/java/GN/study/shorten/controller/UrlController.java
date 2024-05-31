package GN.study.shorten.controller;

import GN.study.shorten.dto.RequestUrl;
import GN.study.shorten.dto.ResponseUrl;
import GN.study.shorten.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/shorted")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/")
    public ResponseEntity<ResponseUrl> createShortenUrl(@Valid @RequestBody RequestUrl requestUrl) {
        try{
            ResponseUrl responseUrl = urlService.createShortenUrl(requestUrl);
            return ResponseEntity.ok().body(responseUrl);
        } catch(NoSuchAlgorithmException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{shortedUrl}")
    public ResponseEntity<ResponseUrl> getShortenUrl(@PathVariable String shortedUrl) {
        try {
            ResponseUrl responseUrl = urlService.searchShortedUrl(shortedUrl);
            return ResponseEntity.ok().body(responseUrl);
        } catch(NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
