package kfoodbox.bookmark.controller;

import kfoodbox.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;
}
