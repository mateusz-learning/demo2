--liquibase formatted sql
--changeset mateusz:1

CREATE sequence hibernate_sequence;

CREATE TABLE usr (
  id                    bigint PRIMARY KEY,
  user_name             VARCHAR(40)  NOT NULL,
  gender                VARCHAR(6)   NOT NULL CHECK(gender IN('MALE', 'FEMALE')),
  date_of_birth         DATE         NOT NULL,
  email                 VARCHAR(255) NOT NULL UNIQUE,
  number_of_children    INT                   CHECK(number_of_children >= 0),
  has_insurance         boolean
);

CREATE TABLE accommodation (
  id                    bigint PRIMARY KEY,
  street                VARCHAR(255) NOT NULL,
  street_number         VARCHAR(50)  NOT NULL,
  house_number          VARCHAR(50),
  postcode              VARCHAR(50)  NOT NULL,
  city                  VARCHAR(255) NOT NULL
);

CREATE TABLE car (
  id             bigint PRIMARY KEY,
  user_id        INT REFERENCES usr (id),
  licence_number VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usr_accommodation (
  user_id               INT REFERENCES usr (id) ON DELETE CASCADE NOT NULL,
  accommodation_id      INT REFERENCES accommodation (id) ON DELETE CASCADE NOT NULL,
  PRIMARY KEY (user_id, accommodation_id)
);

CREATE index usr_index_date_of_birth ON usr(date_of_birth);
CREATE index accommodation_index_street ON accommodation(street);
CREATE index accommodation_index_street_number ON accommodation(street_number);
CREATE index accommodation_index_house_number ON accommodation(house_number);
CREATE index accommodation_index_city ON accommodation(city);
CREATE index car_index_user_id ON car(user_id);
CREATE index usr_accommodation_index_user_id ON usr_accommodation(user_id);

--rollback drop TABLE usr_accommodation;
--rollback drop TABLE car;
--rollback drop TABLE accommodation;
--rollback drop TABLE usr;
--rollback DROP sequence hibernate_sequence
