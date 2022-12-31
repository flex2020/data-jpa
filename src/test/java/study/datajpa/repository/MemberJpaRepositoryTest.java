package study.datajpa.repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        // DB에 member 저장하고, saveMember로 가져옴
        Member saveMember = memberJpaRepository.save(member);

        // DB에 저장한 member의 Id를 통해 해당 member를 찾아서 findMember로 가져옴
        Member findMember = memberJpaRepository.find(saveMember.getId());

        // findMember의 id와 member의 id가 같은지 테스트
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());

        // findMember의 userName과 member의 userName이 같은지 테스트
        Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());

        // findMember와 member가 같은지 테스트
        // JPA는 내가 save한 데이터(member)와 찾은 데이터(findMember)가 같다는 보장을 해줌 (1차 캐시)
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}