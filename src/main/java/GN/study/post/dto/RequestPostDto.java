package GN.study.post.dto;

import GN.study.post.entity.Post;
import GN.study.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostDto {

    @NotBlank
    private String subject;

    private String contents;

    private Long userId;

    public Post toEntity(User user){
        return Post.builder()
                .subject(this.getSubject())
                .contents(this.getContents())
                .user(user)
                .build();
    }
}
