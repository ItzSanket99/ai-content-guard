package com.sanket.aicontentguard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "summaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String originalText;

    @Lob
    private String summaryText;

    @Enumerated(EnumType.STRING)
    private SummaryType summaryType;

    @Enumerated(EnumType.STRING)
    private SummaryStatus status;

    private Integer riskScore;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    @OneToMany(
            mappedBy = "summary",
            cascade = CascadeType.ALL
    )
    private List<Violation> violations;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}