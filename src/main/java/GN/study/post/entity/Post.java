package GN.study.post.entity;

import GN.study.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subject;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(Long id, String subject, String contents, User user) {
        this.id = id;
        this.subject = subject;
        this.contents = contents;
        this.user = user;
    }
}
