CREATE TABLE smssubmitbak (
  Command VARBINARY(200) DEFAULT NULL,
  pre_seq1 BIGINT(10) NOT NULL DEFAULT '0',
  pre_seq2 INTEGER(10) NOT NULL DEFAULT '0',
  pre_seq3 INTEGER(10) NOT NULL DEFAULT '0',
  post_seq1 BIGINT(10) DEFAULT NULL,
  post_seq2 INTEGER(10) DEFAULT NULL,
  post_seq3 INTEGER(10) DEFAULT NULL,
  convertTime TIMESTAMP(19) NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
  resp CHAR(1) DEFAULT NULL,
  retries INTEGER(2) DEFAULT '0',
  PRIMARY KEY (pre_seq1, pre_seq2, pre_seq3))TYPE=InnoDB;

COMMIT;

