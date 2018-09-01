CREATE TABLE `smgp_delivertimeout` (
`seq` BIGINT(10) NOT NULL,
`recvTime` TIMESTAMP(19) NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
`resp` CHAR(1) DEFAULT 'N',
`retries` INTEGER(2) DEFAULT '0',
PRIMARY KEY (`seq`, `recvTime`))TYPE=InnoDB ;

COMMIT;

