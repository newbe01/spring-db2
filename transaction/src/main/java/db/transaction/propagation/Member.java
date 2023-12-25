package db.transaction.propagation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {

    @GeneratedValue
    @Id
    private Long id;

    private String username;

    public Member() {
    }

    public Member(String username) {
        this.username = username;
    }

}
