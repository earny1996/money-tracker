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
    PRIMARY KEY (`id`),
    KEY `fkusers` (`fkusers`),
    CONSTRAINT `accounts_users` FOREIGN KEY (`fkusers`) REFERENCES `users` (`id`)
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `transactions` (
    `id` BIGINT(19) NOT NULL,
    `fkusers` BIGINT(19) NOT NULL,
    `fkfromaccounts` BIGINT(19) NOT NULL,
    `fktoaccounts` BIGINT(19) NOT NULL,
    `executeddate` DATETIME NOT NULL,
    `amount` DOUBLE NOT NULL,
    `createddate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `transactions_fkusers` (`fkusers`),
    CONSTRAINT `transactions_users` FOREIGN KEY (`fkusers`) REFERENCES `users` (`id`),
    KEY `transactions_fkfromaccounts` (`fkfromaccounts`),
    CONSTRAINT `transactions_fkfromaccounts` FOREIGN KEY (`fkfromaccounts`) REFERENCES `accounts` (`id`),
    KEY `transactions_fktoaccounts` (`fktoaccounts`),
    CONSTRAINT `transactions_fktoaccounts` FOREIGN KEY (`fktoaccounts`) REFERENCES `accounts` (`id`)
) ENGINE = InnoDB;