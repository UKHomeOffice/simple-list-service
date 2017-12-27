package uk.gov.digital.ho.hocs.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "houses")
@Access(AccessType.FIELD)
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public class House {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @Getter
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="house_id", referencedColumnName = "id")
    @Getter
    private Set<Member> members = new HashSet<>();

    @Column(name = "deleted", nullable = false)
    @Getter
    @Setter
    private Boolean deleted;

    public House(String name, Set<Member> members) {
        this.name = name;
        this.members = members;
        this.deleted = false;
    }
}