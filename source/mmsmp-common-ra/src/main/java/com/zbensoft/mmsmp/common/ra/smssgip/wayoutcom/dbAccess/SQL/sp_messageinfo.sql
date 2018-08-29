CREATE TABLE `sp_messageinfo` (
  `cellphonenumber` varchar(20) NOT NULL,
  `delivertime` varchar(20) default NULL,
  `transactionid` varchar(50) NOT NULL,
  `fromw` varchar(20) default NULL,
  `subject` varchar(50) default NULL,
  PRIMARY KEY  (`cellphonenumber`,`transactionid`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk
