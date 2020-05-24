-- Family Schema
CREATE SEQUENCE family_seq start 1 increment 1;
CREATE TABLE family
(
    id                      BIGINT                  PRIMARY KEY     DEFAULT nextval('family_seq'),
    name                    VARCHAR(50)             NOT NULL,
    country_code            VARCHAR(3)              NOT NULL,
    created_date            TIMESTAMPTZ             NOT NULL        DEFAULT now(),
    updated_date            TIMESTAMPTZ             NULL            DEFAULT now()
);

CREATE SEQUENCE family_marriage_seq start 1 increment 1;
CREATE TABLE family_marriage
(
    id                      BIGINT                  PRIMARY KEY     DEFAULT nextval('family_marriage_seq'),
    created_date            TIMESTAMPTZ             NOT NULL        DEFAULT now(),
    updated_date            TIMESTAMPTZ             NULL            DEFAULT now()
);

CREATE SEQUENCE family_member_seq start 1 increment 1;
CREATE TABLE family_member
(
    id                      BIGINT                  PRIMARY KEY     DEFAULT nextval('family_member_seq'),
    first_name              VARCHAR(20)             NOT NULL,
    middle_name             VARCHAR(20)             NULL,
    last_name               VARCHAR(20)             NOT NULL,
    date_of_birth           DATE                    NOT NULL,
    family_id               BIGINT                  NULL            REFERENCES family(id),
    father_id               BIGINT                  NULL            REFERENCES family_member(id),
    mother_id               BIGINT                  NULL            REFERENCES family_member(id),
    family_marriage_id      BIGINT                  NULL            REFERENCES family_marriage(id),
    created_date            TIMESTAMPTZ             NOT NULL        DEFAULT now(),
    updated_date            TIMESTAMPTZ             NULL            DEFAULT now()
);

-- View With Aged Family
CREATE VIEW v_aged_family AS
    SELECT
     fm.family_id,
     SUM(EXTRACT(EPOCH FROM fm.date_of_birth)) AS sum_age_as_epoch,
     AVG(EXTRACT(EPOCH FROM fm.date_of_birth)) AS avg_age_as_epoch
    FROM
     family_member fm
    group BY
     fm.family_id
    ORDER BY
        2 ASC,
        3 ASC
    LIMIT 1;

-- View with Fast Growing Family
CREATE VIEW v_fast_growing_family AS
    SELECT
        family_id,
        count(1)::decimal / (MAX(date_of_birth) - MIN(date_of_birth)) AS growing_rate,
        count(1) AS total_members
    FROM
        family_member
    GROUP BY
        family_id
    ORDER BY
        2 DESC,
        3 DESC
    LIMIT 1;