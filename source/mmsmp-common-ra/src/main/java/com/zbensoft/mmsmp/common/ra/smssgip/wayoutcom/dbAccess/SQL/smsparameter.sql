CREATE TABLE `smsparameter` (
`name` VARCHAR(20) NOT NULL,
`value` INTEGER(11) DEFAULT '1',
PRIMARY KEY (`name`))TYPE=InnoDB ;

COMMIT;

INSERT INTO `smsparameter` (`name`, `value`) VALUES 
  ('apps_Check_Freq',1),
  ('data_Check_Freq',1),
  ('data_Import_Freq',5),
  ('deliver_bak',5),
  ('deliver_interval',5),
  ('deliver_resend',20),
  ('deliver_retries',3),
  ('deliver_Timeout',1),
  ('report_bak',5),
  ('report_interval',5),
  ('report_resend',20),
  ('report_retries',3),
  ('report_Timeout',1),
  ('submit_bak',5),
  ('submit_interval',60),
  ('submit_resend',120),
  ('submit_retries',3),
  ('submit_Timeout',2);
