package com.rin.rinpicturebackend.controller;

import com.rin.rinpicturebackend.common.BaseResponse;
import com.rin.rinpicturebackend.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ ClassName MainController
 * @ Description 健康检查
 * @ Author Rin
 * @ Date 2025/9/26 19:41
 */
@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("/health")
    public BaseResponse<String> health() {
        return ResultUtils.success("ok");
    }
}
