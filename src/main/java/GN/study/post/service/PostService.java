package GN.study.post.service;

import GN.study.post.dto.RequestPostDto;
import GN.study.post.dto.ResponsePostDto;
import GN.study.post.entity.Post;
import GN.study.post.repository.PostRepository;
import GN.study.user.entity.User;
import GN.study.user.exception.UserNotFoundException;
import GN.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public ResponsePostDto createPost(RequestPostDto requestPostDto){

        User user = userRepository.findById(requestPostDto.getUser_id())
                .orElseThrow(() -> new UserNotFoundException("USER NOT FOUND"));

        Post post = postRepository.save(requestPostDto.toEntity(user));

        return ResponsePostDto.builder()
                .id(post.getId())
                .subject(post.getSubject())
                .contents(post.getContents())
                .user_id(post.getUser().getId())
                .build();
    }
}
