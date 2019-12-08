package me.duvu.tracking.aop.monitor;

import me.duvu.tracking.domain.Device;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author beou on 11/4/18 15:31
 */
@Component
public class EmailContentBuilder {
    private final TemplateEngine templateEngine;

    public EmailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    //-- template engine builder
    public String buildDeviceCreated(Device device) {
        Context context = new Context();
        context.setVariable("device", device);
        return templateEngine.process("deviceCreated", context);
    }

    public String buildDeviceDeleted(Device device) {
        Context context = new Context();
        context.setVariable("device", device);
        return templateEngine.process("deviceDeleted", context);
    }
}
