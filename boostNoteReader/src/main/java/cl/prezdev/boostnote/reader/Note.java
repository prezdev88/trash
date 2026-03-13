package cl.prezdev.boostnote.reader;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class Note {
    private Instant createdAt;
    private Instant updatedAt;
    private String type;
    private String folder;
    private String title;
    private List<String> tags;
    private String content;
    private List<String> linesHighlighted;
    private Boolean isStarred;
    private Boolean isTrashed;
}
