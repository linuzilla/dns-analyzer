package ncu.cc.digger.controllers_api.utils;

import ncu.cc.digger.models.APICommonResponse;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class APIControllerUtil {
    public static APICommonResponse response(Consumer<APICommonResponse> consumer) {
        APICommonResponse apiCommonResponse = new APICommonResponse();
        apiCommonResponse.setStatus(APICommonResponse.Status.success);
        consumer.accept(apiCommonResponse);
        return apiCommonResponse;
    }

    public static APICommonResponse success(Supplier<Object> supplier) {
        APICommonResponse apiCommonResponse = new APICommonResponse();
        apiCommonResponse.setStatus(APICommonResponse.Status.success);
        apiCommonResponse.setData(supplier.get());
        return apiCommonResponse;
    }
}
