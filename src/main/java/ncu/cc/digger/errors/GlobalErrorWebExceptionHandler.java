package ncu.cc.digger.errors;

//@Component
//@Order(-2)
public class GlobalErrorWebExceptionHandler { // extends AbstractErrorWebExceptionHandler {
//    public GlobalErrorWebExceptionHandler(
//            ErrorAttributes errorAttributes,
//            ResourceProperties resourceProperties,
//            ApplicationContext applicationContext,
//            ServerCodecConfigurer configurer) {
//        super(errorAttributes, resourceProperties, applicationContext);
//        this.setMessageWriters(configurer.getWriters());
//    }
//
//    @Override
//    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
//        return RouterFunctions.route(
//                RequestPredicates.all(), this::renderErrorResponse);
//    }
//
//    private Mono<ServerResponse> renderErrorResponse(
//            ServerRequest request) {
//
//        Map<String, Object> errorPropertiesMap = getErrorAttributes(request, false);
//
//        StackTraceUtil.print1(errorPropertiesMap);
//
////        return ServerResponse.status(HttpStatus.BAD_REQUEST)
////                .contentType(MediaType.APPLICATION_JSON_UTF8)
////                .body(BodyInserters.fromObject(errorPropertiesMap));
//        return ServerResponse.temporaryRedirect(URI.create(Routes.ERROR)).build();
//    }
}
