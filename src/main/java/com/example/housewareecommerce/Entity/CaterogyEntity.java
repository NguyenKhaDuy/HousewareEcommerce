package com.example.housewareecommerce.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "category")
public class CaterogyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "namecaterogy")
    private String nameCaterogy;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Long status;
    @Column(name = "created")
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCaterogy() {
        return nameCaterogy;
    }

    public void setNameCaterogy(String nameCaterogy) {
        this.nameCaterogy = nameCaterogy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
