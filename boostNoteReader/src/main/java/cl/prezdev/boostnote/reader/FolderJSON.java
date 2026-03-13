package cl.prezdev.boostnote.reader;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FolderJSON {
    private List<Folder> folders;
    private String version;

    public FolderJSON(){
        this.folders = new ArrayList<>();
    }

    public void addNote(Note note) {
        for(Folder folder : folders){
            if(folder.getKey().equals(note.getFolder())){
                folder.addNote(note);
                break;
            }
        }
    }
}
