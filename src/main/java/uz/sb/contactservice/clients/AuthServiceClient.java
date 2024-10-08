package uz.sb.contactservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.sb.contactservice.config.FeignConfig;
import uz.sb.domain.dto.response.UserResponse;

@FeignClient(name = "AUTH-SERVICE", configuration = FeignConfig.class)
public interface AuthServiceClient {

    @GetMapping("api/auth/find-by-id/{id}")
    UserResponse findById(@PathVariable Long id);
}
