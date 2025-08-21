package be.kdg.prog6.watersideContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FullShippingOrderResponseDto {
    private String id;
    private String soNumber;
    private String poReference;
    private String vesselNumber;
    private String customerNumber;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime estimatedArrivalDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime estimatedDepartureDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime actualArrivalDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime actualDepartureDate;
    private String status;
    private boolean inspectionCompleted;
    private boolean bunkeringCompleted;
    private boolean loadingCompleted;
    private String foremanSignature;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime validationDate;
}
