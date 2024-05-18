package GN.study.post.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostDto {

    private Long postId;

    private String subject;

    private String contents;

    private Long userId;
}
