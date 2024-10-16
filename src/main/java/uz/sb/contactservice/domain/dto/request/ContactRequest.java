package uz.sb.contactservice.domain.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContactRequest {
    private Long userId; // adder
    private Long contactUserId;  // person to join contact
    private String contactName;
}
