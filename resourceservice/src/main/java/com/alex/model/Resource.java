package com.alex.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@Entity
@NoArgsConstructor
@Table(name = "resource")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resource {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Lob
    private Blob data;

    private String fileName;
}
