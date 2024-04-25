package com.example.forum_backendapi.dto;

public class ReplyRequestDTO {
    private Long userId;
    private String replyText;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }
}
