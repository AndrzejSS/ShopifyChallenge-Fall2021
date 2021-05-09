package com.Images.Image.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Image {
    private String name;
    private String username;
    private boolean isPublic;
    @Lob
    private byte[] imageBytes;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "id"))
    @Column(name = "value")
    private List<String> tags = new ArrayList<String>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Image() {
        super();
    }

    public Image(String name, boolean isPublic, byte[] bytes, List<String> tags) {
        this.name = name;
        this.isPublic = isPublic;
        this.imageBytes = bytes;
        this.tags = tags;
    }
}
