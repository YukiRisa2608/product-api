package ra.module05api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import ra.module05api.dto.PageDto;
import java.util.List;
import java.util.stream.Collectors;

public class PageConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <T, D> PageDto convertPageToPageDto(Page<T> entityPage, Class<D> dtoClass) {
        List<D> dtoData = entityPage.getContent().stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList());

        return PageDto.builder()
                .data(dtoData)
                .hasNext(entityPage.hasNext())
                .hasPrev(entityPage.hasPrevious())
                .pages(entityPage.getTotalPages())
                .totalElements(entityPage.getTotalElements())
                .number(entityPage.getNumber())
                .size(entityPage.getSize())
                .sort(entityPage.getSort())
                .build();
    }
}

