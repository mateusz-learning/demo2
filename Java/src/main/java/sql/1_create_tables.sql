DROP TABLE IF EXISTS accommodation CASCADE;
DROP TABLE IF EXISTS car CASCADE;
DROP TABLE IF EXISTS usr CASCADE;
DROP TABLE IF EXISTS usr_accommodation CASCADE;
DROP SEQUENCE IF EXISTS hibernate_sequence;

CREATE SEQUENCE hibernate_sequence;

CREATE TABLE IF NOT EXISTS usr (
  id                    BIGINT PRIMARY KEY,
  user_name             VARCHAR(40)  NOT NULL,
  gender                VARCHAR(6)   NOT NULL CHECK(gender in('MALE', 'FEMALE')),
  date_of_birth         DATE         NOT NULL,
  email                 VARCHAR(255) NOT NULL UNIQUE,
  number_of_children    INT                   CHECK(number_of_children >= 0),
  has_insurance         BOOLEAN
);

CREATE TABLE IF NOT EXISTS accommodation (
  id                    BIGINT PRIMARY KEY,
  street                VARCHAR(255) NOT NULL,
  street_number         VARCHAR(50)  NOT NULL,
  house_number          VARCHAR(50),
  postcode              VARCHAR(50)  NOT NULL,
  city                  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS car (
  id             BIGINT PRIMARY KEY,
  user_id        INT REFERENCES usr (id),
  licence_number VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usr_accommodation (
  user_id               INT REFERENCES usr (id) ON DELETE CASCADE NOT NULL,
  accommodation_id      INT REFERENCES accommodation (id) ON DELETE CASCADE NOT NULL,
  PRIMARY KEY (user_id, accommodation_id)
);

CREATE INDEX accommodation_index_street ON accommodation(street);
CREATE INDEX accommodation_index_street_number ON accommodation(street_number);
CREATE INDEX accommodation_index_house_number ON accommodation(house_number);
CREATE INDEX accommodation_index_city ON accommodation(city);
CREATE INDEX car_index_user_id ON car(user_id);
CREATE INDEX usr_index_date_of_birth ON usr(date_of_birth);
CREATE INDEX usr_accommodation_index_user_id ON usr_accommodation(user_id);
