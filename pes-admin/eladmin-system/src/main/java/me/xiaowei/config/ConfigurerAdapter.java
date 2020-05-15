package me.xiaowei.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

/**
 * WebMvcConfigurer
 *
 * @author Zheng Jie
 * @date 2018-11-30
 */
@Configuration
@EnableWebMvc
public class ConfigurerAdapter implements WebMvcConfigurer {

    @Value("${file.path}")
    private String path;

    @Value("${file.avatar}")
    private String avatar;

    @Value("${file.imgthum}")
    private String imgthum;

    @Value("${file.imgcontent}")
    private String imgcontent;

    @Value("${file.imgcontent2}")
    private String imgcontent2;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String avatarUtl = Paths.get(avatar).normalize().toUri().toASCIIString();
        String pathUtl = Paths.get(path).normalize().toUri().toASCIIString();

        /**
         * 本地资源映射
         * */
        String imgcontentUtl = Paths.get(imgcontent).normalize().toUri().toASCIIString();
        String imgcontent2Utl = Paths.get(imgcontent2).normalize().toUri().toASCIIString();
        String imgthumUtl = Paths.get(imgthum).normalize().toUri().toASCIIString();


        registry.addResourceHandler("/avatar/**").addResourceLocations(avatarUtl).setCachePeriod(0);
        registry.addResourceHandler("/file/**").addResourceLocations(pathUtl).setCachePeriod(0);

        registry.addResourceHandler("/imgcontent/**")

                .addResourceLocations(imgcontentUtl).setCachePeriod(0)
                .addResourceLocations(imgcontent2Utl).setCachePeriod(0);

//                .addResourceLocations("file:C:/Users/modderBUG/Pictures/碧蓝航线/pixiv/")
//                .addResourceLocations("file:F:/迅雷下载/[波萝社]BOL031夏美saber/")
//                .addResourceLocations("file:F:/迅雷下载/[波萝社]BOL062夏美酱毛衣合集/")
//                .addResourceLocations("file:F:/迅雷下载/[PDLONE]铂金刊VOL.22/图包/")
//                .addResourceLocations("file:C:/Users/modderBUG/Pictures/碧蓝航线/pixiv/")
//                .addResourceLocations("file:F:/迅雷下载/apanwonder/");

        registry.addResourceHandler("/imgthum/**")

                .addResourceLocations(imgthumUtl).setCachePeriod(0);
//                .addResourceLocations("file:F:/迅雷下载/imgthum/");


        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").setCachePeriod(0);


    }
}