package org.spine.iquestionapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.spine.iquestionapi.service.EntityIdResolver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A questionnaire is a set of questions to be answered by a caregiver, created by a Spine user
 */
@Getter
@Setter
@Entity
@Table(name = "questionnaire")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
   generator = ObjectIdGenerators.PropertyGenerator.class,
   property = "id",
   resolver = EntityIdResolver.class,
   scope=Questionnaire.class
   )
public class Questionnaire {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();
    /**
     * The name of the questionnaire
     */
    @NotNull
    private String name;
    /**
     * The questions in the questionnaire
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Segment> segments = new ArrayList<>();
    
}
