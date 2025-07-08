package org.comment.service;

public interface AskAiService {
    /**
     * 简单 AI 问答（单轮对话）
     * 
     * @param prompt 用户问题
     * @return AI 返回文本
     */
    String askAiSimple(String prompt) throws Exception;
}