package com.saintdan.framework.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

/**
 * Get current user.{@link AuthenticationPrincipal}
 *
 * <pre>
 * @Controller
 * public class MyController {
 *   @RequestMapping("/user/current/show")
 *   public String show(@CurrentUser CustomUser customUser) {
 *       // do something with CustomUser
 *       return "view";
 *   }
 * </pre>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@kotlin.annotation.Retention()
@AuthenticationPrincipal
annotation class CurrentUser