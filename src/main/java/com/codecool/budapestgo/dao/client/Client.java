package com.codecool.budapestgo.dao.client;

import com.codecool.budapestgo.data.ClientCategoryType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private final Integer id;
    @Enumerated(EnumType.STRING)
    @NonNull private final ClientCategoryType type;
    @NonNull private String email;
    @NonNull private String password;
    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
