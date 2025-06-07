package com.example.meditime.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiReport {
    private String patterns;
    private String alerts;
}
