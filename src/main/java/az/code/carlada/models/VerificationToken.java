package az.code.carlada.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {
    @Id
    private String email;
    private String token;
}
