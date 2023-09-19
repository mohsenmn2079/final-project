package com.example.finalproject.report.exeption;

public class LikeDislikeException extends RuntimeException {
    public LikeDislikeException(Long userId,long reportId) {
        super(userId + " can't like or dislike report "+ reportId);
    }

    public LikeDislikeException(String message) {
        super(message);
    }

    public LikeDislikeException(String message, Throwable cause) {
        super(message, cause);
    }
}
