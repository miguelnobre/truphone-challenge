--Init DB State
DELETE FROM family_member;
DELETE FROM family;
ALTER SEQUENCE family_member_seq RESTART WITH 1;
ALTER SEQUENCE family_seq RESTART WITH 1;

-- Initial dummy values for Family Entity,
INSERT INTO family(name, country_code) values ('Nobre', 'PRT');
INSERT INTO family(name, country_code) values ('Smith', 'GBR');

-- Initial dummy values for FamilyMember Entity
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id) values ('Miguel', 'Nobre', '1992-10-14', (select id from family where name = 'Nobre'));
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id) values ('Lurdes', 'Nobre', '1992-05-06', (select id from family where name = 'Nobre'));
--Miguel e Lurdes Duplicados
INSERT INTO family_member(first_name, middle_name, last_name, date_of_birth, family_id) values ('Miguel', 'Duplicado Father ', 'Nobre', '1992-10-14', (select id from family where name = 'Nobre'));
INSERT INTO family_member(first_name, middle_name, last_name, date_of_birth, family_id) values ('Lurdes', 'Duplicado Mother', 'Nobre', '1992-05-06', (select id from family where name = 'Nobre'));

INSERT INTO family_member(first_name, last_name, date_of_birth, family_id) values ('John', 'Smith', '1992-10-14', (select id from family where name = 'Smith'));
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id) values ('Jane', 'Smith', '1992-05-06', (select id from family where name = 'Smith'));
--Duas Linahs duplicadas
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id) values ('Luis', 'Nobre', '1992-10-14', (select id from family where name = 'Nobre'));
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id) values ('Luis', 'Nobre', '1992-10-14', (select id from family where name = 'Nobre'));
---
--Filho do Miguel e Lurdes
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id, father_id, mother_id) values ('Antonio', 'Nobre', '1992-10-14', (select id from family where name = 'Nobre'), 1, 2);
-- Filho do Duplicado de Miguel e Lurdes
INSERT INTO family_member(first_name, last_name, date_of_birth, family_id, father_id, mother_id) values ('Antonio', 'Nobre', '1992-10-14', (select id from family where name = 'Nobre'), 3, 4);
