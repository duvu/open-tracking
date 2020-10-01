package me.duvu.tracking.aop.monitor;

import me.duvu.tracking.entities.Device;
import me.duvu.tracking.internal.websocket.LocalWSService;
import me.duvu.tracking.external.email.MailService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author beou on 6/6/18 17:02
 */

@Slf4j
@Aspect
@Component
public class ServiceMonitor {

    private final MailService mailService;
    private final LocalWSService localWSService;
    private final EmailContentBuilder contentBuilder;

    public ServiceMonitor(MailService mailService, LocalWSService localWSService, EmailContentBuilder contentBuilder) {
        this.mailService = mailService;
        this.localWSService = localWSService;
        this.contentBuilder = contentBuilder;
    }

    @After("execution(* me.duvu.tracking.services.AccountService.create(..))")
    public void logCreateAccount(JoinPoint joinPoint) {
        log.info("[>_] AspectJ+++///" + joinPoint);
    }

    @After("execution(* me.duvu.tracking.services.AccountService.update(..))")
    public void logUpdateAccount(JoinPoint joinPoint) {
        log.info("[>_] AspectJ+++///" + joinPoint);
    }

    @AfterReturning(value = "execution(* me.duvu.tracking.services.DeviceService.create(..))", returning = "result")
    public void logCreateDevice(JoinPoint joinPoint, Object result) {
        if (result instanceof Device) {
            Device device = (Device) result;
            String content = contentBuilder.buildDeviceCreated(device);
            mailService.send("hoaivubk@gmail.com", "Device "+device.getDeviceId()+" Created", content);
        }

    }

    @AfterReturning(value = "execution(* me.duvu.tracking.services.DeviceService.delete(..))", returning = "result")
    public void deleteDevicePointCut(JoinPoint joinPoint, Object result) {
        if (result instanceof Device) {
            Device device = (Device) result;
            String deviceId = device.getDeviceId();
            String content = contentBuilder.buildDeviceDeleted(device);
            mailService.send("hoaivubk@gmail.com", "Device " +deviceId+ " Deleted", content);

            try {
                localWSService.send("{\"command\":\"DEVICE_DELETED\", \"data\":\"" + deviceId +"\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @After("execution(* me.duvu.tracking.services.DeviceService.update(..))")
    public void logUpdateDevice(JoinPoint joinPoint) {
        log.info("[>_] AspectJ+++///" + joinPoint);
    }
}
