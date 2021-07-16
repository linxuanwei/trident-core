package x.trident.core.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import x.trident.core.pojo.response.ErrorResponseData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * http响应信息的直接渲染工具
 *
 * @author 林选伟
 * @date 2020/12/15 21:39
 */
@Slf4j
public class ResponseRenderUtil {
    private ResponseRenderUtil() {
    }

    /**
     * 渲染接口json信息
     */
    public static void renderJsonResponse(HttpServletResponse response, Object responseData) {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.toString());
        String errorResponseJsonData = JSON.toJSONString(responseData);
        try {
            response.getWriter().write(errorResponseJsonData);
        } catch (IOException e) {
            log.error("渲染http json信息错误！", e);
        }
    }

    /**
     * 渲染接口json信息
     */
    public static void renderErrorResponse(HttpServletResponse response,
                                           String code, String message, String exceptionClazz) {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.toString());
        ErrorResponseData errorResponseData = new ErrorResponseData(code, message);
        errorResponseData.setExceptionClazz(exceptionClazz);
        String errorResponseJsonData = JSON.toJSONString(errorResponseData);
        try {
            response.getWriter().write(errorResponseJsonData);
        } catch (IOException e) {
            log.error("渲染http json信息错误！", e);
        }
    }

}
