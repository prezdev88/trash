package cl.prezdev.boostnote.reader;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Folder {
    private String key;
    private String color;
    private String name;
    private List<Note> notes;

    public Folder(){
        this.notes = new ArrayList<>();
    }

    public void addNote(Note note){
        this.notes.add(note);
    }
}
