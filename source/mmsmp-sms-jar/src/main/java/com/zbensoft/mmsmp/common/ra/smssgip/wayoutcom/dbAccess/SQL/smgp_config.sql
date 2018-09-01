CREATE TABLE `smgp_config` (
`configName` VARCHAR(20) NOT NULL,
`ip` VARCHAR(15) DEFAULT NULL,
`port` INTEGER(11) DEFAULT NULL,
PRIMARY KEY (`configName`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `smgp_config` (`configName`, `ip`, `port`) VALUES 
  ('AgentClient','172.16.0.242',8814),
  ('AgentServer','172.16.0.242',8813),
  ('SPClient','172.16.1.35',8890),
  ('SPServer','172.16.0.242',8812);
