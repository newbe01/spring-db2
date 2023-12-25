package db.transaction.propagation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Log {

    @GeneratedValue
    @Id
    private Long id;

    private String message;

    public Log() {
    }

    public Log(String message) {
        this.message = message;
    }

}
