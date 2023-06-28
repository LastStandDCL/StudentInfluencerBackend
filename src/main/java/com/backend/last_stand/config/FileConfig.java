package com.backend.last_stand.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 这个类用于存储程序产生的文件存储路径
 *
 * @author bowen 2631702650@qq.com
 * @since 2023/1/25
 */
@Component
@ConfigurationProperties(prefix = "storage")
@Data
public class FileConfig {
    /**
     * 程序附带存储文件夹的根路径
     */
    private String rootPath;

}
