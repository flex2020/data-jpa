package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // MemberRepository는 인터페이스로 생성해야함
}