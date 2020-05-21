package com.truphone.challenge.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@Setter
@Getter
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GenericGenerator(name = "SequencePerEntityGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = @Parameter(name = "prefer_sequence_per_entity", value = "true"))
    @GeneratedValue(generator = "SequencePerEntityGenerator")
    private Long id;

    @CreatedDate
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @LastModifiedDate
    private ZonedDateTime updatedDate;
}
