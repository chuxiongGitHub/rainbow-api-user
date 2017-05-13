/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.config

import com.rainbow.interceptor.BaseInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
@Configuration
open class ApplicationConfig : WebMvcConfigurerAdapter() {

    @Bean
    open fun apiInterceptor() = BaseInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(apiInterceptor()).addPathPatterns("**")
    }
}