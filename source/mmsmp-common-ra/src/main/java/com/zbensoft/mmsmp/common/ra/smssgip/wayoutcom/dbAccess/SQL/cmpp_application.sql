CREATE TABLE `cmpp_application` (
`APPID` INTEGER(5) DEFAULT NULL,
`code` VARCHAR(10) NOT NULL,
`reserve` INTEGER(11) DEFAULT NULL,
PRIMARY KEY (`code`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `cmpp_application` (`APPID`, `code`, `reserve`) VALUES 
  (9990,'9990',NULL),
  (9991,'9991',NULL),
  (9992,'9992',NULL),
  (9998,'9998',NULL),
  (9999,'9999',NULL);
