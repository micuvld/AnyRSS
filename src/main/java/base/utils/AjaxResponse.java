package base.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AjaxResponse {
    boolean isError;
    String response;

    public static AjaxResponse buildSuccessResponse(String response) {
        return builder().isError(false).response(response).build();
    }

    public static AjaxResponse buildErrorResponse(String response) {
        return builder().isError(true).response(response).build();
    }
}
