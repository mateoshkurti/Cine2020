create table SEC_USER
(
  userId            BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  email             VARCHAR(75) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  enabled           BIT NOT NULL 
) ;

create table SEC_ROLE
(
  roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
) ;

create table USER_ROLE
(
  ID     BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

create Table Cinema (
	cinemaID BIGINT NOT NULL Primary Key AUTO_INCREMENT,
	cinemaName VARCHAR(255),
	cinemaStage VARCHAR(255),
	availableSeats int,
	movie VARCHAR(255),
	properlySanitized VARCHAR(8),
	markedSeats VARCHAR(8),
	markedGround VARCHAR(8),
	doorSigns VARCHAR(8)
);

alter table USER_ROLE
  add constraint USER_ROLE_UK unique (userId, roleId);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (userId)
  references SEC_USER (userId);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (roleId)
  references SEC_ROLE (roleId);
