-- Family Schema
CREATE SEQUENCE family_seq start 1 increment 1;
CREATE TABLE family
(
    id                  BIGINT          PRIMARY KEY     DEFAULT nextval('family_seq'),
    name                VARCHAR(50)     NOT NULL,
    country_code        VARCHAR(3)      NOT NULL,
    created_date        TIMESTAMPTZ     NOT NULL        DEFAULT now(),
    updated_date        TIMESTAMPTZ     NULL
);

CREATE SEQUENCE family_member_seq start 1 increment 1;
CREATE TABLE family_member
(
    id                  BIGINT          PRIMARY KEY     DEFAULT nextval('family_member_seq'),
    first_name          VARCHAR(20)     NOT NULL,
    middle_name         VARCHAR(20)     NULL,
    last_name           VARCHAR(20)     NOT NULL,
    date_of_birth       DATE            NOT NULL,
    family_id           BIGINT          NULL            REFERENCES family(id),
    father_id           BIGINT          NULL            REFERENCES family_member(id),
    mother_id           BIGINT          NULL            REFERENCES family_member(id),
    spouse_id           BIGINT          NULL            REFERENCES family_member(id),
    created_date        TIMESTAMPTZ     NOT NULL        DEFAULT now(),
    updated_date        TIMESTAMPTZ     NULL
);

-- Initial dummy values for Family Entity
INSERT INTO family(name, country_code) values ('Nobre', 'PRT');
INSERT INTO family(name, country_code) values ('Smith', 'GBR');

-- Initial dummy values for FamilyMember Entity
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id) values ('Miguel', 'Nobre', '1992-10-14', (select id from family where name = 'Nobre'));
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id) values ('Lurdes', 'Nobre', '1992-05-06', (select id from family where name = 'Nobre'));
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id) values ('John', 'Smith', '1992-10-14', (select id from family where name = 'Smith'));
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id) values ('Jane', 'Smith', '1992-05-06', (select id from family where name = 'Smith'));
