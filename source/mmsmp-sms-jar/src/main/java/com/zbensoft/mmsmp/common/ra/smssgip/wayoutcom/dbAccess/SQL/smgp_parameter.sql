CREATE TABLE `smgp_parameter` (
`name` VARCHAR(20) NOT NULL,
`value` INTEGER(11) DEFAULT '1',
PRIMARY KEY (`name`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `smgp_parameter` (`name`, `value`) VALUES 
  ('apps_Check_Freq',1),
  ('data_Check_Freq',1),
  ('data_Import_Freq',5),
  ('deliver_bak',1),
  ('deliver_interval',5),
  ('deliver_resend',10),
  ('deliver_retries',3),
  ('deliver_Timeout',5),
  ('submit_bak',10),
  ('submit_interval',60),
  ('submit_resend',1440),
  ('submit_retries',3),
  ('submit_Timeout',600);
