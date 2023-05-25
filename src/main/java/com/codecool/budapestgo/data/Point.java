package com.codecool.budapestgo.data;

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
