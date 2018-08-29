CREATE TABLE `cngp_submitresend` (
  `postCommand` varbinary(250) default NULL,
  `preCommand` varbinary(250) default NULL,
  `seq` bigint(10) NOT NULL,
  `postmsgID` varbinary(10) default NULL,
  `premsgID` varbinary(10) default NULL,
  `convertTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `resp` char(1) default 'N',
  `retries` int(2) default '0',
  PRIMARY KEY  (`seq`,`convertTime`)
)

COMMIT;

