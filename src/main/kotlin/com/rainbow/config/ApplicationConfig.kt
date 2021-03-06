package com.rainbow.config

import com.rainbow.interceptor.ApiInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * Created by rainbow on 2017/6/5.
 *一事专注，便是动人；一生坚守，便是深邃！
 */
@Configuration
class ApplicationConfig : WebMvcConfigurerAdapter() {

    @Bean
    open fun apiInterceptor() = ApiInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry) {

        registry.addInterceptor(apiInterceptor()).addPathPatterns("/api/**")
    }
}