package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberRepository;
    @Autowired MemberRepository memberRepository2;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        // DB에 member 저장하고, saveMember로 가져옴
        Member saveMember = memberRepository.save(member);

        // DB에 저장한 member의 Id를 통해 해당 member를 찾아서 findMember로 가져옴
        Member findMember = memberRepository.find(saveMember.getId());

        // findMember의 id와 member의 id가 같은지 테스트
        assertThat(findMember.getId()).isEqualTo(member.getId());

        // findMember의 userName과 member의 userName이 같은지 테스트
        assertThat(findMember.getUserName()).isEqualTo(member.getUserName());

        // findMember와 member가 같은지 테스트
        // JPA는 내가 save한 데이터(member)와 찾은 데이터(findMember)가 같다는 보장을 해줌 (1차 캐시)
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //findMember1.setUserName("member123123123"); // 변경 감지 = 더티 체킹, update 역할 수행

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
        // 카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository2.findByUserNameAndAgeGreaterThan("AAA", 15);
        assertThat(result.get(0).getUserName()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }
}