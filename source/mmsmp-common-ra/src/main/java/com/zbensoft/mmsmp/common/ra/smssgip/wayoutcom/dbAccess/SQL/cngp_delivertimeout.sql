CREATE TABLE `cngp_delivertimeout` (
  `Command` varbinary(300) default NULL,
  `seq` bigint(10) NOT NULL,
  `recvTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `resp` char(1) default 'N',
  `retries` int(2) default '0',
  PRIMARY KEY  (`seq`,`recvTime`)
)

COMMIT;

