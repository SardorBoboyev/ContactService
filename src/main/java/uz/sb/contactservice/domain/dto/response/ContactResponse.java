package uz.sb.contactservice.domain.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContactResponse extends BaseResponse {
    private Long id;
    private Long userId; // adder
    private Long contactUserId;  // person to join contact
    private String contactName;
}
