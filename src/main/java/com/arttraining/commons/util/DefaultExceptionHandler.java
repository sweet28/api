package com.arttraining.commons.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

public class DefaultExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3) {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView();
		String errorCode="10010";
		String errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_10010;
		
	    /*	使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常	*/
	    FastJsonJsonView view = new FastJsonJsonView();
	    Map<String, Object> attributes = new HashMap<String, Object>();
	    attributes.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
	    attributes.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
	    view.setAttributesMap(attributes);
	    mv.setView(view); 
		return mv;
	}

}
