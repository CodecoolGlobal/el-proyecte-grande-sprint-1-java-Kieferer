package com.codecool.budapestgo.dao.model.stop;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Point {
    double latitude, longitude;

}
