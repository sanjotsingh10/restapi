package com.application.notesApplication.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NoteDTO {
    private String title;
    private String content;
}
