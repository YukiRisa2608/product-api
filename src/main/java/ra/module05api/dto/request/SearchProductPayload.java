package ra.module05api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductPayload {

    private String keyword = "";

    private Integer page = 0;

    private Long categoryId = null;

    private String sortBy = null;

}
