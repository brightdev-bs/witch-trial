package vanilla.witchtrial.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import static vanilla.witchtrial.global.common.constants.Constants.DEFAULT_PAG;
import static vanilla.witchtrial.global.common.constants.Constants.DEFAULT_PAGE_SIZE;

@Data
public class PagingDto {
    @Range(min = 0)
    private long page;
    @Range(min = 0, max = 10)
    private long size;

    public PagingDto() {
        this.page = DEFAULT_PAG;
        this.size = DEFAULT_PAGE_SIZE;
    }

    public PagingDto(long page, long size) {
        this.page = page;
        this.size = size;
        if(size == 0) this.size = DEFAULT_PAGE_SIZE;
    }

    public long getStartPage(long currentPage) {
        return (currentPage / DEFAULT_PAGE_SIZE) * 10 + 1;
    }

    public long getEndPage(long currentPage, long totalCount) {
        long totalPage = totalCount / DEFAULT_PAGE_SIZE;
        if(totalCount % DEFAULT_PAGE_SIZE > 0)
            totalPage++;

        long endPage = getStartPage(currentPage) + DEFAULT_PAGE_SIZE - 1;

        if(endPage > totalPage) {
            endPage = totalPage;
        }


        return endPage;
    }
}
