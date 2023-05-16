package com.codecool.budapestgo.dao.model;

import com.codecool.budapestgo.dao.types.ClientCategoryType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    @Enumerated(EnumType.STRING)
    @NonNull private final ClientCategoryType type;
    @NonNull
    @Column(unique = true)
    private final String email;
    @NonNull
    private String password;
    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
