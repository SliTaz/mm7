CREATE TABLE `cngp_deliverok` (
`seq` BIGINT(10) NOT NULL,
`time` TIMESTAMP(19) NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
PRIMARY KEY (`seq`, `time`))TYPE=InnoDB ;

COMMIT;

