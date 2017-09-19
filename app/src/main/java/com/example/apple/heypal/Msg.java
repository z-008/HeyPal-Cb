package com.example.apple.heypal;

/**
 * Created by apple on 9/18/17.
 */

public class Msg {

        public String messageBody;
        public String messageId;
        public int messagePosition;
        public String messageTime;

        public Msg(String messageId, String messageBody, int messagePosition, String messageTime)
        {
            this.messageId = messageId;
            this.messageBody = messageBody;
            this.messagePosition = messagePosition;
            this.messageTime = messageTime;

    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getMessageId() {
        return messageId;
    }

    public int getMessagePosition() {
        return messagePosition;
    }

    public String getMessageTime() {
        return messageTime;
    }
}
