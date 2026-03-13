package cl.prezdev.model.response;

import lombok.Data;
import java.util.List;

@Data
public class ListAvlResponse {
    private List<AvlDevice> avls;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}
