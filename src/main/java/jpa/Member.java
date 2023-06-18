package jpa;



import javax.persistence.*;

@Entity
@Table(name="MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Member() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Member(String name) {
        this.name = name;
    }
}
