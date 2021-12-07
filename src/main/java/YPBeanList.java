import lombok.Data;

import java.util.List;


@Data
public class YPBeanList {

    private String name;
    private String author;
    private String arrangedBy;
    private String transcribedBy;
    private String permission;
    private Integer bpm;
    private Integer bitsPerPage;
    private Integer pitchLevel;
    private List<List<SongNotesDTO>> songNotes;
}
