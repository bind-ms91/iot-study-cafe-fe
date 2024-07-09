package bind.iotstudycafe.commons.exampleDomain.service;

import bind.iotstudycafe.commons.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ExampleDomainService {

    Mono<ResponseEntity<ExampleDomain>> save(ExampleDomainDto exampleDomainDto);

    Mono<ResponseEntity<ExampleDomain>> findByIdToEntity(Long id);

    Mono<ExampleDomain> findByIdBodyToMono(Long id);

//    List<ExampleDomain> findAll(ExampleDomain cond);
//
//     Mono<Void> update(Long id, ExampleDomainUpdateDto updateParam);

}
