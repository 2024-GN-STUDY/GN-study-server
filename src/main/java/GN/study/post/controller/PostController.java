package GN.study.post.controller;

import GN.study.post.dto.RequestPostDto;
import GN.study.post.dto.ResponsePostDto;
import GN.study.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ResponsePostDto> createPost(@Valid @RequestBody RequestPostDto requestPostDto){
        return ResponseEntity.ok().body(postService.createPost(requestPostDto));
    }

}
