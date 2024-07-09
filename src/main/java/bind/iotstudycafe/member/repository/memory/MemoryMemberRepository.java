package bind.iotstudycafe.member.repository.memory;

import bind.iotstudycafe.member.domain.MemberGrade;
import bind.iotstudycafe.member.domain.Member;
import bind.iotstudycafe.member.repository.MemberRepositoryV1;
import bind.iotstudycafe.member.dto.MemberUpdateDto;
import bind.iotstudycafe.member.dto.MemberSearchCond;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemoryMemberRepository implements MemberRepositoryV1 {

    private static final Map<Long, Member> store = new HashMap<>(); //static
    private static long sequence = 0L; //static


    @Override
    public Member save(Member member) {

        member.setId(++sequence);
        store.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {

        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByMemberId(String memberId) {

        return store.values().stream()
                .filter(member -> member.getMemberId().equals(memberId))
                .findFirst();
    }

    @Override
    public List<Member> findAll(MemberSearchCond cond) {

        String userId = cond.getMemberId();
        String userName = cond.getMemberName();
        Integer maxAge = Optional.ofNullable(cond.getMaxAge()).orElse(Integer.MAX_VALUE);
        Integer minAge = Optional.ofNullable(cond.getMinAge()).orElse(Integer.MIN_VALUE);
        String memberGrade = cond.getMemberGrade();

        return store.values().stream()
                .filter(member -> {
                    if (ObjectUtils.isEmpty(userId)) {
                        return true;
                    }
                    return member.getMemberId().contains(userId);
                }).filter(member -> {
                    if (ObjectUtils.isEmpty(userName)) {
                        return true;
                    }
                    return member.getMemberName().contains(userName);
                }).filter(member -> {
//                    if (maxAge == null && minAge == null) {
//                        return true;
//                    }
                    return member.getAge() <= maxAge && member.getAge() >= minAge;
                }).filter(member -> {
                    if (ObjectUtils.isEmpty(memberGrade)) {
                        return true;
                    }
                    return member.containsGrade(memberGrade);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void update(Long id, MemberUpdateDto updateParam) {

        Member findMember = findById(id).orElseThrow();
        findMember.setMemberGrade(updateParam.getMemberGrade());
        findMember.setMemberPassword(updateParam.getMemberPassword());
        findMember.setMemberName(updateParam.getMemberName());
        findMember.setAge(updateParam.getAge());

    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    public void clearStore() {
        store.clear();
    }

}
