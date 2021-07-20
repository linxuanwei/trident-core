package x.trident.core.system.integration;

import x.trident.core.pojo.response.ErrorResponseData;
import x.trident.core.util.ResponseRenderUtil;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 当请求404的时候返回的错误界面
 *
 * @author 林选伟
 * @date 2021/5/17 10:45
 */
public class ErrorStaticJsonView implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (response.isCommitted()) {
            // response已经提交不能响应
            return;
        }

        ErrorResponseData errorResponseData = new ErrorResponseData("404", "请求资源不存在");
        ResponseRenderUtil.renderJsonResponse(response, errorResponseData);
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

}