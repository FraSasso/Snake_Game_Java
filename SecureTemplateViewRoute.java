import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;

public interface SecureTemplateViewRoute extends TemplateViewRoute {

    ModelAndView secureHandle(Request request) throws Exception;

    @Override
    public default ModelAndView handle(Request request, Response response) throws Exception {
        try {
            return secureHandle(request);
        } catch (Exception e) {
            return new ModelAndView(new HashMap<String, Object>(), "Error.hbs");
        }
    }

}

