package GN.study.post.dto;


import GN.study.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostDto {

    private Long id;

    private String subject;

    private String contents;

    private Long user_id;

}
