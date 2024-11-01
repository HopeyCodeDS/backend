package be.kdg.prog6.landsideContext.core;


import be.kdg.prog6.common.events.WeighBridgeTicketGeneratedEvent;
import be.kdg.prog6.landsideContext.facade.GenerateTicketCommand;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.WeighBridgeTicket;
import be.kdg.prog6.landsideContext.ports.in.GenerateWeighBridgeTicketUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.WeighBridgeTicketRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.DomainEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class GenerateWeighBridgeTicketUseCaseImpl implements GenerateWeighBridgeTicketUseCase {

    private final TruckRepositoryPort truckRepositoryPort;
    private final WeighBridgeTicketRepositoryPort ticketRepositoryPort;
    private final DomainEventPublisher eventPublisher;

    public GenerateWeighBridgeTicketUseCaseImpl(TruckRepositoryPort truckRepositoryPort,
                                                WeighBridgeTicketRepositoryPort ticketRepositoryPort,
                                                DomainEventPublisher eventPublisher) {
        this.truckRepositoryPort = truckRepositoryPort;
        this.ticketRepositoryPort = ticketRepositoryPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public WeighBridgeTicket generateTicket(GenerateTicketCommand command) {
        log.info("Generating Weigh Bridge Ticket using this details: {}", command);
        Truck truck = truckRepositoryPort.findTruckByLicensePlate(command.getLicensePlate())
                .orElseThrow(() -> new IllegalArgumentException("Truck not found"));

        // Create ticket and let WeighBridgeTicket handle the net weight calculation
        WeighBridgeTicket ticket = new WeighBridgeTicket(
                truck.getLicensePlate(),
                truck.getWeight(),
                command.getTareWeight()
        );

        ticketRepositoryPort.save(ticket);

        // Publish event after successfully generating the ticket
        eventPublisher.publish(new WeighBridgeTicketGeneratedEvent(
                ticket.getLicensePlate(),
                ticket.getGrossWeight(),
                ticket.getTareWeight(),
                ticket.getNetWeight(),
                ticket.getTimestamp()
        ));

        return ticket;
    }
}
