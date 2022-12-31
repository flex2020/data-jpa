package study.datajpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // 팀은 여러명의 member를 가지므로 1 ... 0 관계, mappedBy는 fk가 없는쪽에 걸어두는게 좋음
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
