package andreapascarella.u5d10project.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "travels")
public class Travel {

    @Id
    @GeneratedValue
    private UUID travelId;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDate travelDate;

    private boolean isCompleted;

    public Travel(String destination, LocalDate travelDate) {
        this.destination = destination;
        this.travelDate = travelDate;
        isCompleted = travelDate.isBefore(LocalDate.now());
    }
}
