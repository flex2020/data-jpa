package study.datajpa.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자
@ToString(of = {"id", "username", "age"}) // 연관관계인 필드인 team의 경우는 무한루프가 발생할 수 있으므로, ToString하지 않기
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String userName;
    private int age;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")  // fk 설정
    // Member - Team 이 0 ... 1 관계이므로 ManyToOne
    // ManyToOne은 항상 fetch를 lazy로 세팅해야함(지연로딩: 하나의 Entity를 조회할땐 그 Entity만 조회하는 것)
    private Team team;


    public Member(String userName) {
        this.userName = userName;
    }

    public Member(String userName, int age, Team team) {
        this.userName = userName;
        this.age = age;
        if(team != null) {
            changeTeam(team);
        }
    }


    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
