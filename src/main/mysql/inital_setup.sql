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
    `id` BIGINT(19) NOT NULL,
    `name` VARCHAR(75) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `currencycode` VARCHAR(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `balance` DOUBLE NOT NULL, PRIMARY KEY (`id`),
    `fkusers` BIGINT(19) NOT NULL,
    KEY `fkusers` (`fkusers`),
    CONSTRAINT `accounts_users` FOREIGN KEY (`fkusers`) REFERENCES `users` (`id`)
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;