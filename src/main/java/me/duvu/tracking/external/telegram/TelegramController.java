package me.duvu.tracking.external.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/telegram")
public class TelegramController {
    Logger logger = LoggerFactory.getLogger(getClass());

}
