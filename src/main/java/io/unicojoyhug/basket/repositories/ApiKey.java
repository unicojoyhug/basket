package io.unicojoyhug.basket.repositories;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "api_key")
public class ApiKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "key")
    private String key;
}