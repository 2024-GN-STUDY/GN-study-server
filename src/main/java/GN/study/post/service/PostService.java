package GN.study.post.service;

import GN.study.post.dto.RequestPostDto;
import GN.study.post.dto.ResponsePostDto;
import GN.study.post.entity.Post;
import GN.study.post.mapper.PostMapper;
import GN.study.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @Transactional
    public ResponsePostDto createPost(RequestPostDto requestPostDto){

        Post post = postMapper.requestDtoToEntity(requestPostDto);

        return postMapper.entityToResponsePostDto(postRepository.save(post));
    }
}
