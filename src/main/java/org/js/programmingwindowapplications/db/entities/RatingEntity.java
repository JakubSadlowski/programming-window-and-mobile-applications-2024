package org.js.programmingwindowapplications.db.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private AnimalShelterEntity shelter;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    private String comment;

    public RatingEntity() {}

    public RatingEntity(int value, AnimalShelterEntity shelter, String comment) {
        setValue(value);
        this.shelter = shelter;
        this.dateTime = LocalDateTime.now();
        this.comment = comment;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getValue() { return value; }
    public void setValue(int value) {
        if (value < 0 || value > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        this.value = value;
    }

    public AnimalShelterEntity getShelter() { return shelter; }
    public void setShelter(AnimalShelterEntity shelter) { this.shelter = shelter; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
