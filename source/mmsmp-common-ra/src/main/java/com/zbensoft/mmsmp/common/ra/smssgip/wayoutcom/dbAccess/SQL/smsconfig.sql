CREATE TABLE `smsconfig` (
`configName` VARCHAR(20) NOT NULL,
`ip` VARCHAR(15) DEFAULT NULL,
`port` INTEGER(5) DEFAULT NULL,
PRIMARY KEY (`configName`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `smsconfig` (`configName`, `ip`, `port`) VALUES 
  ('AgentClient','172.16.1.212',9814),
  ('AgentServer','172.16.0.242',9813),
  ('SPClient','172.16.1.100',8801),
  ('SPServer','172.16.0.242',9812);
