CREATE TABLE `smgp_submitresend` (
`seq` BIGINT(10) NOT NULL,
`convertTime` TIMESTAMP(19) NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
`resp` CHAR(1) DEFAULT 'N',
`retries` INTEGER(2) DEFAULT '0',
PRIMARY KEY (`seq`, `convertTime`))TYPE=InnoDB ;

COMMIT;

