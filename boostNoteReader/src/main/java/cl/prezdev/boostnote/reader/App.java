package cl.prezdev.boostnote.reader;

import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Log
public class App {
    public static void main(String[] args) throws IOException {
        BoostNote boostNote = new BoostNote(new File("boostnote"));

        List<Folder> folders = boostNote.getFolders();

        for(Folder folder : folders){
            System.out.println("----------------------------------");
            System.out.println("FOLDER NAME: "+folder.getName());
            System.out.println("FOLDER KEY: "+folder.getKey());
            System.out.println("FOLDER COLOR: "+folder.getColor());

            for(Note note : folder.getNotes()){
                System.out.println("------------");
                System.out.println("TITLE: "+note.getTitle());
                System.out.println("TAGS: "+note.getTags());
                System.out.println("CREATED AT: "+note.getCreatedAt());
                System.out.println("UPDATED AT: "+note.getUpdatedAt());
                System.out.println("CONTENT:\n"+ note.getContent());
            }
        }
    }
}
