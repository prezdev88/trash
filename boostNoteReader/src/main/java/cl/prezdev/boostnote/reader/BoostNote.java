package cl.prezdev.boostnote.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.java.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
@Log
public class BoostNote {
    private File root;
    private File boostnoteJsonFile;
    private File notesDirectory;
    private FolderJSON folderJSON;
    private List<Note> notes;

    public BoostNote(File root) throws IOException {
        this.root = root;
        this.boostnoteJsonFile = new File(root.getAbsoluteFile() + "/boostnote.json");
        this.notesDirectory = new File(root.getAbsoluteFile() + "/notes");

        log.info("file root: "+root.getAbsolutePath());
        log.info("boostnote JSON File: "+boostnoteJsonFile.getAbsolutePath());

        loadFolder();
        loadNotes();
        loadNotesInFolders();
    }

    private void loadNotesInFolders() {
        for(Note note : notes){
            folderJSON.addNote(note);
        }
    }


    private void loadNotes() throws FileNotFoundException {
        File[] fileNotes = notesDirectory.listFiles();

        notes = new ArrayList<>();
        for(File fileNote : fileNotes){
            Note note = loadNote(fileNote);
            notes.add(note);
        }
    }

    private Note loadNote(File fileNote) throws FileNotFoundException {
        List<String> tags               = new ArrayList<>();
        String content                  = "";
        List<String> linesHighlighted   = new ArrayList<>();

        Boolean processingTags              = false;
        Boolean processingContent           = false;
        Boolean processingLinesHighlighted  = false;

        Note note = new Note();

        Scanner scanner = new Scanner(fileNote);

        String line;
        while(scanner.hasNext()){
            line = scanner.nextLine();

            if(processingContent){
                if(line.contains("'''")){
                    processingContent = false;
                    note.setContent(content);

                    continue;
                }

                content += line.trim()+"\n";
                continue;
            }else if(processingTags){
                // TODO: pensar en el caso cuando el tag contiene []
                if(line.contains("]")){
                    processingTags = false;
                    note.setTags(tags);

                    continue;
                }

                tags.add(line.replace("\"", "").trim());
                continue;
            }else if(processingLinesHighlighted){
                if(line.contains("]")){
                    processingLinesHighlighted = false;
                    note.setLinesHighlighted(linesHighlighted);

                    continue;
                }

                linesHighlighted.add(line.replace("\"", ""));
                continue;
            }

            if(line.contains(":")){
                if(line.contains("createdAt") || line.contains("updatedAt")){
                    Instant instant = getInstant(line);

                    if(line.contains("createdAt") ){
                        note.setCreatedAt(instant);
                    }else if(line.contains("updatedAt")){
                        note.setUpdatedAt(instant);
                    }
                    continue;
                }

                String[] split = line.split(":");

                String key      = split[0];
                String value    = split[1].replace("\"", "").trim();

                switch (key){
                    case "type":
                        note.setType(value);
                        break;
                    case "folder":
                        note.setFolder(value);
                        break;
                    case "title":
                        note.setTitle(value);
                        break;
                    case "isStarred":
                        note.setIsStarred(Boolean.parseBoolean(value));
                        break;
                    case "isTrashed":
                        note.setIsTrashed(Boolean.parseBoolean(value));
                        break;
                }

                if(value.contains("'''")){
                    processingContent = true;
                }else if(value.contains("[")){
                    if(key.contains("tags")){
                        if(!value.contains("[]")){
                            processingTags = true;
                        }
                    }else if(key.contains("linesHighlighted")){
                        if(!value.contains("[]")){
                            processingLinesHighlighted = true;
                        }
                    }
                }
            }
        }

        return note;
    }

    private Instant getInstant(String line) {
        int i = line.indexOf(":") + 1;

        String dateString = line.substring(i).replace("\"", "").trim();

        return Instant.parse(dateString);
    }

    private void loadFolder() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        folderJSON = mapper.readValue(boostnoteJsonFile, FolderJSON.class);
    }

    public List<Folder> getFolders() {
        return folderJSON.getFolders();
    }
}
