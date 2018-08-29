CREATE TABLE `cngp_parameter` (
`name` VARCHAR(20) NOT NULL,
`value` INTEGER(11) DEFAULT '1',
PRIMARY KEY (`name`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `cngp_parameter` (`name`, `value`) VALUES 
  ('apps_Check_Freq',1),
  ('Cyc2smg',10),
  ('data_Check_Freq',1),
  ('data_Import_Freq',2),
  ('deliver_bak',1),
  ('deliver_interval',5),
  ('deliver_resend',10),
  ('deliver_retries',3),
  ('deliver_Timeout',5),
  ('submit_bak',10),
  ('submit_interval',60),
  ('submit_resend',10),
  ('submit_retries',3),
  ('submit_Timeout',1440);
