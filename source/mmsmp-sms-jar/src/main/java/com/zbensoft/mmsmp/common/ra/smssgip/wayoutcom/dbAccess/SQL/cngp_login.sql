CREATE TABLE `cngp_login` (
`name` VARCHAR(10) NOT NULL,
`value` VARCHAR(10) NOT NULL,
PRIMARY KEY (`name`))TYPE=MyISAM ;

COMMIT;

INSERT INTO `cngp_login` (`name`, `value`) VALUES 
  ('ClientId','aceway'),
  ('Secret','1234'),
  ('LoginMode','2'),
  ('Version','10');
