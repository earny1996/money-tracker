CREATE DATABASE moneytracker;

CREATE TABLE `users` ( 
    `id` BIGINT(19) NOT NULL,
    `firstname` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `lastname` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `email` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `password` VARCHAR(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`), UNIQUE (`email`)
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE `accounts` (
  `id` bigint(19) NOT NULL,
  `name` varchar(75) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `currencycode` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `balance` double NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

  alter table accounts
       add constraint FKnjuop33mo69pd79ctplkck40n
       foreign key (user_id)
       references users (id)