package com.rin.rinpicturebackend.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import com.rin.rinpicturebackend.config.CosClientConfig;
import com.rin.rinpicturebackend.exception.BusinessException;
import com.rin.rinpicturebackend.exception.ErrorCode;
import com.rin.rinpicturebackend.exception.ThrowUtils;
import com.rin.rinpicturebackend.model.dto.file.UploadPictureResult;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @ ClassName FileManager 提供图片上传，返回图片解析的方法
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/8 18:10
 */
@Deprecated
@Service
@Slf4j
public class FileManager {
    @Resource
    private CosManager cosManager;

    @Resource
    private CosClientConfig cosClientConfig;


    public UploadPictureResult uploadPicture(MultipartFile multipartFile, String uploadPathPrefix) {
        // 校验图片
        validPicture(multipartFile);
        //图片上传地址
        String uuid = RandomUtil.randomString(16);
        String originFilename = multipartFile.getOriginalFilename();
        String uploadFileName = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), uuid, FileUtil.getSuffix(originFilename));
        String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFileName);
        File file = null;
        try {
            // 创建临时文件
            file = File.createTempFile(uploadPath, null);
            multipartFile.transferTo(file);
            // 上传图片
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
            // 封装返回结果
            UploadPictureResult uploadPictureResult = new UploadPictureResult();
            int picWidth = imageInfo.getWidth();
            int picHeight = imageInfo.getHeight();
            double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
            uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
            uploadPictureResult.setPicName(FileUtil.mainName(originFilename));
            uploadPictureResult.setPicSize(FileUtil.size(file));
            uploadPictureResult.setPicWidth(picWidth);
            uploadPictureResult.setPicHeight(picHeight);
            uploadPictureResult.setPicScale(picScale);
            uploadPictureResult.setPicFormat(imageInfo.getFormat());
            return uploadPictureResult;

        } catch (Exception e) {
            log.error("上传图片到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            this.deleteTempFile(file);
        }
    }

    /**
     * 校验图片
     * 由于文件的校验比较复杂，所以抽象为单独的方法，对文件的大小，后缀名进行校验
     *
     * @param multipartFile 文件
     */
    private void validPicture(MultipartFile multipartFile) {
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR, "上传文件为空");
        // 1. 校验文件大小
        long fileSize = multipartFile.getSize();
        final long ONE_M = 1024 * 1024L;
        ThrowUtils.throwIf(fileSize > 2 * ONE_M, ErrorCode.PARAMS_ERROR, "上传文件大小不能超过2M");

        // 2. 校验文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final List<String> IMAGE_SUFFIX_LIST = Arrays.asList("png", "jpg", "jpeg", "webp");
        ThrowUtils.throwIf(!IMAGE_SUFFIX_LIST.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "上传文件格式错误");
    }

    private void validPicture(String uploadUrl) {
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

    /**
     * 删除临时文件
     * 在文件的上传的过程中，首先会创建一个临时文件夹，不论是否上传成功，都会创建一个临时文件，所以需要及时删除
     *
     * @param file 文件
     */
    private void deleteTempFile(File file) {
        if (file == null) {
            return;
        }
        // 删除临时文件
        boolean deleteResult = file.delete();
        if (!deleteResult) {
            log.error("file delete error, filepath = {}", file.getAbsolutePath());
        }
    }

    public UploadPictureResult uploadPictureByUrl(String uploadUrl, String uploadPathPrefix) {
        // 校验图片(url地址)
        validPicture(uploadUrl);
        //图片上传地址
        String uuid = RandomUtil.randomString(16);
        String originFilename = FileUtil.mainName(uploadUrl);
        String uploadFileName = String.format("%s_%s.%s",
                DateUtil.formatDate(new Date()),
                uuid,
                FileUtil.getSuffix(originFilename));
        String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFileName);
        File file = null;
        try {
            // 创建临时文件
            file = File.createTempFile(uploadPath, null);
//            multipartFile.transferTo(file);
            // 直接通过url下载图片
            HttpUtil.downloadFile(uploadUrl, file);
            // 上传图片
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
            // 封装返回结果
            UploadPictureResult uploadPictureResult = new UploadPictureResult();
            int picWidth = imageInfo.getWidth();
            int picHeight = imageInfo.getHeight();
            double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
            uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
            uploadPictureResult.setPicName(FileUtil.mainName(originFilename));
            uploadPictureResult.setPicSize(FileUtil.size(file));
            uploadPictureResult.setPicWidth(picWidth);
            uploadPictureResult.setPicHeight(picHeight);
            uploadPictureResult.setPicScale(picScale);
            uploadPictureResult.setPicFormat(imageInfo.getFormat());
            return uploadPictureResult;

        } catch (Exception e) {
            log.error("上传图片到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            this.deleteTempFile(file);
        }
    }
}
