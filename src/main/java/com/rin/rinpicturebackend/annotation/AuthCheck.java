package com.rin.rinpicturebackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限检查
 *
 * @ ClassName AtuCheck
 * @ Description
 * @ Author Rin
 * @ Date 2025/9/28 10:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 必须拥有某个角色
     *
     * @return
     */
    String mustRole() default "";

}
