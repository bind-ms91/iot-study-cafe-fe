package bind.iotstudycafe.commons.exampleDomain.service;

import bind.iotstudycafe.commons.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainSave;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainSearchCond;
import bind.iotstudycafe.commons.exampleDomain.dto.ExampleDomainUpdate;
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

//    Mono<ResponseEntity<List<ExampleDomain>>>
//    List<ExampleDomain> findAll(ExampleDomain cond);
//


}
