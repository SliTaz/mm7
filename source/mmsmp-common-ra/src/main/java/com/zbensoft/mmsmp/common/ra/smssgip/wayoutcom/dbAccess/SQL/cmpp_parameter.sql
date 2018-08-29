CREATE TABLE `cmpp_parameter` (
`name` VARCHAR(20) NOT NULL,
`value` INTEGER(11) DEFAULT '1',
PRIMARY KEY (`name`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `cmpp_parameter` (`name`, `value`) VALUES 
  ('apps_Check_Freq',1),
  ('data_Check_Freq',5),
  ('data_Import_Freq',5),
  ('deliver_bak',1),
  ('deliver_interval',10),
  ('deliver_resend',5),
  ('deliver_retries',3),
  ('deliver_Timeout',30),
  ('submit_bak',10),
  ('submit_interval',60),
  ('submit_resend',60),
  ('submit_retries',3),
  ('submit_Timeout',300);
