package by.vstu.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
public class ThymeleafTemplateService {

    private final TemplateEngine textTemplateEngine;

    @Autowired
    public ThymeleafTemplateService(TemplateEngine textTemplateEngine) {
        this.textTemplateEngine = textTemplateEngine;
    }

    public String process(String template, Locale locale, Map<String, Object> model) {
        Context context = new Context(locale, model);
        return textTemplateEngine.process(template, context);
    }
}
