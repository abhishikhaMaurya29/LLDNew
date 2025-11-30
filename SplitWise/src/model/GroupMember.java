package model;

import java.time.LocalDateTime;

public class GroupMember {
    private Long id;
    private User user;
    private Group group;
    private LocalDateTime joinedAt;
}
