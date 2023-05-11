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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "client_seq",
            sequenceName = "client_seq",
            allocationSize = 1
    )
    private final Long id;
    @Enumerated(EnumType.STRING)
    @NonNull private final ClientCategoryType type;
    @NonNull private String email;
    @NonNull private String password;
    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
