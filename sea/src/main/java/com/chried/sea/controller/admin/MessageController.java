package com.chried.sea.controller.admin;

import com.chried.core.param.Parameter;
import com.chried.core.param.Result;
import com.chried.sea.system.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息控制器.
 *
 * @author chried
 */
@RestController
@RequestMapping(value = "admin/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * @param accessToken token.
     * @return 消息数.
     */
    @GetMapping(value = "count")
    public Result<Integer> count(@RequestHeader(value = Parameter.ACCESS_TOKEN) String accessToken, @RequestParam(value = "read", required = false) Boolean read) {

        return Result.of(messageService.count(read, accessToken));
    }
}
