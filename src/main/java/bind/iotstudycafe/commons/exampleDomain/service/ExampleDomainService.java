package bind.iotstudycafe.commons.exampleDomain.service;

import bind.iotstudycafe.commons.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ExampleDomainService {

    Mono<ResponseEntity<ExampleDomain>> saveToEntity(ExampleDomain exampleDomain);

    Mono<ExampleDomain> saveBodyToMono(ExampleDomain exampleDomain);

    Mono<ResponseEntity<ExampleDomain>> findByIdToEntity(Long id);

    Mono<ExampleDomain> findByIdBodyToMono(Long id);

    Mono<ResponseEntity<List<ExampleDomain>>> findExampleDomainsToEntityList(ExampleDomain exampleDomain);

    List<ExampleDomain> findExampleDomainsBodyToFlux(ExampleDomain exampleDomain);

//    Mono<ResponseEntity<List<ExampleDomain>>>
//    List<ExampleDomain> findAll(ExampleDomain cond);
//
//     Mono<Void> update(Long id, ExampleDomainUpdateDto updateParam);

}
