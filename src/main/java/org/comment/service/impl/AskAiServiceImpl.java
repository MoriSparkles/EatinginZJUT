package org.comment.service.impl;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.comment.service.AskAiService;
import org.springframework.stereotype.Service;

@Service
public class AskAiServiceImpl implements AskAiService {
    // 阿里云百炼平台应用ID
    private static final String APP_ID = "52df196922cf41df880fe210373b87e4";

    @Override
    public String askAiSimple(String prompt) throws ApiException, NoApiKeyException, InputRequiredException {
        ApplicationParam param = ApplicationParam.builder()
                .apiKey("sk-dbca692f87a7477ba0c0a208909a2ba0") // 临时硬编码，仅本地测试用
                .appId(APP_ID)
                .prompt(prompt)
                .build();
        Application application = new Application();
        ApplicationResult result = application.call(param);
        return result.getOutput().getText();
    }
}