package com.rin.rinpicturebackend.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ ClassName CosClientConfig
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/8 16:32
 */
@Configuration
@ConfigurationProperties(prefix = "cos.client")
@Data
public class CosClientConfig {

    /**
     * 域名
     */
    private String host;

    /**
     * secretId
     */
    private String secretId;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 存储桶
     */
    private String bucket;

    /**
     * 地域
     */
    private String region;

    @Bean
    public COSClient cosClient() {
        // 初始化身份验证信息
        BasicCOSCredentials credentials = new BasicCOSCredentials(secretId, secretKey);

        // 初始化客户端配置
        ClientConfig config = new ClientConfig(new Region(region));

        // 初始化cos客户端
        return new COSClient(credentials, config);
    }
}
