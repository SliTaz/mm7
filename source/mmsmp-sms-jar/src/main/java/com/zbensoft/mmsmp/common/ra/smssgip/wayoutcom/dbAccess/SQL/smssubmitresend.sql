CREATE TABLE `smssubmitresend` (
`post_seq1` BIGINT(10) NOT NULL,
`post_seq2` INTEGER(10) NOT NULL,
`post_seq3` INTEGER(10) NOT NULL,
`pre_seq1` BIGINT(10) DEFAULT NULL,
`pre_seq2` INTEGER(10) DEFAULT NULL,
`pre_seq3` INTEGER(10) DEFAULT NULL,
`convertTime` TIMESTAMP(19) NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
`resp` CHAR(1) DEFAULT 'N',
`retries` INTEGER(2) DEFAULT '0',
PRIMARY KEY (`post_seq1`, `post_seq2`, `post_seq3`))TYPE=InnoDB ;

COMMIT;

