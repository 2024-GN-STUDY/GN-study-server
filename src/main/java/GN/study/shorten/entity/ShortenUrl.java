package GN.study.shorten.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ShortenUrl {

    @Id
    @GeneratedValue
    private Long id;

    // 원본 URL
    private String originUrl;

    // 변환된 URL
    private String shortenUrl;

    @Builder
    public ShortenUrl(String origin_url, String shorten_url) {
        this.originUrl = origin_url;
        this.shortenUrl = shorten_url;
    }
}
