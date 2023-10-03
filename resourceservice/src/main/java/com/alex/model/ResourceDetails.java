package com.alex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceDetails {

    private String name;
    private String artist;
    private String album;
    private String length;
    private Long resourceId;
    private String year;
}