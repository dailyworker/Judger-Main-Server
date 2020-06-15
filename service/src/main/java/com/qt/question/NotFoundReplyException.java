package com.qt.question;

public class NotFoundReplyException extends RuntimeException{

    public NotFoundReplyException() { super(); }

    public NotFoundReplyException(String message) { super(message); }
}
