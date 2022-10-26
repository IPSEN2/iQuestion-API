package org.spine.iquestionapi.model;


import lombok.*;
import org.spine.iquestionapi.repository.EmailResetTokenRepo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePassword {
    @Id
    private long id;

    private String token;

    private String newPassword;
}
