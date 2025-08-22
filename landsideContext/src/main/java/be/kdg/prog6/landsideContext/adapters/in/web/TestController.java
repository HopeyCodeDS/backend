package be.kdg.prog6.landsideContext.adapters.in.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/landside/test")
public class TestController {
    
    @GetMapping("/public")
    public String publicEndpoint() {
        return "This endpoint is public - no authentication required";
    }
    
    @GetMapping("/authenticated")
    public String authenticatedEndpoint() {
        return "This endpoint requires authentication";
    }
    
    @GetMapping("/seller-only")
    @PreAuthorize("hasRole('SELLER')")
    public String sellerOnlyEndpoint() {
        return "This endpoint requires SELLER role";
    }
}
