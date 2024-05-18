package GN.study.post.mapper;

import GN.study.post.dto.RequestPostDto;
import GN.study.post.dto.ResponsePostDto;
import GN.study.post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post requestDtoToEntity(RequestPostDto requestPostDto);

    ResponsePostDto entityToResponsePostDto(Post post);
}
