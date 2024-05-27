package GN.study.post.controller;

import GN.study.config.JwtUtil;
import GN.study.post.dto.RequestPostDto;
import GN.study.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody RequestPostDto requestPostDto, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        }

        String token = authorizationHeader.substring(7); // "Bearer " 부분을 제거

        requestPostDto.setUserId(jwtUtil.extractUserId(token));

        return ResponseEntity.ok().body(postService.createPost(requestPostDto));
    }



}
