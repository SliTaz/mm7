CREATE TABLE `cngp_config` (
`configName` VARCHAR(20) NOT NULL,
`ip` VARCHAR(15) DEFAULT NULL,
`port` INTEGER(11) DEFAULT NULL,
PRIMARY KEY (`configName`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `cngp_config` (`configName`, `ip`, `port`) VALUES 
  ('AgentServer','218.249.100.4',8813),
  ('SPClient','202.99.192.123',9890),
  ('SPClient-in','218.249.100.4',8881);
