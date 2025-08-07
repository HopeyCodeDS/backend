package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderMaterialRequirement;
import be.kdg.prog6.warehousingContext.ports.out.PurchaseOrderMaterialRequirementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PurchaseOrderMaterialRequirementDatabaseAdapter implements PurchaseOrderMaterialRequirementRepositoryPort {
    
    private final PurchaseOrderMaterialRequirementRepository repository;
    private final PurchaseOrderMaterialRequirementJpaMapper mapper;
    
    @Override
    public PurchaseOrderMaterialRequirement save(PurchaseOrderMaterialRequirement requirement) {
        PurchaseOrderMaterialRequirementJpaEntity entity = mapper.toJpa(requirement);
        PurchaseOrderMaterialRequirementJpaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public List<PurchaseOrderMaterialRequirement> findByPurchaseOrderNumber(String purchaseOrderNumber) {
        return repository.findByPurchaseOrderNumber(purchaseOrderNumber)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PurchaseOrderMaterialRequirement> findByPurchaseOrderNumberAndRawMaterialName(String purchaseOrderNumber, String rawMaterialName) {
        return repository.findByPurchaseOrderNumberAndRawMaterialName(purchaseOrderNumber, rawMaterialName)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<PurchaseOrderMaterialRequirement> findById(UUID requirementId) {
        return repository.findById(requirementId.toString())
                .map(mapper::toDomain);
    }
} 