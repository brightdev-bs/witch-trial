package vanilla.witchtrial.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class PagingDto {

    @Range(min = 0)
    private int page;
    @Range(min = 0, max = 20)
    private int size;

    public PagingDto() {
    }

    public PagingDto(int page, int size) {
        this.page = page;
        this.size = size;
        if(size == 0) this.size = 10;
    }
}
