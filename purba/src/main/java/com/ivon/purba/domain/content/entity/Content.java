package com.ivon.purba.domain.content.entity;

import com.ivon.purba.config.baseEntity.BaseTimeEntity;
import com.ivon.purba.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "content")
@NoArgsConstructor
public class Content extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_type_id", nullable = false, updatable = false)
    private ContentType contentType;

    @Column(name = "easy_delete", nullable = false)
    @ColumnDefault("false")
    private Boolean easyDelete;

    @Column(name = "title", nullable = true, length = 255)
    private String title;

    @Column(name = "location", nullable = true, length = 255)
    private String location;

    @Column(name = "content_data", nullable = true, length = 10000)
    private String data;

    @Column(name = "start_date", nullable = true)
    private String startDate;

    @Column(name = "end_date", nullable = true)
    private String endDate;

    @Column(name = "summary", nullable = true, length = 1000)
    private String summary;

    @Column(name = "charge", nullable = true)
    private Integer charge;

    @Column(name = "bank_account", nullable = true, length = 255)
    private String bankAccount;

    public Content(User user, ContentType contentType) {
        this.user = user;
        this.contentType = contentType;
    }

    protected void updateFields(User user, String title, String location, String data, String startDate, String endDate, Integer charge,
                                String bankAccount, String summary) {
        if (user != null) {
            this.user = user;
        }
        if (title != null) {
            this.title = title;
        }
        if (location != null) {
            this.title = location;
        }
        if (data != null) {
            this.data = data;
        }
        if (startDate != null) {
            this.startDate = startDate;
        }
        if (endDate != null) {
            this.endDate = endDate;
        }
        if (charge != null) {
            this.charge = charge;
        }
        if (bankAccount != null) {
            this.bankAccount = bankAccount;
        }
        if (summary != null) {
            this.summary = summary;
        }
    }

    public void updateEasyDelete(Boolean flag) {
        this.easyDelete = flag;
    }

    public void createContent(User user, ContentType contentType, String location, String title, String data, String startDate, String endDate, Integer charge, String backAccount, String summary) {
        this.user = user;
        this.contentType = contentType;
        this.location = location;
        this.title = title;
        this.data = data;
        this.startDate = startDate;
        this.endDate = endDate;
        this.charge = charge;
        this.bankAccount = backAccount;
        this.summary = summary;
    }
}