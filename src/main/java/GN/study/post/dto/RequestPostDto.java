package GN.study.post.dto;

import GN.study.post.entity.Post;
import GN.study.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostDto {

    @NotBlank
    private String subject;

    private String contents;

    @NotBlank
    private Long user_id;


    public Post toEntity(User user){
        return Post.builder()
                .subject(this.getSubject())
                .contents(this.getContents())
                .user(user)
                .build();
    }
}
