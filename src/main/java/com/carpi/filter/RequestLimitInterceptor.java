package com.carpi.filter;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.RateLimiter;

public class RequestLimitInterceptor implements HandlerInterceptor, BeanPostProcessor {

	private Logger logger = LoggerFactory.getLogger(RequestLimitInterceptor.class);

	private Integer globalRateLimiter = 100;

	private Map<PatternsRequestCondition, RateLimiter> urlRateMap;

	private Properties urlProperties;

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (urlRateMap != null) {
			String lookupPath = urlPathHelper.getLookupPathForRequest(request);
			for (PatternsRequestCondition patternsRequestCondition : urlRateMap.keySet()) {
				// 使用spring DispatcherServlet的匹配器PatternsRequestCondition进行匹配
				patternsRequestCondition.compareTo(patternsRequestCondition, request);
				PatternsRequestCondition matches = patternsRequestCondition.getMatchingCondition(request);
				if (matches != null) {
					if (urlRateMap.get(patternsRequestCondition).tryAcquire(1000, TimeUnit.MILLISECONDS)) {
						logger.info(" 请求'{}'匹配到mathes {} ,成功获取令牌，进入请求。", lookupPath,
								Joiner.on(",").join(patternsRequestCondition.getPatterns()));
					} else {
						logger.info(" 请求'{}'匹配到mathes {}，超过限流速率，获取令牌失败。", lookupPath,
								Joiner.on(",").join(patternsRequestCondition.getPatterns()));
						return false;
					}

				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	/**
	 * 限流的 URL与限流值的K/V 值
	 *
	 * @param urlProperties
	 */
	public void setUrlProperties(Properties urlProperties) {
		this.urlProperties = urlProperties;
	}

	public void setGlobalRateLimiter(Integer globalRateLimiter) {
		this.globalRateLimiter = globalRateLimiter;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (RequestMappingHandlerMapping.class.isAssignableFrom(bean.getClass())) {
			if (urlRateMap == null) {
				urlRateMap = new ConcurrentHashMap<>();
			}
			logger.info("we get all the controllers's methods and assign it to urlRateMap");
			RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) bean;
			Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
			for (RequestMappingInfo rmi : handlerMethods.keySet()) {
				PatternsRequestCondition pc = rmi.getPatternsCondition();
				urlRateMap.put(pc, RateLimiter.create(globalRateLimiter));
			}
			if (urlProperties != null) {
				for (String urlPatterns : urlProperties.stringPropertyNames()) {
					String limit = urlProperties.getProperty(urlPatterns);
					if (!limit.matches("^-?\\d+$"))
						logger.error("the value {} for url patterns {} is not a number ,please check it ", limit,
								urlPatterns);
					urlRateMap.put(new PatternsRequestCondition(urlPatterns),
							RateLimiter.create(Integer.parseInt(limit)));
				}
			}
		}
		return bean;
	}
}