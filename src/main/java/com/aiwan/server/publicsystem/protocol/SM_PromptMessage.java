package com.aiwan.server.publicsystem.protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * @since 2019.6.20
 * 提示信息发送
 */
public class SM_PromptMessage implements Serializable {

    /**
     * 提示信息
     */
    private int promptCode;

    /**
     * 其他信息
     */
    private String otherMessage;

    public static SM_PromptMessage valueOf(int promptCode, String otherMessage) {
        SM_PromptMessage sm_promptMessage = new SM_PromptMessage();
        sm_promptMessage.setPromptCode(promptCode);
        sm_promptMessage.setOtherMessage(otherMessage);
        return sm_promptMessage;
    }

    public int getPromptCode() {
        return promptCode;
    }

    public void setPromptCode(int promptCode) {
        this.promptCode = promptCode;
    }

    public String getOtherMessage() {
        return otherMessage;
    }

    public void setOtherMessage(String otherMessage) {
        this.otherMessage = otherMessage;
    }
}
