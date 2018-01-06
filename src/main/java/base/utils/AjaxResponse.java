package base.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AjaxResponse {
    boolean isError;
    Object response;

    public static AjaxResponse buildSuccessResponse(Object response) {
        return builder().isError(false).response(response).build();
    }

    public static AjaxResponse buildErrorResponse(Object response) {
        return builder().isError(true).response(response).build();
    }
}
