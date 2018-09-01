CREATE TABLE `cngp_application` (
`APPID` INTEGER(5) DEFAULT NULL,
`code` VARCHAR(10) NOT NULL,
`access_num` VARCHAR(15) DEFAULT NULL,
PRIMARY KEY (`code`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `cngp_application` (`APPID`, `code`, `access_num`) VALUES 
  (8992,'8992','6660115'),
  (9990,'9990',NULL),
  (9991,'9991',NULL),
  (9992,'9992',''),
  (9998,'9998',NULL),
  (9995,'9999',NULL);
