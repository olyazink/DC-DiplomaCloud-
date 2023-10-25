package ru.netology.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {
    @Id
    private String token;


}
