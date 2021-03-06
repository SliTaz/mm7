CREATE TABLE `smsreportresend` (
`seq1` BIGINT(10) NOT NULL,
`seq2` INTEGER(10) NOT NULL,
`seq3` INTEGER(10) NOT NULL,
`cseq1` BIGINT(10) DEFAULT NULL,
`cseq2` INTEGER(10) DEFAULT NULL,
`cseq3` INTEGER(10) DEFAULT NULL,
`recvTime` TIMESTAMP(19) NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
`resp` CHAR(1) DEFAULT 'N',
`retries` INTEGER(2) DEFAULT '0',
PRIMARY KEY (`seq1`, `seq2`, `seq3`))TYPE=InnoDB ;

COMMIT;

