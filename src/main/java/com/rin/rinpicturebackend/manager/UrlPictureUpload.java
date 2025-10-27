package com.rin.rinpicturebackend.manager;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.rin.rinpicturebackend.exception.BusinessException;
import com.rin.rinpicturebackend.exception.ErrorCode;
import com.rin.rinpicturebackend.exception.ThrowUtils;
import com.rin.rinpicturebackend.manager.upload.PictureUploadTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * @ ClassName UrlPictureUpload
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/27 23:17
 */
@Service
public class UrlPictureUpload extends PictureUploadTemplate {
    @Override
    protected void validPicture(Object inputSource) {
        String uploadUrl = (String) inputSource;
        ThrowUtils.throwIf(StrUtil.isBlank(uploadUrl), ErrorCode.PARAMS_ERROR, "上传图片地址为空");
        // 1. 格式验证
        try {
            new URL(uploadUrl);
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "上传图片地址错误");
        }
        // 2. 协议校验
        ThrowUtils.throwIf(!(uploadUrl.startsWith("http://:") || uploadUrl.startsWith("https://")),
                ErrorCode.PARAMS_ERROR, "仅支持 HTTP 或者 HTTPS 协议的文件地址");
        // 3. 发送head请求，验证图片是否存在
        HttpResponse response = null;
        try {
            response = HttpUtil.createRequest(Method.HEAD, uploadUrl).execute();
            // 未正常返回，无需执行其他判断
            if (response.getStatus() != HttpStatus.HTTP_OK) {
                return;
            }
            // 4.校验文件类型
            String contentType = response.header("Content-Type");
            if (StrUtil.isNotBlank(contentType)) {
                // 允许的图片类型
                final List<String> ALLOW_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/webp");
                ThrowUtils.throwIf(!ALLOW_CONTENT_TYPES.contains(contentType.toLowerCase()), ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
            // 5. 校验文件大小
            String contentLengthStr = response.header("Content-Length");
            if (StrUtil.isNotBlank(contentLengthStr)) {
                try {
                    long contentLength = Long.parseLong(contentLengthStr);
                    final long TWO_MB = 1024 * 1024 * 2L; // 2MB
                    ThrowUtils.throwIf(contentLength > TWO_MB, ErrorCode.PARAMS_ERROR, "文件大小不能超过2MB");
                } catch (NumberFormatException e) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小超出限制");
                }
            }
        } finally {
            // 资源释放
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    protected String getOriginFileName(Object inputSource) {
        String uploadUrl = (String) inputSource;
        return FileUtil.mainName(uploadUrl);
    }

    @Override
    protected void processFile(Object inputSource, File file) throws Exception {
        String uploadUrl = (String) inputSource;
        HttpUtil.downloadFile(uploadUrl, file);
    }
}
