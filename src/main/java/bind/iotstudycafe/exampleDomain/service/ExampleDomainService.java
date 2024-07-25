package bind.iotstudycafe.exampleDomain.service;

import bind.iotstudycafe.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.exampleDomain.dto.ExampleDomainSave;
import bind.iotstudycafe.exampleDomain.dto.ExampleDomainSearchCond;
import bind.iotstudycafe.exampleDomain.dto.ExampleDomainUpdate;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ExampleDomainService {

    Mono<ResponseEntity<ExampleDomain>> saveToEntity(ExampleDomainSave exampleDomainSave);

    Mono<ExampleDomain> saveBodyToMono(ExampleDomainSave exampleDomainSave);

    Mono<ResponseEntity<ExampleDomain>> findByIdToEntity(Long id);

    Mono<ExampleDomain> findByIdBodyToMono(Long id);

    Mono<ResponseEntity<List<ExampleDomain>>> findExampleDomainsToEntityList(ExampleDomainSearchCond exampleDomainSearchCond);

    List<ExampleDomain> findExampleDomainsBodyToFlux(ExampleDomainSearchCond exampleDomainSearchCond);

    Mono<Void> update(Long id, ExampleDomainUpdate updateParam);

    Mono<Void> delete(Long id);
}
