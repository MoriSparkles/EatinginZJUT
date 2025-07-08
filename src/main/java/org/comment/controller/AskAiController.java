package org.comment.controller;

import org.comment.service.AskAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AskAiController {
    @Autowired
    private AskAiService askAiService;

    /**
     * 简单 AI 问答接口（单轮对话）
     * 
     * @param param {"prompt": "你的问题"}
     * @return {"success": true, "data": "AI返回内容"} 或 {"success": false, "message":
     *         "错误信息"}
     */
    @PostMapping("/simple")
    public Map<String, Object> askAiSimple(@RequestBody Map<String, String> param) {
        Map<String, Object> result = new HashMap<>();
        try {
            String prompt = param.get("prompt");
            String aiText = askAiService.askAiSimple(prompt);
            result.put("success", true);
            result.put("data", aiText);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "AI服务异常：" + e.getMessage());
        }
        return result;
    }
}