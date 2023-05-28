package com.codecool.budapestgo.dao.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private byte[] imgData;
    @NonNull private final String title;
    @NonNull private final String description;
    @NonNull private final String articleText;
}
