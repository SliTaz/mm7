CREATE TABLE `cmpp_config` (
`configName` VARCHAR(20) NOT NULL,
`ip` VARCHAR(15) DEFAULT NULL,
`port` INTEGER(11) DEFAULT NULL,
PRIMARY KEY (`configName`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `cmpp_config` (`configName`, `ip`, `port`) VALUES 
  ('AgentClient','172.16.0.242',7814),
  ('AgentServer','172.16.0.242',7813),
  ('SPClient','172.16.0.242',7890),
  ('SPServer','172.16.0.242',7812);
