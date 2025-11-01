package space.endora.dummy.features.commend.service;

import space.endora.dummy.features.commend.dto.CommentRequest;
import space.endora.dummy.features.commend.dto.CommentResponse;
import space.endora.dummy.features.commend.dto.CommentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentResponse createComment(CommentRequest request);

    Page<CommentResponse> getAllComments(Pageable pageable);

    CommentResponse getCommentById(Long id);

    CommentResponse updateComment(Long id, CommentUpdateRequest request);

    void deleteComment(Long id);

    void resetDailyComments();

    long getTodayCommentCount();
}