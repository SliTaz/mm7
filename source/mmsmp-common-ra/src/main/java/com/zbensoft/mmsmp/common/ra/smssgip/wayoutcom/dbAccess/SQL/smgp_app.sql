CREATE TABLE `smgp_app` (
`name` VARCHAR(10) DEFAULT NULL,
`id` INTEGER(5) NOT NULL DEFAULT '0',
`clientIP` VARCHAR(15) DEFAULT NULL,
`ServerIP` VARCHAR(15) DEFAULT NULL,
`ServerPort` INTEGER(11) DEFAULT NULL,
PRIMARY KEY (`id`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `smgp_app` (`name`, `id`, `clientIP`, `ServerIP`, `ServerPort`) VALUES 
  ('wdf',8992,'172.16.0.242','172.16.0.242',8992),
  ('csc',9990,'172.16.1.234','172.16.1.234',9990),
  ('zzf1',9991,'172.16.1.100','172.16.1.100',9991);
