package bind.iotstudycafe.commons.config;

import bind.iotstudycafe.member.repository.MemberRepositoryV1;
import bind.iotstudycafe.member.repository.MemberRepositoryV2;
import bind.iotstudycafe.member.repository.memory.MemoryMemberRepository;
import bind.iotstudycafe.member.repository.querydsl.MemberQueryRepository;
import bind.iotstudycafe.member.service.MemberService;
import bind.iotstudycafe.member.service.MemberServiceV1;
import bind.iotstudycafe.member.service.MemberServiceV2;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MemberConfig {

    private final EntityManager em;
    private final MemberRepositoryV2 memberRepositoryV2; //SpringDataJPA


//    @Bean
//    public MemberService memberService() {
//        return new MemberServiceV1(memberRepository());
//    }
//    @Bean
//    public MemberRepositoryV1 memberRepository() {
//        return new MemoryMemberRepository();
//    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceV2(memberRepositoryV2, memberQueryRepository() );
    }

    @Bean
    public MemberQueryRepository memberQueryRepository() {
        return new MemberQueryRepository(em);
    }

}
