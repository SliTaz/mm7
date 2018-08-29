CREATE TABLE `cngp_app` (
`name` VARCHAR(10) DEFAULT NULL,
`id` INTEGER(5) NOT NULL DEFAULT '0',
`clientIP` VARCHAR(15) DEFAULT NULL,
`ServerIP` VARCHAR(15) DEFAULT NULL,
`ServerPort` INTEGER(11) DEFAULT NULL,
PRIMARY KEY (`id`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `cngp_app` (`name`, `id`, `clientIP`, `ServerIP`, `ServerPort`) VALUES 
  ('wdf',8992,'218.249.100.4','218.249.100.4',8992),
  ('csc',9990,'172.16.1.234','172.16.1.234',9990),
  ('zzf1',9991,'172.16.1.100','172.16.1.100',8992),
  ('liw',9992,'172.16.0.14','172.16.0.14',8892),
  ('liwei',9993,'218.249.100.23','218.249.100.23',8992),
  ('lw',9994,'218.249.100.21','218.249.100.21',8992),
  ('wdf',9995,'172.16.0.242','172.16.0.242',8992);
