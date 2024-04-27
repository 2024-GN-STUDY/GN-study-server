package GN.study.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Post {

    @Id
    @Column(name = "post_id")
    private Long id;
}
