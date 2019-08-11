package org.yjcycc.lottery.resolver;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.tools.common.exception.BizException;
import org.yjcycc.tools.common.exception.ParameterException;
import org.yjcycc.tools.common.exception.PermissionException;
import org.yjcycc.tools.common.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @description Spring自定义异常解析器
 * @author biao
 * @date 2016年9月14日
 */
public class MyExceptionResolver implements HandlerExceptionResolver {
	
	private final static Logger LOG = Logger.getLogger(MyExceptionResolver.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
		PrintWriter writer = null;
		try {
			this.setResponse(response);
			writer = response.getWriter();
			this.resolve(response, ex, writer);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (writer != null) writer.close(); 
		}
		return null;
	}
	
	/**
	 * @description 设置response头部信息
	 * @param response
	 */
	private void setResponse(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");   
		response.setContentType(Constant.JSON_CHARSET);
		response.setCharacterEncoding("UTF-8");
	}

	/**
	 * @description 解析异常并以Json方式返回异常信息
	 * @param response
	 * @param ex
	 * @param writer
	 */
	private void resolve(HttpServletResponse response, Exception ex, PrintWriter writer) {
		String statusCode = Constant.STS_CODE_999;
		String msg = Constant.MSG_999;
		if (ex instanceof PermissionException) {
			statusCode = Constant.STS_CODE_997;
			msg = ((PermissionException) ex).getMessage();
		} else if (ex instanceof BizException) {
			statusCode = Constant.STS_CODE_996;
			msg = ((BizException) ex).getMessage();
		} else if (ex instanceof ParameterException) {
			statusCode = Constant.STS_CODE_998;
			msg = ((ParameterException) ex).getMessage();
		}
		Map<String, Object> mapResult = new HashMap<String, Object>();
		mapResult.put(Constant.STATUS_CODE, statusCode);
		mapResult.put(Constant.MSG, msg);
		writer.write(JsonUtil.toJson(mapResult));
		writer.flush();
	}

}
